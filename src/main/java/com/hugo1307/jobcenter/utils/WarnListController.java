package com.hugo1307.jobcenter.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarnListController {

    private static WarnListController instance;

    private WarnListController() {}

    private final DataAccessor warnListAccessor = new DataAccessor(DataFile.WARNLIST);

    public void addPlayer(UUID playerUUID) {

        warnListAccessor.reloadConfig();

        List<String> playersBlackListed = new ArrayList<>();

        if (warnListAccessor.getConfig().contains("WarnList"))
            playersBlackListed = warnListAccessor.getConfig().getStringList("WarnList");
        playersBlackListed.add(playerUUID.toString());

        warnListAccessor.getConfig().set("WarnList", playersBlackListed);
        warnListAccessor.saveConfig();

    }

    public boolean containsPlayer(UUID playerUUID) {

        warnListAccessor.reloadConfig();

        List<String> playersBlackListed;

        if (warnListAccessor.getConfig().contains("WarnList"))
            playersBlackListed = warnListAccessor.getConfig().getStringList("WarnList");
        else return false;

        return playersBlackListed.contains(playerUUID.toString());

    }

    public void removePlayer(UUID playerUUID) {

        warnListAccessor.reloadConfig();

        List<String> playersBlackListed;

        if (warnListAccessor.getConfig().contains("WarnList"))
            playersBlackListed = warnListAccessor.getConfig().getStringList("WarnList");
        else return;

        playersBlackListed.remove(playerUUID.toString());

        warnListAccessor.getConfig().set("WarnList", playersBlackListed);
        warnListAccessor.saveConfig();

    }
    
    public static WarnListController getInstance() {
        if (instance == null) instance = new WarnListController();
        return instance;
    }

}
