package yt.sehrschlecht.keepitems.filters.external;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.ItemFilter;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public class ExecutableItemsFilter extends ItemFilter {
    @Override
    public boolean isEnabled() {
        return Config.getInstance().isExecutableItemsFilterEnabled();
    }

    private boolean isEIItem(PersistentDataContainer dataContainer, String id) {
        NamespacedKey namespacedKey = new NamespacedKey("executableitems", "ei-id");
        if(dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            String itemId = dataContainer.get(namespacedKey, PersistentDataType.STRING);
            return itemId.equalsIgnoreCase(id);
        }
        return false;
    }

    @Override
    public boolean keepItem(ItemStack item) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        for (String id : Config.getInstance().getExecutableItemsItems()) {
            if(isEIItem(dataContainer, id)) {
                return true;
            }
        }
        return false;
    }

}
