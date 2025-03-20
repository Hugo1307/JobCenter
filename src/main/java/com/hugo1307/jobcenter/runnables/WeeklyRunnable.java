package com.hugo1307.jobcenter.runnables;

import com.hugo1307.jobcenter.Main;
import com.hugo1307.jobcenter.jobs.JobsDataController;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayerRank;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.BlackListController;
import com.hugo1307.jobcenter.utils.MainConfigController;
import com.hugo1307.jobcenter.utils.Messages;
import com.hugo1307.jobcenter.utils.WarnListController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

public class WeeklyRunnable extends BukkitRunnable {

    @Override
    public void run() {

        int weekResetDay = MainConfigController.getInstance().getWeekResetDay();

        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != weekResetDay) return;

        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) != 23) return;

        if (Calendar.getInstance().get(Calendar.MINUTE) != 59) return;

        BlackListController.getInstance().clean();

        List<PlayerProfile> allPlayerProfiles = PlayersDataController.getInstance().getAllPLayerProfiles();
        for (PlayerProfile playerProfile : allPlayerProfiles) {

            OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(playerProfile.getUuid());

            if (playerProfile.getCurrentJob() == null) continue;

            if (playerProfile.getCurrentJob().getCompletedAmount() >= playerProfile.getCurrentJob().getRequiredAmount()) {

                Main.getInstance().getEcon().depositPlayer(player, playerProfile.getCurrentJob().getPayment());
                playerProfile.setCompletedJobs(playerProfile.getCompletedJobs()+1);

                if (playerProfile.getCurrentJob().getRewardCommand() != null)
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), playerProfile.getCurrentJob().getRewardCommand());

                int jobsForAdvancedRank = MainConfigController.getInstance().getCompletedJobsForRank(PlayerRank.ADVANCED);
                int jobsForExpertRank = MainConfigController.getInstance().getCompletedJobsForRank(PlayerRank.EXPERT);

                if (playerProfile.getPlayerRank() == PlayerRank.BASIC && playerProfile.getCompletedJobs() > jobsForAdvancedRank) {
                    playerProfile.setPlayerRank(PlayerRank.ADVANCED);
                }else if (playerProfile.getPlayerRank() == PlayerRank.ADVANCED && playerProfile.getCompletedJobs() > jobsForExpertRank) {
                    playerProfile.setPlayerRank(PlayerRank.EXPERT);
                }

                if (WarnListController.getInstance().containsPlayer(player.getUniqueId()))
                    WarnListController.getInstance().removePlayer(player.getUniqueId());

                if (player.isOnline())
                    ((Player) player).sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getPaymentReceived(), ChatColor.GREEN + String.valueOf(playerProfile.getCurrentJob().getPayment()) + ChatColor.GRAY));

            }else {

                if (!WarnListController.getInstance().containsPlayer(player.getUniqueId())) {
                    WarnListController.getInstance().addPlayer(player.getUniqueId());
                }else if (!BlackListController.getInstance().containsPlayer(player.getUniqueId())) {
                    BlackListController.getInstance().addPlayer(player.getUniqueId());
                    WarnListController.getInstance().removePlayer(player.getUniqueId());
                }

            }

            JobsDataController.getInstance().resetJobsData();

            playerProfile.resetJob();
            playerProfile.save();

        }

    }

}
