package com.hugo1307.jobcenter;

import com.hugo1307.jobcenter.commands.CmdHandler;
import com.hugo1307.jobcenter.events.BlockBreak;
import com.hugo1307.jobcenter.events.InventoryClick;
import com.hugo1307.jobcenter.utils.ConfigAccessor;
import com.hugo1307.jobcenter.utils.ConfigFile;
import com.hugo1307.jobcenter.utils.DataAccessor;
import com.hugo1307.jobcenter.utils.DataFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    private final String pluginPrefix = "[JobCenter] ";

    @Override
    public void onEnable() {

        instance = this;

        // Register main command
        getCommand("jobcenter").setExecutor(new CmdHandler());
        loadConfigFiles();
        loadDataFiles();
        registerEvents();

        Bukkit.getLogger().info(pluginPrefix + "Plugin successfully enabled.");

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(pluginPrefix + "Plugin successfully disabled.");
    }

    public void loadConfigFiles() {
        if(!new File(getDataFolder(), ConfigFile.CONFIG.getFileName()).exists()) saveDefaultConfig();
        if(!new File(getDataFolder(), ConfigFile.JOBS.getFileName()).exists()) new ConfigAccessor(ConfigFile.JOBS).saveDefaultConfig();
        if(!new File(getDataFolder(), ConfigFile.MESSAGES.getFileName()).exists()) new ConfigAccessor(ConfigFile.MESSAGES).saveDefaultConfig();
    }

    public void loadDataFiles() {
        if (!new File(getDataFolder(), ConfigFile.JOBS.getFileName()).exists()) new DataAccessor(DataFile.PLAYERS).saveDefaultConfig();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

}
