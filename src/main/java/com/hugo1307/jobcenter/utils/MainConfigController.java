package com.hugo1307.jobcenter.utils;

import com.hugo1307.jobcenter.Main;
import com.hugo1307.jobcenter.players.PlayerRank;
import org.bukkit.Bukkit;

import java.time.DayOfWeek;
import java.util.logging.Level;

public class MainConfigController {

    private static MainConfigController instance;

    private MainConfigController(){}

    public int getCompletedJobsForRank(PlayerRank playerRank) {

        Main.getInstance().reloadConfig();

        if (Main.getInstance().getConfig().contains("JobCenter.JobsRank." + playerRank.getAlias()))
            return Main.getInstance().getConfig().getInt("JobCenter.JobsRank." + playerRank.getAlias());
        else {
            Bukkit.getLogger().log(Level.WARNING, Main.getInstance().getPluginPrefix() + "Unable to get amount of jobs needed for rank " + playerRank.getAlias());
            return -1;
        }
    }

    public int getWeekResetDay() {
        Main.getInstance().reloadConfig();
        if (Main.getInstance().getConfig().contains("JobCenter.JobWeek.StartDay"))
            return Main.getInstance().getConfig().getInt("JobCenter.JobWeek.StartDay");
        else return 1;
    }

    public static MainConfigController getInstance() {
        if (instance == null) instance = new MainConfigController();
        return instance;
    }
}
