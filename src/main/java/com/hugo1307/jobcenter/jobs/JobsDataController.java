package com.hugo1307.jobcenter.jobs;

import com.hugo1307.jobcenter.utils.DataAccessor;
import com.hugo1307.jobcenter.utils.DataFile;

import java.util.Set;
import java.util.stream.Collectors;

public class JobsDataController {

    private static JobsDataController instance;

    private final DataAccessor jobsData = new DataAccessor(DataFile.JOBS);

    private JobsDataController() {}

    public void setJobsTaken(JobCategory jobCategory, int jobId, int amount) {
        jobsData.reloadConfig();
        jobsData.getConfig().set("JobsData." + jobCategory.getConfigAlias() + "." + jobId + ".AmountTaken", amount);
        jobsData.saveConfig();
    }

    public int getJobsTaken(JobCategory jobCategory, int jobId) {

        jobsData.reloadConfig();

        Set<Integer> jobsIds;

        if (jobsData.getConfig().contains("JobsData." + jobCategory.getConfigAlias()))
            jobsIds  = jobsData.getConfig().getConfigurationSection("JobsData." + jobCategory.getConfigAlias()).getKeys(false).stream().map(Integer::parseInt).collect(Collectors.toSet());
        else return 0;

        for (int currentJobId : jobsIds) {
            if (currentJobId == jobId) {
                if (jobsData.getConfig().contains("JobsData." + jobCategory.getConfigAlias() + "." + jobId + ".AmountTaken"))
                    return jobsData.getConfig().getInt("JobsData." + jobCategory.getConfigAlias() + "." + jobId + ".AmountTaken");
                else return 0;
            }
        }

        return 0;

    }

    public void resetJobsData() {

        jobsData.reloadConfig();

        for (JobCategory jobCategory : JobCategory.values()) {

            Set<Integer> jobsIds;

            if (jobsData.getConfig().contains("JobsData." + jobCategory.getConfigAlias())) {

                jobsIds = jobsData.getConfig().getConfigurationSection("JobsData." + jobCategory.getConfigAlias()).getKeys(false).stream().map(Integer::parseInt).collect(Collectors.toSet());

                for (int jobId : jobsIds) {
                    jobsData.getConfig().set("JobsData." + jobCategory.getConfigAlias() + "." + jobId + ".AmountTaken", 0);
                    jobsData.saveConfig();
                }

            }

        }

    }

    public static JobsDataController getInstance() {
        if (instance == null) instance = new JobsDataController();
        return instance;
    }

}
