package yt.sehrschlecht.keepitems.filters;

import org.bukkit.inventory.ItemStack;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public abstract class ItemFilter {

    public abstract boolean isEnabled();
    public abstract boolean keepItem(ItemStack item);

}
