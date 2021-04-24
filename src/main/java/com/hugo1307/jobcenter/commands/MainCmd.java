package com.hugo1307.jobcenter.commands;

import com.hugo1307.jobcenter.guis.GUI;
import com.hugo1307.jobcenter.guis.JobsGUI;
import com.hugo1307.jobcenter.utils.Messages;
import com.hugo1307.jobcenter.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCmd implements PluginCmd {

    private static MainCmd instance;

    private final Messages messages = Messages.getInstance();

    private final CommandSender sender;
    private final String[] args;

    protected MainCmd(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runCmd() {

        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getPluginPrefix() + messages.getPlayerCmdOnly());
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(Permissions.MAIN_CMD.getPermission())) {
            sender.sendMessage(messages.getPluginPrefix() + messages.getNoPermission());
            return;
        }

        JobsGUI.getInstance().build();
        JobsGUI.getInstance().show(player);

    }

    @Override
    public CommandType getCmdType() {
        return CommandType.MAIN;
    }

}
