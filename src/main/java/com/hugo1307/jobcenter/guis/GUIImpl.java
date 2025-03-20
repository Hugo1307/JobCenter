package com.hugo1307.jobcenter.guis;

import com.hugo1307.jobcenter.caches.GUIHistoryCache;
import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobCategory;
import com.hugo1307.jobcenter.jobs.JobsConfigController;
import com.hugo1307.jobcenter.jobs.JobsDataController;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.BlackListController;
import com.hugo1307.jobcenter.utils.ItemStackBuilder;
import com.hugo1307.jobcenter.utils.MainConfigController;
import com.hugo1307.jobcenter.utils.Messages;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Map;

@Getter
public class GUIImpl implements GUI {

    private final String name;
    private final int size;
    private final Inventory inventory;

    public GUIImpl(String name, int size) {
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, this.size, this.name);
    }

    @Override
    public void build() {
       fill();
    }

    @Override
    public void show(Player player) {
        player.openInventory(this.inventory);
        GUIHistoryCache.getInstance().add(player.getUniqueId(), this);
    }

    @Override
    public void handleClick(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
    }

    @Override
    public String getName() {
        return this.name;
    }

    protected void addExitButton() {
        ItemStack backButton = new ItemStackBuilder.Builder(Material.REDSTONE_TORCH_ON)
                .name(ChatColor.RED + "Back").build().toItemStack();
        this.getInventory().setItem(40, backButton);
    }

    protected void fill() {

        ItemStack filler = new ItemStackBuilder.Builder(Material.STAINED_GLASS_PANE)
                .itemData((short)7).name(" ").build().toItemStack();

        for (int inventoryItemIdx = 0; inventoryItemIdx < this.size; inventoryItemIdx++)
            inventory.setItem(inventoryItemIdx, filler);

    }

    protected void handleJobGuiClick(InventoryClickEvent inventoryClickEvent, Map<ItemStack, Integer> jobsIds, JobCategory jobCategory) {

        Player whoClicked = (Player) inventoryClickEvent.getWhoClicked();
        ItemStack clickedItem = inventoryClickEvent.getCurrentItem();
        Integer jobId = jobsIds.get(clickedItem);

        inventoryClickEvent.setCancelled(true);

        if (jobId != null) {

            PlayerProfile whoClickedProfile = PlayersDataController.getInstance().loadPlayerProfile(whoClicked.getUniqueId());

            if (whoClickedProfile == null) {
                whoClickedProfile = new PlayerProfile.Builder(whoClicked.getUniqueId()).build();
                whoClickedProfile.save();
            }

            if (whoClickedProfile.getCurrentJob() != null) {
                whoClicked.closeInventory();
                whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getAlreadyHasJob());
                return;
            }

            if (BlackListController.getInstance().containsPlayer(whoClicked.getUniqueId())) {
                whoClicked.closeInventory();
                whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getUnableToAcceptJob());
                return;
            }

            Job jobSelected = JobsConfigController.getInstance().getJob(jobCategory, jobId);

            if (jobSelected == null) {
                whoClicked.closeInventory();
                whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getUnableToFindJob());
                return;
            }

            double jobPaymentPerDay = jobSelected.getPayment()/7;
            int jobItemsPerDay = jobSelected.getRequiredAmount()/7;

            int daysPassed = (((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - MainConfigController.getInstance().getWeekResetDay()) % 7) + 7) % 7;

            jobSelected.setPayment(Math.round(jobSelected.getPayment() - daysPassed*jobPaymentPerDay));
            jobSelected.setRequiredAmount(jobSelected.getRequiredAmount() - daysPassed*jobItemsPerDay);

            int jobsTaken = JobsDataController.getInstance().getJobsTaken(jobCategory, jobId);

            if (jobsTaken >= jobSelected.getAvailable()) {
                whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getMaxJobAmountReached());
                return;
            }

            JobsDataController.getInstance().setJobsTaken(jobCategory, jobId, jobsTaken+1);
            whoClickedProfile.setCurrentJob(jobSelected);
            whoClickedProfile.save();

            whoClicked.closeInventory();

            whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getJobSelected(), ChatColor.GREEN + jobSelected.getName() + ChatColor.GRAY));

        } else if (clickedItem.getType() == Material.REDSTONE_TORCH_ON) {
            GUIHistoryCache.getInstance().getLastGui(whoClicked.getUniqueId()).show(whoClicked);
        }

    }

}
