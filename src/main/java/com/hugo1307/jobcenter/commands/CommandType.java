package com.hugo1307.jobcenter.commands;

import lombok.Getter;

public enum CommandType {

    MAIN("", "MainCmd"),
    RELOAD("reload", "ReloadCmd"),
    RANK("rank", "RankCmd"),
    CURRENT_JOB("job", "CurrentJobCmd"),
    HELP("help", "HelpCmd");

    @Getter
    private final String args;
    @Getter
    private final String className;

    CommandType(String args, String className) {
        this.args = args;
        this.className = className;
    }

    public static CommandType fromArgs(String args) {
        for (CommandType commandType : CommandType.values())
            if (commandType.getArgs().equalsIgnoreCase(args)) return commandType;
        return null;
    }

}
