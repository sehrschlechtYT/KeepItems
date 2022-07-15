package yt.sehrschlecht.keepitems;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.keepitems.commands.KeepItemsCommand;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.FilterManager;
import yt.sehrschlecht.keepitems.filters.external.CustomCraftingFilter;
import yt.sehrschlecht.keepitems.filters.external.CustomNameFilter;
import yt.sehrschlecht.keepitems.filters.external.ExecutableItemsFilter;
import yt.sehrschlecht.keepitems.filters.external.MaterialFilter;
import yt.sehrschlecht.keepitems.listeners.DeathListener;

public final class KeepItems extends JavaPlugin {
    private static KeepItems plugin;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        Config.reload();

        getCommand("keepitems").setExecutor(new KeepItemsCommand());
        getCommand("keepitems").setTabCompleter(new KeepItemsCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new DeathListener(), this);

        FilterManager filterManager = new FilterManager();
        filterManager.registerFilters(
                new MaterialFilter(),
                new CustomNameFilter(),
                new CustomCraftingFilter(),
                new ExecutableItemsFilter()
        );
    }

    @Override
    public void onDisable() {

    }

    public static KeepItems getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return "§7[§bKeepItems§7] ";
    }
}
