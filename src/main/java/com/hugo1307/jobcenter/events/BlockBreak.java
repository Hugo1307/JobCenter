package com.hugo1307.jobcenter.events;

import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobType;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.MessageFormat;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {

        double[] percentageMessages = {0.25, 0.50, 0.75};

        Player player = event.getPlayer();
        PlayerProfile playerProfile = PlayersDataController.getInstance().loadPlayerProfile(player.getUniqueId());

        if (playerProfile == null) return;

        if (playerProfile.getCurrentJob() == null) return;

        if (playerProfile.getCurrentJob().getType() != JobType.COLLECT) return;

        Block blockBroken = event.getBlock();
        Job playerCurrentJob = playerProfile.getCurrentJob();

        if (blockBroken.getType() != playerCurrentJob.getItemMaterial()) return;

        int completedAmount = playerCurrentJob.addCompletedAmount(1);
        playerProfile.save();

        for (double percentage : percentageMessages)
            if (completedAmount == playerCurrentJob.getRequiredAmount()*percentage)
                player.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getCompletedJobPercentage(), ChatColor.GREEN + String.valueOf(percentage*100) + ChatColor.GRAY));

        if (completedAmount == playerCurrentJob.getRequiredAmount()) {
            player.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getJobCompleted());
            player.sendTitle(ChatColor.GREEN + "Job Completed", ChatColor.GRAY + "You've completed your weekly job.");
        }

    }

}
