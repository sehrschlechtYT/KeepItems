package yt.sehrschlecht.keepitems.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import yt.sehrschlecht.keepitems.config.Config;

import java.util.Iterator;
import java.util.List;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        if(drops == null || drops.isEmpty()) return;
        Config config = Config.getInstance();
        if(config.isPermissionEnabled() && !player.hasPermission(config.getPermissionValue())) return;
        for(Iterator<ItemStack> iterator = drops.iterator(); iterator.hasNext();) {
            ItemStack item = iterator.next();
            boolean kept = false;
            if(config.isCustomCraftingItemsEnabled()) {
                PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
                for (String stringItem : Config.getInstance().getCustomCraftingItems()) {
                    String namespace = stringItem.split(":")[0];
                    String key = stringItem.split(":")[1];
                    if(isCustomCraftingItem(dataContainer, namespace, key)) {
                        event.getItemsToKeep().add(item);
                        iterator.remove();
                        kept = true;
                        break;
                    }
                }
            }
            if(config.isExecutableItemsEnabled()) {
                PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
                for (String id : Config.getInstance().getExecutableItemsItems()) {
                    if(isEIItem(dataContainer, id)) {
                        event.getItemsToKeep().add(item);
                        iterator.remove();
                        kept = true;
                        break;
                    }
                }
            }
            if(config.isMaterialListEnabled() && !kept) {
                if(config.getMaterials().contains(item.getType())) {
                    event.getItemsToKeep().add(item);
                    iterator.remove();
                }
            }
            if(config.isCustomNameListEnabled() && !kept) {
                for (String customName : config.getCustomNames()) {
                    if(config.customNameShouldCheckContains()) {
                        if(item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains(customName)) {
                            event.getItemsToKeep().add(item);
                            iterator.remove();
                            break;
                        }
                    } else if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(customName)) {
                        event.getItemsToKeep().add(item);
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

    private static boolean isCustomCraftingItem(PersistentDataContainer dataContainer, String namespace, String key) {
        NamespacedKey namespacedKey = new NamespacedKey("wolfyutilities", "custom_item");
        if(dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            String itemId = dataContainer.get(namespacedKey, PersistentDataType.STRING);
            itemId = itemId.replace("customcrafting:", "");
            String[] splitItemID = itemId.split("/");
            if(namespace.equals(splitItemID[0]) && key.equals(splitItemID[1])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEIItem(PersistentDataContainer dataContainer, String id) {
        NamespacedKey namespacedKey = new NamespacedKey("executableitems", "ei-id");
        if(dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            String itemId = dataContainer.get(namespacedKey, PersistentDataType.STRING);
            return itemId.equalsIgnoreCase(id);
        }
        return false;
    }
}
