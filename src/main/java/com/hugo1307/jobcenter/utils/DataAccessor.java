package com.hugo1307.jobcenter.utils;

import com.hugo1307.jobcenter.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataAccessor {

    private final String fileName;
    private final File configFile;
    private FileConfiguration fileConfiguration;

    public DataAccessor(DataFile dataFile) {

        if (Main.getInstance() == null)
            throw new IllegalArgumentException("Error initializing ConfigAccessor. The plugin cannot be null.");

        File dataFolder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Data");
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdirs())
                throw new IllegalStateException(Main.getInstance().getPluginPrefix() + "Unable to create data folder.");
        }

        this.fileName = dataFile.getFileName();
        this.configFile = new File(dataFolder, fileName);

    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = Main.getInstance().getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            fileConfiguration.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) this.reloadConfig();
        return fileConfiguration;
    }

    public void saveConfig() {
        if (fileConfiguration != null && configFile != null) {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }

    public void saveDefaultConfig() {
        if (!configFile.exists()) Main.getInstance().saveResource(fileName, false);
    }

}
