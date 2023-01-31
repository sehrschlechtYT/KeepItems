package yt.sehrschlecht.keepitems.utils;

import yt.sehrschlecht.keepitems.KeepItems;
import yt.sehrschlecht.keepitems.config.Config;

import java.util.function.Supplier;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public enum Debug {
    FILTERS(() -> Config.getInstance().filterDebug);

    private final Supplier<Boolean> enabled;

    Debug(Supplier<Boolean> enabled) {
        this.enabled = enabled;
    }

    public void send(String message) {
        if(enabled.get()) {
            KeepItems.getPlugin().getLogger().info("[Debug] " + message);
        }
    }
}
