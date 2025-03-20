package com.hugo1307.jobcenter.commands;

import com.hugo1307.jobcenter.utils.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCmd implements PluginCmd {

    private final CommandSender sender;
    private final String[] args;

    public ReloadCmd(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runCmd() {

        if (sender instanceof Player) {
            if (!sender.hasPermission(Permissions.RELOAD_CMD.getPermission())) {
                sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                return;
            }
        }

        for (ConfigFile configFile : ConfigFile.values()) {
            ConfigAccessor config = new ConfigAccessor(configFile);
            config.reloadConfig();
            config.saveConfig();
        }

        for (DataFile dataFile : DataFile.values()) {
            DataAccessor data = new DataAccessor(dataFile);
            data.reloadConfig();
            data.saveConfig();
        }

        sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getReloadConfig());

    }

    @Override
    public CommandType getCmdType() {
        return CommandType.RELOAD;
    }

}
