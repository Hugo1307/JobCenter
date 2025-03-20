package com.hugo1307.jobcenter.events;

import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobType;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;

public class CraftItem implements Listener {

    @EventHandler
    public void onCraftItemEvent(CraftItemEvent event) {

        double[] percentageMessages = {0.25, 0.50, 0.75};

        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        PlayerProfile playerProfile = PlayersDataController.getInstance().loadPlayerProfile(player.getUniqueId());

        if (playerProfile == null) return;

        if (playerProfile.getCurrentJob() == null) return;

        if (playerProfile.getCurrentJob().getType() != JobType.CRAFT) return;

        ItemStack craftedItem = event.getRecipe().getResult();
        Job playerCurrentJob = playerProfile.getCurrentJob();

        if (craftedItem.getType() != playerCurrentJob.getItemMaterial()) return;

        Inventory inventory = event.getInventory();
        ClickType clickType = event.getClick();
        int realAmount = craftedItem.getAmount();

        if (clickType.isShiftClick()) {

            int lowerAmount = craftedItem.getMaxStackSize() + 1000;

            for(ItemStack actualItem : inventory.getContents()) {
                if(actualItem.getType() != Material.AIR && lowerAmount > actualItem.getAmount() && !actualItem.getType().equals(craftedItem.getType()))
                    lowerAmount = actualItem.getAmount();
            }

            realAmount = lowerAmount * craftedItem.getAmount();

        }

        int completedAmount = playerCurrentJob.addCompletedAmount(realAmount);
        playerProfile.save();

        for (double percentage : percentageMessages)
            if (playerCurrentJob.getCompletedAmount() == playerCurrentJob.getRequiredAmount()*percentage)
                player.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getCompletedJobPercentage(), ChatColor.GREEN + String.valueOf(percentage*100) + ChatColor.GRAY));

        if (completedAmount == playerCurrentJob.getRequiredAmount()) {
            player.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getJobCompleted());
            player.sendTitle(ChatColor.GREEN + "Job Completed", ChatColor.GRAY + "You've completed your weekly job.");
        }

    }

}
