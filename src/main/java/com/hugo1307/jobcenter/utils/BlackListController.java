package com.hugo1307.jobcenter.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlackListController {

    private static BlackListController instance;

    private final DataAccessor blacklistAccessor = new DataAccessor(DataFile.BLACKLIST);

    private BlackListController() {}

    public void addPlayer(UUID playerUUID) {

        blacklistAccessor.reloadConfig();

        List<String> playersBlackListed = new ArrayList<>();

        if (blacklistAccessor.getConfig().contains("Blacklist"))
            playersBlackListed = blacklistAccessor.getConfig().getStringList("Blacklist");
        playersBlackListed.add(playerUUID.toString());

        blacklistAccessor.getConfig().set("Blacklist", playersBlackListed);
        blacklistAccessor.saveConfig();

    }

    public boolean containsPlayer(UUID playerUUID) {

        blacklistAccessor.reloadConfig();

        List<String> playersBlackListed;

        if (blacklistAccessor.getConfig().contains("Blacklist"))
            playersBlackListed = blacklistAccessor.getConfig().getStringList("Blacklist");
        else return false;

        return playersBlackListed.contains(playerUUID.toString());

    }

    public void removePlayer(UUID playerUUID) {

        blacklistAccessor.reloadConfig();

        List<String> playersBlackListed;

        if (blacklistAccessor.getConfig().contains("Blacklist"))
            playersBlackListed = blacklistAccessor.getConfig().getStringList("Blacklist");
        else return;

        playersBlackListed.remove(playerUUID.toString());

        blacklistAccessor.getConfig().set("Blacklist", playersBlackListed);
        blacklistAccessor.saveConfig();

    }

    public void clean() {
        blacklistAccessor.reloadConfig();
        blacklistAccessor.getConfig().set("Blacklist", new ArrayList<>());
        blacklistAccessor.saveConfig();
    }

    public static BlackListController getInstance() {
        if (instance == null) instance = new BlackListController();
        return instance;
    }
}
