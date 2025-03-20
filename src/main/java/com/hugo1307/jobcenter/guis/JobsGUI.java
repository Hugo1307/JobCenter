package com.hugo1307.jobcenter.guis;

import com.hugo1307.jobcenter.jobs.JobCategory;
import com.hugo1307.jobcenter.jobs.JobsConfigController;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayerRank;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobsGUI extends GUIImpl {

    private static JobsGUI instance;

    private final List<String> itemsDescription = new ArrayList<>();

    private JobsGUI() {
        super(ChatColor.BLACK + "" + ChatColor.BOLD + "Job Center", 9*3);
    }

    @Override
    public void build() {

        for (JobCategory jobCategory : JobCategory.values())
            this.itemsDescription.add(JobsConfigController.getInstance().getJobCategoryDescription(jobCategory));

        ItemStack filler = new ItemStackBuilder.Builder(Material.STAINED_GLASS_PANE)
                .itemData((short)7).name(" ").build().toItemStack();

        ItemStack basicJobsItem = new ItemStackBuilder.Builder(Material.WOOD)
                .name(ChatColor.GRAY + "Basic Jobs").lore(Collections.singletonList(itemsDescription.get(0))).build().toItemStack();

        ItemStack advancedJobsItem = new ItemStackBuilder.Builder(Material.EMERALD_BLOCK)
                .name(ChatColor.GREEN + "Advanced Jobs").lore(Collections.singletonList(itemsDescription.get(1))).build().toItemStack();

        ItemStack expertJobsItem = new ItemStackBuilder.Builder(Material.DIAMOND_BLOCK)
                .name(ChatColor.GOLD + "Expert Jobs").lore(Collections.singletonList(itemsDescription.get(2))).build().toItemStack();

        for (int inventoryItemIdx = 0; inventoryItemIdx < super.getSize(); inventoryItemIdx++)
            super.getInventory().setItem(inventoryItemIdx, filler);

        super.getInventory().setItem(10, basicJobsItem);
        super.getInventory().setItem(13, advancedJobsItem);
        super.getInventory().setItem(16, expertJobsItem);

    }

    @Override
    public void handleClick(InventoryClickEvent event) {

        ItemStack clickedItem = event.getCurrentItem();
        Player whoClicked = (Player) event.getWhoClicked();

        PlayerProfile playerProfile = PlayersDataController.getInstance().loadPlayerProfile(whoClicked.getUniqueId());

        event.setCancelled(true);

        if (playerProfile == null) {
            playerProfile = new PlayerProfile.Builder(whoClicked.getUniqueId()).build();
            playerProfile.save();
        }

        switch (clickedItem.getType()) {
            case WOOD:

                if (!whoClicked.hasPermission(Permissions.BASIC_JOBS.getPermission())) {
                    whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                    whoClicked.closeInventory();
                    break;
                }

                BasicJobsGUI.getInstance().build();
                BasicJobsGUI.getInstance().show(whoClicked);
                break;

            case EMERALD_BLOCK:

                if (!whoClicked.hasPermission(Permissions.ADVANCED_JOBS.getPermission())) {
                    whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                    whoClicked.closeInventory();
                    break;
                }

                if (playerProfile.getPlayerRank() == PlayerRank.BASIC) {
                    whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getInsufficientRank(), ChatColor.GREEN + "Advanced" + ChatColor.GRAY));
                    whoClicked.closeInventory();
                    break;
                }

                AdvancedJobsGUI.getInstance().build();
                AdvancedJobsGUI.getInstance().show(whoClicked);
                break;

            case DIAMOND_BLOCK:

                if (!whoClicked.hasPermission(Permissions.EXPERT_JOBS.getPermission())) {
                    whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                    whoClicked.closeInventory();
                    break;
                }

                if (playerProfile.getPlayerRank() == PlayerRank.BASIC || playerProfile.getPlayerRank() == PlayerRank.ADVANCED) {
                    whoClicked.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getInsufficientRank(), ChatColor.GREEN + "Expert" + ChatColor.GRAY));
                    whoClicked.closeInventory();
                    break;
                }

                ExpertJobsGUI.getInstance().build();
                ExpertJobsGUI.getInstance().show(whoClicked);
                break;
        }

    }

    public static JobsGUI getInstance() {
        if (instance == null) instance = new JobsGUI();
        return instance;
    }

}
