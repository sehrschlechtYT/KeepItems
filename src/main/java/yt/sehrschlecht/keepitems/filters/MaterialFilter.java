package yt.sehrschlecht.keepitems.filters;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.keepitems.config.Config;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public class MaterialFilter extends ItemFilter {

    @Override
    public boolean isEnabled() {
        return Config.getInstance().materialFilterEnabled;
    }

    @Override
    public boolean shouldKeepItem(@NotNull ItemStack item) {
        return Config.getInstance().getMaterials().contains(item.getType());
    }

}
