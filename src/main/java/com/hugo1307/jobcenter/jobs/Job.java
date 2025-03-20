package com.hugo1307.jobcenter.jobs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Builder(setterPrefix = "with")
@Getter
public class Job {

    private final int id;
    private final String name;
    private final String description;
    private final String rewardCommand;
    private final JobType type;
    private final JobCategory category;
    private final Material itemMaterial;
    @Setter private int completedAmount;
    @Setter private int requiredAmount;
    @Setter private double payment;
    private final int available;

    public int addCompletedAmount(int completedAmount) {
        this.completedAmount += completedAmount;
        return this.completedAmount;
    }

    public String getDescription() {
        return description.replaceAll("\\{amount}", String.valueOf(requiredAmount)).replaceAll("\\{item}", itemMaterial.toString());
    }

}
