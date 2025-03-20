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
	private final String jobCompleted;
	private final String insufficientRank;
	private final String maxJobAmountReached;
	private final String playerNotFound;
	private final String playerRankFound;
	private final String unknownRank;
	private final String playerRankSet;
	private final String playerUnemployed;
	private final String paymentReceived;
	private final String unableToAcceptJob;
	
	private Messages() {
		
		this.pluginHeader = ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-= " + messagesConfig.getConfig().getString("PluginPrefix") + ChatColor.GRAY + " =-=-=-=-=-=-=-=-=-=-";
		 
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
		this.jobCompleted = messagesConfig.getConfig().getString("JobCompleted");
		this.insufficientRank = messagesConfig.getConfig().getString("InsufficientRank");
		this.maxJobAmountReached = messagesConfig.getConfig().getString("MaxJobAmountReached");
		this.playerNotFound = messagesConfig.getConfig().getString("PlayerNotFound");
		this.playerRankFound = messagesConfig.getConfig().getString("PlayerRankFound");
		this.unknownRank = messagesConfig.getConfig().getString("UnknownRank");
		this.playerRankSet = messagesConfig.getConfig().getString("PlayerRankSet");
		this.playerUnemployed = messagesConfig.getConfig().getString("PlayerUnemployed");
		this.paymentReceived = messagesConfig.getConfig().getString("PaymentReceived");
		this.unableToAcceptJob = messagesConfig.getConfig().getString("UnableToAcceptJob");

	}

	public static Messages getInstance() {
		if (instance == null) instance = new Messages();
		return instance;
	}

}