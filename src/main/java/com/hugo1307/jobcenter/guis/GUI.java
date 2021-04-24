package com.hugo1307.jobcenter.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GUI {

    void build();
    void show(Player player);
    void handleClick(InventoryClickEvent inventoryClickEvent);
    String getName();

}
