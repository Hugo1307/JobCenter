package com.hugo1307.jobcenter.commands;

import com.hugo1307.jobcenter.Main;
import com.hugo1307.jobcenter.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            new MainCmd(sender, args).runCmd();
        }else {

            CommandType commandType = CommandType.fromArgs(args[0]);

            if (commandType != null) {

                PluginCmd pluginCmd;

                try{
                    pluginCmd = (PluginCmd) Class.forName(this.getClass().getPackage().getName()  + "." + commandType.getClassName())
                            .getConstructor(CommandSender.class, String[].class).newInstance(sender, args);
                }catch (Exception e) {
                    Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error while obtaining plugin's command class controller.");
                    e.printStackTrace();
                    return false;
                }

                pluginCmd.runCmd();

            }else{
                sender.sendMessage(Messages.getInstance().getPluginPrefix() + Messages.getInstance().getUnknownCommand());
            }

        }

        return false;
    }

}
