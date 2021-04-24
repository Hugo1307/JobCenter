package com.hugo1307.jobcenter.players;

import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.utils.Savable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class PlayerProfile implements Savable {

    private final UUID uuid;
    @Setter private Job currentJob;
    @Setter private boolean isJobCompleted;
    @Setter private int completedJobs;
    @Setter private int acceptCoolDown;
    @Setter private PlayerRank playerRank;

    public static class Builder {

        private final UUID uuid;
        private Job currentJob;
        private boolean isJobCompleted;
        private int completedJobs;
        private int acceptCoolDown;
        private PlayerRank playerRank;

        public Builder(UUID uuid) {
            this.uuid = uuid;
        }

        public Builder withJob(Job job){
            this.currentJob = job;
            return this;
        }

        public Builder jobCompleted(boolean isJobCompleted) {
            this.isJobCompleted = isJobCompleted;
            return this;
        }

        public Builder withCompletedJobs(int completedJobs) {
            this.completedJobs = completedJobs;
            return this;
        }

        public Builder withCoolDown(int coolDown){
            this.acceptCoolDown = coolDown;
            return this;
        }

        public Builder withRank(PlayerRank rank) {
            this.playerRank = rank;
            return this;
        }


        public PlayerProfile build() {
            return new PlayerProfile(this);
        }

    }

    public PlayerProfile(Builder builder) {
        this.uuid = builder.uuid;
        this.currentJob = builder.currentJob;
        this.isJobCompleted = builder.isJobCompleted;
        this.completedJobs = builder.completedJobs;
        this.acceptCoolDown = builder.acceptCoolDown;
        if (builder.playerRank == null)
            this.playerRank = PlayerRank.BASIC;
        else
            this.playerRank = builder.playerRank;
    }

    @Override
    public void save() {
        PlayersDataController.getInstance().savePlayerProfile(this);
    }

    public void delete() {
        PlayersDataController.getInstance().deletePlayerProfile(this.uuid);
    }

}
