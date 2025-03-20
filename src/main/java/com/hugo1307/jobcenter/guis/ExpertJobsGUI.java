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

public class ExpertJobsGUI extends GUIImpl {

    private static ExpertJobsGUI instance;

    private final Map<ItemStack, Integer> jobsIds = new HashMap<>();

    private ExpertJobsGUI() {
        super(ChatColor.GOLD + "" + ChatColor.BOLD + "Expert Jobs", 9*5);
    }

    @Override
    public void build() {

        List<Job> allExpertJobs = JobsConfigController.getInstance().getAllJobs(JobCategory.EXPERT_JOB);
        Queue<ItemStack> itemsToDisplay = new LinkedList<>();

        for (Job expertJob : allExpertJobs) {

            List<String> jobIconLore = new ArrayList<>();

            jobIconLore.add("");
            jobIconLore.add(ChatColor.GRAY + expertJob.getDescription());
            jobIconLore.add("");
            jobIconLore.add(ChatColor.GREEN + "Payment: " + ChatColor.GRAY + expertJob.getPayment());

            ItemStack jobIcon = new ItemStackBuilder.Builder(expertJob.getItemMaterial())
                    .name(expertJob.getName())
                    .lore(jobIconLore)
                    .build().toItemStack();
            itemsToDisplay.add(jobIcon);
            jobsIds.put(jobIcon, expertJob.getId());

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
        super.handleJobGuiClick(inventoryClickEvent, jobsIds, JobCategory.EXPERT_JOB);
    }

    public static ExpertJobsGUI getInstance() {
        if (instance == null) instance = new ExpertJobsGUI();
        return instance;
    }

}
