package com.hugo1307.jobcenter.jobs;

import lombok.Getter;

public enum JobCategory {

    BASIC_JOB("BasicJobs"), ADVANCED_JOB("AdvancedJobs"), EXPERT_JOB("ExpertJobs");

    @Getter
    private final String configAlias;

    JobCategory(String configAlias) {
        this.configAlias = configAlias;
    }

}
