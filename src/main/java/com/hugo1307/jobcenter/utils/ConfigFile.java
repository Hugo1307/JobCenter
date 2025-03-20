package com.hugo1307.jobcenter.utils;

import lombok.Getter;

public enum ConfigFile {

    CONFIG("config.yml"), MESSAGES("messages.yml"), JOBS("jobs.yml");

    @Getter
    private final String fileName;

    ConfigFile(String fileName) {
        this.fileName = fileName;
    }

}
