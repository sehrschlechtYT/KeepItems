package yt.sehrschlecht.keepitems.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.FilterManager;
import yt.sehrschlecht.keepitems.filters.ItemFilter;
import yt.sehrschlecht.keepitems.utils.Debug;

import java.util.*;

public class DeathListener implements Listener {

    private Map<UUID, List<ItemStack>> itemsToKeep = new HashMap<>();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if(itemsToKeep.containsKey(player.getUniqueId())) {
            Debug.FILTERS.debug("Player " + player.getName() + " respawned. " + itemsToKeep.get(player.getUniqueId()).size() + " items to keep.");
            for (ItemStack itemStack : itemsToKeep.get(player.getUniqueId())) {
                Debug.FILTERS.debug("Adding " + itemStack.getType() + " to " + player.getName());
                player.getInventory().addItem(itemStack);
            }
        }
        itemsToKeep.remove(player.getUniqueId());
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        Debug.FILTERS.debug(player.getName() + " died! Amount of drops: " + drops.size());
        if(drops.isEmpty()) return;
        Config config = Config.getInstance();
        if(config.isPermissionEnabled() && !player.hasPermission(config.getPermissionValue())) return;

        List<ItemStack> items = new ArrayList<>();
        Debug.FILTERS.debug("Created list of items");

        for(Iterator<ItemStack> iterator = drops.iterator(); iterator.hasNext();) {
            ItemStack item = iterator.next();
            Debug.FILTERS.debug("Checking item: " + item.getType().name());
            for (ItemFilter filter : FilterManager.getInstance().getFilters()) {
                Debug.FILTERS.debug("Checking filter: " + filter.getClass().getSimpleName() + (filter.isEnabled() ? " (enabled)" : " (disabled)"));
                if(!filter.isEnabled()) continue;
                if(filter.keepItem(item)) {
                    Debug.FILTERS.debug("Item " + item.getType().name() + " is kept by filter " + filter.getClass().getSimpleName());
                    iterator.remove();
                    items.add(item);
                    break;
                }
            }
        }

        Debug.FILTERS.debug("Amount of items to keep: " + items.size());

        itemsToKeep.remove(player.getUniqueId());
        if(!items.isEmpty()) {
            Debug.FILTERS.debug("Adding items to map");
            itemsToKeep.put(player.getUniqueId(), items);
        }
    }

}
