package com.hugo1307.jobcenter.guis;

import lombok.Getter;

public enum GUIType {

    MAIN(JobsGUI.getInstance()), BASIC_JOBS(BasicJobsGUI.getInstance()), ADVANCED_JOBS(AdvancedJobsGUI.getInstance()),
    EXPERT_JOBS(ExpertJobsGUI.getInstance());

    @Getter
    private final GUI guiInstance;

    GUIType(GUI guiInstance) {
        this.guiInstance = guiInstance;
    }

}
