package com.hugo1307.jobcenter.guis;

import com.hugo1307.jobcenter.caches.GUIHistoryCache;
import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobsDataController;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.ItemStackBuilder;
import com.hugo1307.jobcenter.jobs.JobCategory;
import com.hugo1307.jobcenter.jobs.JobsConfigController;
import com.hugo1307.jobcenter.utils.MainConfigController;
import com.hugo1307.jobcenter.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.*;

public class AdvancedJobsGUI extends GUIImpl {

    private static AdvancedJobsGUI instance;

    private Map<ItemStack, Integer> jobsIds = new HashMap<>();

    private AdvancedJobsGUI() {
        super(ChatColor.GREEN + "" + ChatColor.BOLD + "Advanced Jobs", 9*5);
    }

    @Override
    public void build() {

        List<Job> allAdvancedJobs = JobsConfigController.getInstance().getAllJobs(JobCategory.ADVANCED_JOB);
        Queue<ItemStack> itemsToDisplay = new LinkedList<>();

        for (Job advancedJob : allAdvancedJobs) {

            List<String> jobIconLore = new ArrayList<>();

            jobIconLore.add("");
            jobIconLore.add(ChatColor.GRAY + advancedJob.getDescription());
            jobIconLore.add("");
            jobIconLore.add(ChatColor.GREEN + "Payment: " + ChatColor.GRAY + advancedJob.getPayment());

            ItemStack jobIcon = new ItemStackBuilder.Builder(advancedJob.getItemMaterial())
                    .name(advancedJob.getName())
                    .lore(jobIconLore)
                .build().toItemStack();
            itemsToDisplay.add(jobIcon);
            jobsIds.put(jobIcon, advancedJob.getId());

        }

        super.fill();

        for (int inventoryRow = 1; inventoryRow < 4; inventoryRow++) {
            for (int inventoryIdx = 1; inventoryIdx < 8; inventoryIdx++) {
                super.getInventory().setItem((inventoryRow*9)+inventoryIdx, itemsToDisplay.poll());
            }
        }

        super.addExitButton();

    }

    @Override
    public void handleClick(InventoryClickEvent inventoryClickEvent) {
        super.handleJobGuiClick(inventoryClickEvent, jobsIds, JobCategory.ADVANCED_JOB);
    }

    public static AdvancedJobsGUI getInstance() {
        if (instance == null) instance = new AdvancedJobsGUI();
        return instance;
    }

}
