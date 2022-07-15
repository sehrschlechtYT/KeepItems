package yt.sehrschlecht.keepitems.utils;

import yt.sehrschlecht.keepitems.KeepItems;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public enum Debug {
    FILTERS("debug"),
    CONFIG("debug-config");

    private final String configKey;

    Debug(String configKey) {
        this.configKey = configKey;
    }

    public void debug(String message) {
        if(KeepItems.getConfiguration().contains(configKey) && KeepItems.getConfiguration().getBoolean(configKey)) {
            KeepItems.getPlugin().getLogger().info("[Debug] " + message);
        }
    }
}
