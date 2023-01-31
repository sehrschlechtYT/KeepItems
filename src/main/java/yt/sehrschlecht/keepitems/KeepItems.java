package yt.sehrschlecht.keepitems;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.keepitems.commands.KeepItemsCommand;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.*;
import yt.sehrschlecht.keepitems.filters.external.CustomCraftingFilter;
import yt.sehrschlecht.keepitems.filters.external.ExecutableItemsFilter;
import yt.sehrschlecht.keepitems.listeners.DeathListener;

import java.io.File;

public final class KeepItems extends JavaPlugin {
    private static KeepItems plugin;
    @Override
    public void onEnable() {
        plugin = this;

        createConfig();
        registerCommands();
        registerListeners();
        registerFilters();
    }

    @Override
    public void onDisable() {

    }

    private void createConfig() {
        new Config(new File(getDataFolder(), "config.yml")).initialize();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("keepitems").setExecutor(new KeepItemsCommand());
        getCommand("keepitems").setTabCompleter(new KeepItemsCommand());
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new DeathListener(), this);
    }

    private void registerFilters() {
        FilterManager filterManager = new FilterManager();
        filterManager.registerFilters(
                new MaterialFilter(),
                new CustomNameFilter(),
                new CustomCraftingFilter(),
                new ExecutableItemsFilter(),
                new EverythingFilter(),
                new CustomModelDataFilter()
        );
    }

    public static KeepItems getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return "§7[§bKeepItems§7] ";
    }

}
