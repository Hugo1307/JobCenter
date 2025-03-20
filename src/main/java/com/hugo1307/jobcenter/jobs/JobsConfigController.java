package com.hugo1307.jobcenter.jobs;

import com.hugo1307.jobcenter.Main;
import com.hugo1307.jobcenter.utils.ConfigAccessor;
import com.hugo1307.jobcenter.utils.ConfigFile;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.*;

public class JobsConfigController {

    private static JobsConfigController instance;

    private final ConfigAccessor jobsConfigAccessor = new ConfigAccessor(ConfigFile.JOBS);

    private JobsConfigController(){}

    public String getJobCategoryDescription(JobCategory jobCategory) {
        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Description"))
            return jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Description");
        else
            return null;
    }

    public Job getJob(JobCategory jobCategory, int id) {

        String jobName, jobDescription, jobCommand;
        JobType jobType;
        Material jobItemMaterial;
        int jobAmount, jobAvailability;
        double jobPayment;

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Name")) {
            jobName = jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Name");
        }else{
            jobName = "Unknown";
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Name for " + jobCategory.getConfigAlias() + " " + id);
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Description")) {
            jobDescription = jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Description");
        }else{
            jobDescription = "Unknown";
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Description for " + jobCategory.getConfigAlias() + " " + id);
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Type")) {
            try {
                jobType = JobType.valueOf(jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Type"));
            }catch (IllegalArgumentException iae) {
                Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error parsing Job Type for " + jobCategory.getConfigAlias() + " " + id);
                return null;
            }
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Type for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Item")) {
            jobItemMaterial = Material.matchMaterial(jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Item"));
            if (jobItemMaterial == null) {
                Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error parsing Job Item Material for " + jobCategory.getConfigAlias() + " " + id);
                return null;
            }
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Item Material for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".ItemAmount")) {
            jobAmount = jobsConfigAccessor.getConfig().getInt("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".ItemAmount");
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Item Amount for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Available")) {
            jobAvailability = jobsConfigAccessor.getConfig().getInt("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Available");
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Availability for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Payment")) {
            jobPayment = jobsConfigAccessor.getConfig().getDouble("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".Payment");
        }else{
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Payment for " + jobCategory.getConfigAlias() + " " + id);
            return null;
        }

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".RewardCmd")) {
            jobCommand = jobsConfigAccessor.getConfig().getString("Jobs." + jobCategory.getConfigAlias() + ".Jobs." + id + ".RewardCmd");
        }else{
            jobCommand = null;
            Bukkit.getLogger().warning(Main.getInstance().getPluginPrefix() + "Error retrieving Job Command for " + jobCategory.getConfigAlias() + " " + id);
        }

        return new Job.JobBuilder(id)
                    .withName(jobName)
                    .withDescription(jobDescription)
                    .withCommand(jobCommand)
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

        if (jobsConfigAccessor.getConfig().contains("Jobs." + jobCategory.getConfigAlias() + ".Jobs"))
            allJobsIds = jobsConfigAccessor.getConfig().getConfigurationSection("Jobs." + jobCategory.getConfigAlias() + ".Jobs").getKeys(false);
        else
            return Collections.emptyList();

        for (String jobId : allJobsIds)
            if (StringUtils.isNumeric(jobId))
                allJobsFromCategory.add(getJob(jobCategory, Integer.parseInt(jobId)));
        allJobsFromCategory.removeIf(Objects::isNull);

        return allJobsFromCategory;

    }

    public static JobsConfigController getInstance() {
        if (instance == null) instance = new JobsConfigController();
        return instance;
    }

}
