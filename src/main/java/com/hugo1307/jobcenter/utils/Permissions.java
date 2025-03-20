package com.hugo1307.jobcenter.utils;

import lombok.Getter;

public enum Permissions {

    BASIC_JOBS("jobcenter.jobs.basic"),
    ADVANCED_JOBS("jobcenter.jobs.advanced"),
    EXPERT_JOBS("jobcenter.jobs.expert"),
    MAIN_CMD("jobcenter.commands.main"),
    RELOAD_CMD("jobcenter.commands.reload"),
    CHECK_RANK_CMD("jobcenter.commands.rank.check"),
    SET_RANK_CMD("jobcenter.commands.rank.set"),
    CURRENT_JOB_CMD("jobcenter.commands.job"),
    HELP_CMD("jobcenter.commands.help");

    @Getter
    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

}
