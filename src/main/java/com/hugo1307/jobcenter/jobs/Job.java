package com.hugo1307.jobcenter.jobs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

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

    public static class JobBuilder {

        private final int id;
        private String name;
        private String description;
        private String rewardCommand;
        private JobType type;
        private JobCategory category;
        private Material itemMaterial;
        private int requiredAmount;
        private double payment;
        private int available;

        public JobBuilder(int id) {
            this.id = id;
        }

        public JobBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public JobBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public JobBuilder withCommand(String command) {
            this.rewardCommand = command;
            return this;
        }

        public JobBuilder ofType(JobType type) {
            this.type = type;
            return this;
        }

        public JobBuilder ofCategory(JobCategory category) {
            this.category = category;
            return this;
        }

        public JobBuilder withItem(Material item) {
            this.itemMaterial = item;
            return this;
        }

        public JobBuilder withRequiredAmount(int amount) {
            this.requiredAmount = amount;
            return this;
        }

        public JobBuilder withPayment(double payment) {
            this.payment = payment;
            return this;
        }

        public JobBuilder withAvailableAmount(int availableAmount) {
            this.available = availableAmount;
            return this;
        }

        public Job build() {
            return new Job(this);
        }

    }

    public Job(JobBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.rewardCommand = builder.rewardCommand;
        this.type = builder.type;
        this.category = builder.category;
        this.itemMaterial = builder.itemMaterial;
        this.requiredAmount = builder.requiredAmount;
        this.payment = builder.payment;
        this.available = builder.available;
    }

    public String getDescription() {
        return description.replaceAll("\\{amount}", String.valueOf(requiredAmount)).replaceAll("\\{item}", itemMaterial.toString());
    }

}
