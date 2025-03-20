package com.hugo1307.jobcenter.players;

import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.utils.Savable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
public class PlayerProfile implements Savable {

    private final UUID uuid;
    @Setter private Job currentJob;
    @Setter private int completedJobs;
    @Setter private int acceptCoolDown;
    @Setter private PlayerRank playerRank;

    @Override
    public void save() {
        PlayersDataController.getInstance().savePlayerProfile(this);
    }

    public void delete() {
        PlayersDataController.getInstance().deletePlayerProfile(this.uuid);
    }

    public void resetJob() {
        this.currentJob = null;
        PlayersDataController.getInstance().resetPlayerJob(this.uuid);
    }

}
