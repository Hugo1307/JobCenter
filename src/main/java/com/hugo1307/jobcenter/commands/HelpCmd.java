package com.hugo1307.jobcenter.commands;

import com.hugo1307.jobcenter.utils.Messages;
import com.hugo1307.jobcenter.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCmd implements PluginCmd {

    private final CommandSender sender;
    private final String[] args;

    public HelpCmd(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runCmd() {

        if (sender instanceof Player) {
            if (!sender.hasPermission(Permissions.HELP_CMD.getPermission())) {
                sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                return;
            }
        }

        sender.sendMessage(Messages.getInstance().getPluginHeader());
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GREEN + " • /jobcenter" + ChatColor.GRAY + " - Open the main GUI.");
        sender.sendMessage(ChatColor.GREEN + " • /jobcenter job" + ChatColor.GRAY + " - Show details about the current job.");
        sender.sendMessage(ChatColor.GREEN + " • /jobcenter help" + ChatColor.GRAY + " - Show this help page.");
        sender.sendMessage(ChatColor.GREEN + " • /jobcenter rank " + ChatColor.DARK_GRAY + "[Player]" + ChatColor.GRAY + " - Show the player's rank.");
        sender.sendMessage(ChatColor.GREEN + " • /jobcenter rank " + ChatColor.DARK_GRAY + "[Player] [Rank]" + ChatColor.GRAY + " - Set the player's rank.");
        sender.sendMessage(ChatColor.GREEN + " • /jobcenter reload" + ChatColor.GRAY + " - Reloads the plugin's configuration files.");
        sender.sendMessage("");
        sender.sendMessage(Messages.getInstance().getPluginFooter());

    }

    @Override
    public CommandType getCmdType() {
        return CommandType.HELP;
    }

}
