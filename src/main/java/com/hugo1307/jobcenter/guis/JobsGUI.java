package com.hugo1307.jobcenter.guis;

import com.hugo1307.jobcenter.jobs.JobCategory;
import com.hugo1307.jobcenter.jobs.JobsDataController;
import com.hugo1307.jobcenter.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobsGUI extends GUIImpl {

    private static JobsGUI instance;

    private final List<String> itemsDescription = new ArrayList<>();

    private JobsGUI() {
        super(ChatColor.BLACK + "" + ChatColor.BOLD + "Job Center", 9*3);
    }

    @Override
    public void build() {

        for (JobCategory jobCategory : JobCategory.values())
            this.itemsDescription.add(JobsDataController.getInstance().getJobCategoryDescription(jobCategory));

        ItemStack filler = new ItemStackBuilder.Builder(Material.STAINED_GLASS_PANE)
                .itemData((short)7).name(" ").build().toItemStack();

        ItemStack basicJobsItem = new ItemStackBuilder.Builder(Material.WOOD)
                .name(ChatColor.GRAY + "Basic Jobs").lore(Collections.singletonList(itemsDescription.get(0))).build().toItemStack();

        ItemStack advancedJobsItem = new ItemStackBuilder.Builder(Material.EMERALD_BLOCK)
                .name(ChatColor.GREEN + "Advanced Jobs").lore(Collections.singletonList(itemsDescription.get(1))).build().toItemStack();

        ItemStack expertJobsItem = new ItemStackBuilder.Builder(Material.DIAMOND_BLOCK)
                .name(ChatColor.GOLD + "Expert Jobs").lore(Collections.singletonList(itemsDescription.get(2))).build().toItemStack();

        for (int inventoryItemIdx = 0; inventoryItemIdx < super.getSize(); inventoryItemIdx++)
            super.getInventory().setItem(inventoryItemIdx, filler);

        super.getInventory().setItem(10, basicJobsItem);
        super.getInventory().setItem(13, advancedJobsItem);
        super.getInventory().setItem(16, expertJobsItem);

    }

    @Override
    public void handleClick(InventoryClickEvent event) {

        ItemStack clickedItem = event.getCurrentItem();
        Player whoClicked = (Player) event.getWhoClicked();

        event.setCancelled(true);

        switch (clickedItem.getType()) {
            case WOOD:
                BasicJobsGUI.getInstance().build();
                BasicJobsGUI.getInstance().show(whoClicked);
                break;
            case EMERALD_BLOCK:
                AdvancedJobsGUI.getInstance().build();
                AdvancedJobsGUI.getInstance().show(whoClicked);
                break;
            case DIAMOND_BLOCK:
                ExpertJobsGUI.getInstance().build();
                ExpertJobsGUI.getInstance().show(whoClicked);
                break;
        }

    }

    public static JobsGUI getInstance() {
        if (instance == null) instance = new JobsGUI();
        return instance;
    }

}
