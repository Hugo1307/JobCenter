package com.hugo1307.jobcenter.events;

import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobType;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {

        Player player = event.getPlayer();
        PlayerProfile playerProfile = PlayersDataController.getInstance().loadPlayerProfile(player.getUniqueId());

        if (playerProfile == null) return;

        if (playerProfile.getCurrentJob() == null) return;

        if (playerProfile.getCurrentJob().getType() != JobType.COLLECT) return;

        Block blockBroken = event.getBlock();
        Job playerCurrentJob = playerProfile.getCurrentJob();

        if (blockBroken.getType() != playerCurrentJob.getItemMaterial()) return;

        int completedAmount = playerCurrentJob.getCompletedAmount()+1;

        playerCurrentJob.setCompletedAmount(completedAmount);
        playerProfile.save();

        if (completedAmount == playerCurrentJob.getRequiredAmount()*0.25) {

        }

    }

}
