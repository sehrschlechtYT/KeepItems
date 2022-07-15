package yt.sehrschlecht.keepitems.filters;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 *
 * An item filter is used to determine if an item should be kept on death or not.
 */
public abstract class ItemFilter {

    /**
     * This method should return a config value as users should be able to disable the filter
     * @return true if the filter is enabled, false if it is disabled
     */
    public abstract boolean isEnabled();

    /**
     * In this method, you should check if an item is allowed to be kept.
     * @param item the item to check
     * @return true if the item should be kept, false if it should be dropped
     */
    public abstract boolean keepItem(@NotNull ItemStack item);

}
