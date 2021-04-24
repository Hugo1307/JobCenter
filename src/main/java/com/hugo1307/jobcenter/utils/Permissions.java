package com.hugo1307.jobcenter.utils;

import lombok.Getter;

public enum Permissions {

    MAIN_CMD("jobcenter.commands.main");

    @Getter
    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

}
