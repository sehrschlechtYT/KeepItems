package yt.sehrschlecht.keepitems.filters;

import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.ItemFilter;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public class MaterialFilter extends ItemFilter {
    @Override
    public boolean isEnabled() {
        return Config.getInstance().isMaterialFilterEnabled();
    }

    @Override
    public boolean keepItem(ItemStack item) {
        return Config.getInstance().getMaterials().contains(item.getType());
    }
}
