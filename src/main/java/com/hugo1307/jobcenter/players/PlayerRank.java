package com.hugo1307.jobcenter.players;

import lombok.Getter;

public enum PlayerRank {

    BASIC("Basic"), ADVANCED("Advanced"), EXPERT("Expert");

    @Getter private final String alias;

    PlayerRank(String alias) {
        this.alias = alias;
    }

    public static PlayerRank fromString(String rank) {
        for (PlayerRank playerRank : PlayerRank.values())
            if (rank.equalsIgnoreCase(playerRank.toString())) return playerRank;
        return null;
    }

}
