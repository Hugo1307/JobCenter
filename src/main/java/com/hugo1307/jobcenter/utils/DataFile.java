package com.hugo1307.jobcenter.utils;

import lombok.Getter;

public enum DataFile {

    PLAYERS("players.yml");

    @Getter
    private final String fileName;

    DataFile(String fileName) {
        this.fileName = fileName;
    }

}
