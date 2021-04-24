package com.hugo1307.jobcenter.jobs;

import com.hugo1307.jobcenter.Main;
import com.hugo1307.jobcenter.utils.ConfigAccessor;
import com.hugo1307.jobcenter.utils.ConfigFile;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.*;

public class JobsDataController {

    private static JobsDataController instance;

    private final ConfigAccessor jobsConfigAccessor = new ConfigAccessor(ConfigFile.JOBS);

    private JobsDataController(){}

    public String getJobCategoryDescription(JobCategory jobCategory) {
        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Description"))
            return jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Description");
        else
            return null;
    }

    public Job getJob(JobCategory jobCategory, int id) {

        String jobName, jobDescription;
        JobType jobType;
        Material jobItemMaterial;
        int jobAmount, jobAvailability;
        double jobPayment;

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Name")) {
            jobName = jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Name");
        }else{
            jobName = "Unknown";
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Name for " + jobCategory.getConfigAlias() + " " + id);
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Description")) {
            jobDescription = jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Description");
        }else{
            jobDescription = "Unknown";
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Description for " + jobCategory.getConfigAlias() + " " + id);
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Type")) {
            try {
                jobType = JobType.valueOf(jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Type"));
            }catch (IllegalArgumentException iae) {
                Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error parsing Job Type for " + jobCategory.getConfigAlias() + " " + id);
                return null;
            }
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Type for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Item")) {
            jobItemMaterial = Material.matchMaterial(jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Item"));
            if (jobItemMaterial == null) {
                Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error parsing Job Item Material for " + jobCategory.getConfigAlias() + " " + id);
                return null;
            }
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Item Material for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".ItemAmount")) {
            jobAmount = jobsConfigAccessor.getConfig().getInt("Jobs." + jobCategory.getConfigAlias() + "." + id + ".ItemAmount");
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Item Amount for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Available")) {
            jobAvailability = jobsConfigAccessor.getConfig().getInt("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Available");
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Availability for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Payment")) {
            jobPayment = jobsConfigAccessor.getConfig().getDouble("Jobs." + jobCategory.getConfigAlias() + "." + id + ".Payment");
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Payment for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        return new Job.JobBuilder(id)
                    .withName(jobName)
                    .withDescription(jobDescription)
                    .ofType(jobType)
                    .ofCategory(jobCategory)
                    .withItem(jobItemMaterial)
                    .withRequiredAmount(jobAmount)
                    .withAvailableAmount(jobAvailability)
                    .withPayment(jobPayment)
                .build();
    }

    public List<Job> getAllJobs(JobCategory jobCategory) {

        Set<String> allJobsIds;
        List<Job> allJobsFromCategory = new ArrayList<>();

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias()))
            allJobsIds = jobsConfigAccessor.getConfig().getConfigurationSection("Jobs." + jobCategory.getConfigAlias()).getKeys(false);
        else
            return Collections.emptyList();

        for (String jobId : allJobsIds)
            if (StringUtils.isNumeric(jobId))
                allJobsFromCategory.add(getJob(jobCategory, Integer.parseInt(jobId)));
        allJobsFromCategory.removeIf(Objects::isNull);

        return allJobsFromCategory;

    }

    public static JobsDataController getInstance() {
        if (instance == null) instance = new JobsDataController();
        return instance;
    }

}
