package yt.sehrschlecht.keepitems.filters;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.keepitems.config.Config;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public class CustomNameFilter extends ItemFilter {

    @Override
    public boolean isEnabled() {
        return Config.getInstance().isCustomNameFilterEnabled();
    }

    @Override
    public boolean shouldKeepItem(@NotNull ItemStack item) {
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return false;
        Config config = Config.getInstance();
        for (String customName : config.getCustomNames()) {
            if(config.customNameShouldCheckContains()) {
                if(item.getItemMeta().getDisplayName().contains(customName)) {
                    return true;
                }
            } else if (item.getItemMeta().getDisplayName().equals(customName)) {
                return true;
            }
        }
        return false;
    }

}
