package yt.sehrschlecht.keepitems;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.keepitems.commands.KeepItemsCommand;
import yt.sehrschlecht.keepitems.listeners.DeathListener;

public final class KeepItems extends JavaPlugin {
    private static KeepItems plugin;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        getCommand("keepitems").setExecutor(new KeepItemsCommand());
        getCommand("keepitems").setTabCompleter(new KeepItemsCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new DeathListener(), this);
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
