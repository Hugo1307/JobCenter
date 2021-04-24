package com.hugo1307.jobcenter.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private final Material material;
    private final int amount;
    private final String name;
    private final List<String> lore;
    private final short itemData;

    public static class Builder {

        private final Material material;

        private int amount = 1;
        private String name = null;
        private List<String> lore = new ArrayList<>();
        private short itemData = -1;

        public Builder(Material material) {
            this.material = material;
        }

        public Builder amount(int val) {
            this.amount = val;
            return this;
        }

        public Builder name(String val) {
            this.name = val;
            return this;
        }

        public Builder lore(List<String> val) {
            this.lore = val;
            return this;
        }

        public Builder itemData(short val) {
            this.itemData = val;
            return this;
        }

        public ItemStackBuilder build() {
            return new ItemStackBuilder(this);
        }

    }

    private ItemStackBuilder(Builder builder) {
        this.material = builder.material;
        this.name = builder.name;
        this.amount = builder.amount;
        this.lore = builder.lore;
        this.itemData = builder.itemData;
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(this.material, this.amount);
        if (itemData != -1)
            item = new ItemStack(this.material, this.amount, this.itemData);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        item.setItemMeta(itemMeta);
        return item;
    }

}
