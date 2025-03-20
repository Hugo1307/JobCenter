package com.hugo1307.jobcenter.events;

import com.hugo1307.jobcenter.guis.GUI;
import com.hugo1307.jobcenter.guis.GUIType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) return;

        if (clickedInventory.getName() == null) return;

        for (GUIType guiType : GUIType.values()) {
            GUI gui = guiType.getGuiInstance();
            if (clickedInventory.getName().equalsIgnoreCase(gui.getName()))
                gui.handleClick(event);
        }

    }

}
