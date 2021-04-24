package com.hugo1307.jobcenter.players;

import com.hugo1307.jobcenter.Main;
import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobCategory;
import com.hugo1307.jobcenter.jobs.JobsDataController;
import com.hugo1307.jobcenter.utils.DataAccessor;
import com.hugo1307.jobcenter.utils.DataFile;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.logging.Level;

public class PlayersDataController {

    private static PlayersDataController instance;

    private final DataAccessor playersDataAccessor = new DataAccessor(DataFile.PLAYERS);

    private PlayersDataController() {}

    public void savePlayerProfile(PlayerProfile playerProfile) {

        playersDataAccessor.reloadConfig();

        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CurrentJob.Id",
                playerProfile.getCurrentJob().getId());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CurrentJob.Category",
                playerProfile.getCurrentJob().getCategory().toString());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CurrentJob.RequiredAmount",
                playerProfile.getCurrentJob().getRequiredAmount());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CurrentJob.CompletedAmount",
                playerProfile.getCurrentJob().getCompletedAmount());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CurrentJob.Payment",
                playerProfile.getCurrentJob().getPayment());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CurrentJob.Completed",
                playerProfile.isJobCompleted());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".CompletedJobs",
                playerProfile.getCompletedJobs());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".JobAcceptCoolDown",
                playerProfile.getAcceptCoolDown());
        playersDataAccessor.getConfig().set("Players." + playerProfile.getUuid() + ".Rank",
                playerProfile.getPlayerRank().toString());

        playersDataAccessor.saveConfig();

    }

    public PlayerProfile loadPlayerProfile(UUID playerUUID) {

        playersDataAccessor.reloadConfig();

        int currentJobId=0, currentJobRequiredAmount=1, completedJobs=0, jobAcceptCoolDown=0, currentJobAmount=0;
        double currentJobPayment=0d;
        boolean currentJobCompleted=false;
        JobCategory currentJobCategory=null;
        PlayerRank playerRank;

        if (!playersDataAccessor.getConfig().contains("Players." + playerUUID)) {
            Bukkit.getLogger().log(Level.SEVERE, Main.getInstance().getPluginPrefix() + "Error retrieving profile for player " + playerUUID);
            return null;
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.Id")) {
            currentJobId = playersDataAccessor.getConfig().getInt("Players." + playerUUID + ".CurrentJob.Id");
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.Category")) {
            try {
                currentJobCategory = JobCategory.valueOf(playersDataAccessor.getConfig().getString("Players." + playerUUID + ".CurrentJob.Category"));
            }catch (IllegalArgumentException iae) {
                Bukkit.getLogger().log(Level.SEVERE, Main.getInstance().getPluginPrefix() + "Error parsing Current Job Category for player " + playerUUID);
            }
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.RequiredAmount")) {
            currentJobRequiredAmount = playersDataAccessor.getConfig().getInt("Players." + playerUUID + ".CurrentJob.RequiredAmount");
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.CompletedAmount")) {
            currentJobAmount = playersDataAccessor.getConfig().getInt("Players." + playerUUID + ".CurrentJob.CompletedAmount");
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.Payment")) {
            currentJobPayment = playersDataAccessor.getConfig().getDouble("Players." + playerUUID + ".CurrentJob.Payment");
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.Completed")) {
            currentJobCompleted = playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CurrentJob.Completed");
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".CompletedJobs")) {
            completedJobs = playersDataAccessor.getConfig().getInt("Players." + playerUUID + ".CompletedJobs");
        }else{
            Bukkit.getLogger().log(Level.WARNING, Main.getInstance().getPluginPrefix() + "Error retrieving Completed Jobs for player " + playerUUID);
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".JobAcceptCoolDown")) {
            jobAcceptCoolDown = playersDataAccessor.getConfig().getInt("Players." + playerUUID + ".JobAcceptCoolDown");
        }else{
            Bukkit.getLogger().log(Level.WARNING, Main.getInstance().getPluginPrefix() + "Error retrieving Job Accept CoolDown for player " + playerUUID);
        }

        if (playersDataAccessor.getConfig().contains("Players." + playerUUID + ".Rank")) {
            try{
                playerRank = PlayerRank.valueOf(playersDataAccessor.getConfig().getString("Players." + playerUUID + ".Rank"));
            }catch (IllegalArgumentException iae) {
                playerRank = PlayerRank.BASIC;
                Bukkit.getLogger().log(Level.WARNING, Main.getInstance().getPluginPrefix() + "Error parsing Rank for player " + playerUUID);
            }
        }else{
            playerRank = PlayerRank.BASIC;
            Bukkit.getLogger().log(Level.WARNING, Main.getInstance().getPluginPrefix() + "Error retrieving Rank for player " + playerUUID);
        }

        Job playerJob = null;

        if (currentJobCategory != null && currentJobId != 0) {
            playerJob = JobsDataController.getInstance().getJob(currentJobCategory, currentJobId);
            playerJob.setRequiredAmount(currentJobRequiredAmount);
            playerJob.setPayment(currentJobPayment);
            playerJob.setCompletedAmount(currentJobAmount);
        }

        return new PlayerProfile.Builder(playerUUID)
                .withJob(playerJob)
                .withCompletedJobs(completedJobs)
                .withCoolDown(jobAcceptCoolDown)
                .withRank(playerRank)
                .jobCompleted(currentJobCompleted)
                .build();

    }

    public void deletePlayerProfile(UUID playerUUID) {
        playersDataAccessor.getConfig().set("Players." + playerUUID, null);
    }

    public static PlayersDataController getInstance() {
        if (instance == null) instance = new PlayersDataController();
        return instance;
    }

}
