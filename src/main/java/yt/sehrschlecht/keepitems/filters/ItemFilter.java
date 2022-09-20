package yt.sehrschlecht.keepitems.filters;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 *
 * An item filter is used to determine if an item should be kept/cleared on death or not.
 */
public abstract class ItemFilter {

    /**
     * This method should return a config value as users should be able to disable the filter
     * @return true if the filter is enabled, false if it is disabled
     */
    public abstract boolean isEnabled();

    /**
     * Checks if an item stack should be kept by the filter or not.
     * @param item the item stack to check
     * @return true if the item should be kept/removed in/from the inventory (depending on the plugin config) from the player drops, false if it should be dropped
     */
    public abstract boolean shouldKeepItem(@NotNull ItemStack item);

}
