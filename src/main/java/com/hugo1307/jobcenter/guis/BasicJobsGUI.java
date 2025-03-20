package com.hugo1307.jobcenter.guis;

import com.hugo1307.jobcenter.caches.GUIHistoryCache;
import com.hugo1307.jobcenter.jobs.Job;
import com.hugo1307.jobcenter.jobs.JobsDataController;
import com.hugo1307.jobcenter.players.PlayerProfile;
import com.hugo1307.jobcenter.players.PlayersDataController;
import com.hugo1307.jobcenter.utils.ItemStackBuilder;
import com.hugo1307.jobcenter.jobs.JobCategory;
import com.hugo1307.jobcenter.jobs.JobsConfigController;
import com.hugo1307.jobcenter.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.*;

public class BasicJobsGUI extends GUIImpl {

    private static BasicJobsGUI instance;

    private final Map<ItemStack, Integer> jobsIds = new HashMap<>();

    private BasicJobsGUI() {
        super(ChatColor.GRAY + "" + ChatColor.BOLD + "Basic Jobs", 9*5);
    }

    @Override
    public void build() {

        List<Job> allBasicJobs = JobsConfigController.getInstance().getAllJobs(JobCategory.BASIC_JOB);
        Queue<ItemStack> itemsToDisplay = new LinkedList<>();

        for (Job basicJob : allBasicJobs) {

            List<String> jobIconLore = new ArrayList<>();

            jobIconLore.add("");
            jobIconLore.add(ChatColor.GRAY + basicJob.getDescription());
            jobIconLore.add("");
            jobIconLore.add(ChatColor.GREEN + "Payment: " + ChatColor.GRAY + basicJob.getPayment());

            ItemStack jobIcon = new ItemStackBuilder.Builder(basicJob.getItemMaterial())
                    .name(basicJob.getName())
                    .lore(jobIconLore)
                    .build().toItemStack();
            itemsToDisplay.add(jobIcon);
            jobsIds.put(jobIcon, basicJob.getId());

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
        super.handleJobGuiClick(inventoryClickEvent, jobsIds, JobCategory.BASIC_JOB);
    }

    public static BasicJobsGUI getInstance() {
        if (instance == null) instance = new BasicJobsGUI();
        return instance;
    }

}
