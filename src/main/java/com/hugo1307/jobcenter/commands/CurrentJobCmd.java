package com.hugo1307.jobcenter.commands;

import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.Messages;
import com.hugo1307.jobcenter.utils.Permissions;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class CurrentJobCmd implements PluginCmd {

    private final CommandSender sender;
    private final String[] args;

    public CurrentJobCmd(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void runCmd() {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getPlayerCmdOnly());
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(Permissions.CURRENT_JOB_CMD.getPermission())) {
            player.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getNoPermission());
            return;
        }

        PlayerProfile playerProfile = PlayersDataController.getInstance().loadPlayerProfile(player.getUniqueId());

        if (playerProfile == null) {
            player.sendMessage(Messages.getInstance().getPluginPrefix() + MessageFormat.format(Messages.getInstance().getPlayerNotFound(), ChatColor.GREEN + player.getName() + ChatColor.GRAY));
            return;
        }

        Job playerCurrentJob = playerProfile.getCurrentJob();

        if (playerCurrentJob == null) {
            player.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getPlayerUnemployed());
            return;
        }

        player.sendMessage(Messages.getInstance().getPluginHeader());
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " " + playerCurrentJob.getName());
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "  • " + playerCurrentJob.getDescription());
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "    • Required Item: " + ChatColor.GRAY + playerCurrentJob.getItemMaterial().toString());
        player.sendMessage(ChatColor.GREEN + "    • Required Amount: " + ChatColor.GRAY + playerCurrentJob.getRequiredAmount());
        player.sendMessage(ChatColor.GREEN + "    • Completed Amount: " + ChatColor.GRAY + playerCurrentJob.getCompletedAmount());
        player.sendMessage(ChatColor.GREEN + "    • Payment: " + ChatColor.GRAY + Math.round(playerCurrentJob.getPayment()) + "$");
        player.sendMessage("");
        player.sendMessage(Messages.getInstance().getPluginFooter());

    }

    @Override
    public CommandType getCmdType() {
        return CommandType.CURRENT_JOB;
    }
}
