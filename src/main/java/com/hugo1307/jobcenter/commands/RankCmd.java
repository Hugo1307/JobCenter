package com.hugo1307.jobcenter.commands;

import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayerRank;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.Messages;
import com.hugo1307.jobcenter.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class RankCmd implements PluginCmd {

    private final CommandSender sender;
    private final String[] args;

    public RankCmd(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runCmd() {

        if (args.length == 2) {

            if (sender instanceof Player) {
                if (!sender.hasPermission(Permissions.CHECK_RANK_CMD.getPermission())) {
                    sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                    return;
                }
            }

            OfflinePlayer playerToCheck = Bukkit.getServer().getOfflinePlayer(args[1]);
            PlayerProfile playerToCheckProfile = PlayersDataController.getInstance().loadPlayerProfile(playerToCheck.getUniqueId());

            if (playerToCheckProfile == null) {
                sender.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getPlayerNotFound(), ChatColor.GREEN + args[1] + ChatColor.GRAY));
                return;
            }

            PlayerRank playerToCheckRank = playerToCheckProfile.getPlayerRank();

            sender.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getPlayerRankFound().replaceAll("'", "''"), ChatColor.GREEN + playerToCheck.getName() + ChatColor.GRAY, ChatColor.GREEN + playerToCheckRank.toString().toLowerCase() + ChatColor.GRAY));

        }else if (args.length > 2) {

            if (sender instanceof Player) {
                if (!sender.hasPermission(Permissions.SET_RANK_CMD.getPermission())) {
                    sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
                    return;
                }
            }

            OfflinePlayer playerToSet = Bukkit.getServer().getOfflinePlayer(args[1]);
            PlayerProfile playerToSetProfile = PlayersDataController.getInstance().loadPlayerProfile(playerToSet.getUniqueId());

            if (playerToSetProfile == null) {
                sender.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getPlayerNotFound(), ChatColor.GREEN + args[1] + ChatColor.GRAY));
                return;
            }

            PlayerRank playerRankToSet = PlayerRank.fromString(args[2]);

            if (playerRankToSet == null) {
                sender.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getUnknownRank(), ChatColor.GREEN + args[2] + ChatColor.GRAY));
                return;
            }

            playerToSetProfile.setPlayerRank(playerRankToSet);
            playerToSetProfile.save();

            sender.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getPlayerRankSet(), ChatColor.GREEN + playerRankToSet.toString().toLowerCase() + ChatColor.GRAY, ChatColor.GREEN + playerToSet.getName() + ChatColor.GRAY));

        }else{
            sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getMissingArguments());
        }

    }

    @Override
    public CommandType getCmdType() {
        return CommandType.RANK;
    }

}
