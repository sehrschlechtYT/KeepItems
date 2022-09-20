package yt.sehrschlecht.keepitems.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.keepitems.KeepItems;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.FilterManager;
import yt.sehrschlecht.keepitems.filters.ItemFilter;
import yt.sehrschlecht.keepitems.utils.Debug;

import java.util.*;
import java.util.logging.Level;

public class DeathListener implements Listener {

    private Map<UUID, List<ItemStack>> itemsToKeep = new HashMap<>();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Debug.FILTERS.send("Player " + player.getName() + " respawned.");
        if(itemsToKeep.containsKey(player.getUniqueId())) {
            Debug.FILTERS.send(itemsToKeep.get(player.getUniqueId()).size() + " items to keep.");
            for (ItemStack itemStack : itemsToKeep.get(player.getUniqueId())) {
                Debug.FILTERS.send("Adding " + itemStack.getType() + " to " + player.getName());
                player.getInventory().addItem(itemStack);
            }
        }
        itemsToKeep.remove(player.getUniqueId());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        Debug.FILTERS.send(player.getName() + " died! Amount of drops: " + drops.size());
        if(drops.isEmpty()) return;
        Config config = Config.getInstance();
        if(config.isPermissionEnabled() && !player.hasPermission(config.getPermissionValue())) return;

        List<ItemStack> items = new ArrayList<>();
        Debug.FILTERS.send("Created list of items");

        for(Iterator<ItemStack> iterator = drops.iterator(); iterator.hasNext();) {
            ItemStack item = iterator.next();
            Debug.FILTERS.send("Checking item: " + item.getType().name());
            for (ItemFilter filter : FilterManager.getInstance().getFilters()) {
                Debug.FILTERS.send("Checking filter: " + filter.getClass().getSimpleName() + (filter.isEnabled() ? " (enabled)" : " (disabled)"));
                if(!filter.isEnabled()) continue;
                try {
                    if(filter.shouldKeepItem(item)) {
                        Debug.FILTERS.send("Item " + item.getType().name() + " is kept by filter " + filter.getClass().getSimpleName());
                        iterator.remove();
                        items.add(item);
                        break;
                    }
                } catch (Exception e) {
                    KeepItems.getPlugin().getLogger().log(Level.SEVERE, "Filter " + filter.getClass().getSimpleName() + " threw an exception:");
                    e.printStackTrace();
                }
            }
        }

        Debug.FILTERS.send("Amount of items to keep: " + items.size());

        itemsToKeep.remove(player.getUniqueId());
        if(!items.isEmpty()) {
            if(config.shouldClearItems()) {
                Debug.FILTERS.send("Items won't be added to the map as clear-items is enabled.");
                return;
            }
            Debug.FILTERS.send("Adding items to map");
            itemsToKeep.put(player.getUniqueId(), items);
        }
    }

}
