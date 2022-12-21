package yt.sehrschlecht.keepitems.filters;

import jdk.internal.net.http.common.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.keepitems.config.Config;

import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class CustomModelDataFilter extends ItemFilter {
    /**
     * This method should return a config value as users should be able to disable the filter
     *
     * @return true if the filter is enabled, false if it is disabled
     */
    @Override
    public boolean isEnabled() {
        return Config.getInstance().customModelDataFilterEnabled;
    }

    /**
     * Checks if an item stack should be kept by the filter or not.
     *
     * @param item the item stack to check
     * @return true if the item should be kept/removed in/from the inventory (depending on the plugin config) from the player drops, false if it should be dropped
     */
    @Override
    public boolean shouldKeepItem(@NotNull ItemStack item) {
        List<Pair<Material, Integer>> itemsToFilter = Config.getInstance().getCustomModelDataItems();
        for (Pair<Material, Integer> pair : itemsToFilter) {
            Material material = pair.first;
            int customModelData = pair.second;
            if(item.getType() == material && item.getItemMeta() != null && item.getItemMeta().getCustomModelData() == customModelData) {
                return true;
            }
        }
        return false;
    }
}
