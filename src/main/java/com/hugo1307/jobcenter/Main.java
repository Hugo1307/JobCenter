package com.hugo1307.jobcenter;

import com.hugo1307.jobcenter.commands.CmdHandler;
import com.hugo1307.jobcenter.events.BlockBreak;
import com.hugo1307.jobcenter.events.CraftItem;
import com.hugo1307.jobcenter.events.InventoryClick;
import com.hugo1307.jobcenter.runnables.WeeklyRunnable;
import com.hugo1307.jobcenter.utils.ConfigAccessor;
import com.hugo1307.jobcenter.utils.ConfigFile;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Main extends JavaPlugin {

    private static Main instance;
    private Economy econ;
    private final String pluginPrefix = "[JobCenter] ";

    @Override
    public void onEnable() {

        instance = this;

        // Register main command
        getCommand("jobcenter").setExecutor(new CmdHandler());
        loadConfigFiles();
        registerEvents();

        new WeeklyRunnable().runTaskTimerAsynchronously(this, 0, 20*45L);

        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(pluginPrefix + "Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

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

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new CraftItem(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Main getInstance() {
        return instance;
    }

}
