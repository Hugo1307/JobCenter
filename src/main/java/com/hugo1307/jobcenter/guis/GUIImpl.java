package com.hugo1307.jobcenter.guis;

import com.hugo1307.jobcenter.caches.GUIHistoryCache;
import com.hugo1307.jobcenter.utils.ItemStackBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class GUIImpl implements GUI {

    private final String name;
    private final int size;
    private final Inventory inventory;

    public GUIImpl(String name, int size) {
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, this.size, this.name);
    }

    @Override
    public void build() {
       fill();
    }

    @Override
    public void show(Player player) {
        player.openInventory(this.inventory);
        GUIHistoryCache.getInstance().add(player.getUniqueId(), this);
    }

    @Override
    public void handleClick(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
    }

    @Override
    public String getName() {
        return this.name;
    }

    protected void addExitButton() {
        ItemStack backButton = new ItemStackBuilder.Builder(Material.REDSTONE_TORCH_ON)
                .name(ChatColor.RED + "Back").build().toItemStack();
        this.getInventory().setItem(40, backButton);
    }

    protected void fill() {

        ItemStack filler = new ItemStackBuilder.Builder(Material.STAINED_GLASS_PANE)
                .itemData((short)7).name(" ").build().toItemStack();

        for (int inventoryItemIdx = 0; inventoryItemIdx < this.size; inventoryItemIdx++)
            inventory.setItem(inventoryItemIdx, filler);

    }

}
