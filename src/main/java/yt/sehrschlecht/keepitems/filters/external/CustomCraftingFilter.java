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
public class CustomCraftingFilter extends ItemFilter {

    @Override
    public boolean isEnabled() {
        return Config.getInstance().isCustomCraftingFilterEnabled();
    }

    public boolean isCCItem(PersistentDataContainer dataContainer, String namespace, String key) {
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

    @Override
    public boolean keepItem(ItemStack item) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        for (String stringItem : Config.getInstance().getCustomCraftingItems()) {
            String[] split = stringItem.split(":");
            String namespace = split[0];
            String key = split[1];

            if(isCCItem(dataContainer, namespace, key)) {
                return true;
            }
        }
        return false;
    }

}
