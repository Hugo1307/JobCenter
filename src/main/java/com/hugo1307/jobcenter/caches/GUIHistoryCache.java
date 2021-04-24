package com.hugo1307.jobcenter.caches;

import com.hugo1307.jobcenter.guis.GUI;

import java.util.*;

public class GUIHistoryCache {

    private static GUIHistoryCache instance;

    private final Map<UUID, Stack<GUI>> guiHistoric = new HashMap<>();

    private GUIHistoryCache(){}

    public void add(UUID uuid, GUI gui) {

        Stack<GUI> playerStack = new Stack<>();

        if (guiHistoric.containsKey(uuid))
            playerStack = guiHistoric.get(uuid);
        playerStack.push(gui);
        guiHistoric.put(uuid, playerStack);

    }

    public GUI getLastGui(UUID uuid) {
        if (guiHistoric.containsKey(uuid)) {
            guiHistoric.get(uuid).pop();
            return guiHistoric.get(uuid).pop();
        }
        return null;
    }

    public static GUIHistoryCache getInstance() {
        if (instance == null) instance = new GUIHistoryCache();
        return instance;
    }
}
