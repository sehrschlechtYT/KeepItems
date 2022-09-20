package yt.sehrschlecht.keepitems.filters.external;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.ItemFilter;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public class ExecutableItemsFilter extends ItemFilter {

    @Override
    public boolean isEnabled() {
        return Config.getInstance().executableItemsFilterEnabled;
    }

    private boolean isEIItem(PersistentDataContainer dataContainer, String id) {
        //noinspection deprecation - instantiation of NamespacedKey is needed because we can't access an instance of the ExecutableItems Plugin
        NamespacedKey namespacedKey = new NamespacedKey("executableitems", "ei-id");
        if(dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            String itemId = dataContainer.get(namespacedKey, PersistentDataType.STRING);
            return itemId != null && itemId.equalsIgnoreCase(id);
        }
        return false;
    }

    @Override
    public boolean shouldKeepItem(@NotNull ItemStack item) {
        if(!item.hasItemMeta()) return false;
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        for (String id : Config.getInstance().executableItemsItems) {
            if(isEIItem(dataContainer, id)) {
                return true;
            }
        }
        return false;
    }

}
