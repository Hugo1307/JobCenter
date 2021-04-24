package com.hugo1307.jobcenter.utils;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public class Messages {

	private static Messages instance;
	private final ConfigAccessor messagesConfig = new ConfigAccessor(ConfigFile.MESSAGES);

	private final String pluginHeader;
	private final String pluginFooter;
	private final String pluginPrefix;
	private final String noPermission;
	private final String missingArguments;
	private final String wrongArguments;
	private final String unknownCommand;
	private final String unknownError;
	private final String reloadConfig;
	private final String playerCmdOnly;
	private final String alreadyHasJob;
	private final String jobSelected;
	private final String unableToFindJob;
	private final String completedJobPercentage;
	
	private Messages() {
		
		this.pluginHeader = ChatColor.GRAY + "-=-=-=-=-=-=-=-=-= " + messagesConfig.getConfig().getString("PluginPrefix") + " =-=-=-=-=-=-=-=-=- ";
		 
		this.pluginFooter = ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-";
		
		this.pluginPrefix = messagesConfig.getConfig().getString("ChatPrefix");

		this.noPermission = messagesConfig.getConfig().getString("NoPermission");
		this.missingArguments = messagesConfig.getConfig().getString("MissingArguments");
		this.wrongArguments = messagesConfig.getConfig().getString("WrongArguments");
		this.unknownError = messagesConfig.getConfig().getString("UnknownError");
		this.unknownCommand = messagesConfig.getConfig().getString("UnknownCommand");
		this.reloadConfig = messagesConfig.getConfig().getString("ReloadConfig");
		this.playerCmdOnly = messagesConfig.getConfig().getString("PlayerCmdOnly");
		this.alreadyHasJob = messagesConfig.getConfig().getString("AlreadyHasJob");
		this.jobSelected = messagesConfig.getConfig().getString("JobSelected");
		this.unableToFindJob = messagesConfig.getConfig().getString("UnableToFindJob");
		this.completedJobPercentage = messagesConfig.getConfig().getString("CompletedJobPercentage");

	}

	public static Messages getInstance() {
		if (instance == null) instance = new Messages();
		return instance;
	}

}