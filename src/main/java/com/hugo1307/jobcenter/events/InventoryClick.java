package com.hugo1307.jobcenter.events;

import com.hugo1307.jobcenter.guis.GUI;
import com.hugo1307.jobcenter.guis.GUIType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;

        for (GUIType guiType : GUIType.values()) {
            GUI gui = guiType.getGuiInstance();
            if (event.getView().getTitle().equals(gui.getName()))
                gui.handleClick(event);
        }

    }

}
