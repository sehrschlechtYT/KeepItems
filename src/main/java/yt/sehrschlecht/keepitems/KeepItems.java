package yt.sehrschlecht.keepitems;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.keepitems.commands.KeepItemsCommand;
import yt.sehrschlecht.keepitems.config.Config;
import yt.sehrschlecht.keepitems.filters.CustomNameFilter;
import yt.sehrschlecht.keepitems.filters.FilterManager;
import yt.sehrschlecht.keepitems.filters.MaterialFilter;
import yt.sehrschlecht.keepitems.filters.external.CustomCraftingFilter;
import yt.sehrschlecht.keepitems.filters.external.ExecutableItemsFilter;
import yt.sehrschlecht.keepitems.listeners.DeathListener;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class KeepItems extends JavaPlugin {
    private static KeepItems plugin;
    private static YamlDocument configuration;

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
        try {
            configuration = YamlDocument.create(
                    new File(getDataFolder(), "config.yml"),
                    Objects.requireNonNull(getResource("config.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build()

            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Config(configuration);
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
                new ExecutableItemsFilter()
        );
    }

    public static KeepItems getPlugin() {
        return plugin;
    }

    public static YamlDocument getConfiguration() {
        return configuration;
    }

    public static String getPrefix() {
        return "§7[§bKeepItems§7] ";
    }

}
