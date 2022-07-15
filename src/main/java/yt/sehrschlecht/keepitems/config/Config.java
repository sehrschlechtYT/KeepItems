package yt.sehrschlecht.keepitems.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import yt.sehrschlecht.keepitems.KeepItems;
import yt.sehrschlecht.keepitems.utils.Debug;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Config {
    private static Config config = null;

    @ConfigOption(key = "filter.material.enabled", type = Boolean.class)
    public boolean materialFilterEnabled;
    @ConfigOption(key = "filter.material.materials", type = List.class)
    public List<String> materials;

    @ConfigOption(key = "filter.custom-name.enabled", type = Boolean.class)
    public boolean customNameFilterEnabled;
    @ConfigOption(key = "filter.custom-name.check-contains", type = Boolean.class)
    public boolean customNameCheckContains;
    @ConfigOption(key = "filter.custom-name.names", type = List.class)
    public List<String> customNames;

    //ToDo somehow broken
    @ConfigOption(key = "filter.custom-crafting.enabled", type = Boolean.class)
    public boolean customCraftingFilterEnabled;
    @ConfigOption(key = "filter.custom-crafting.items", type = List.class)
    public List<String> customCraftingItems;

    @ConfigOption(key = "filter.executable-items.enabled", type = Boolean.class)
    public boolean executableItemsFilterEnabled;
    @ConfigOption(key = "filter.executable-items.items", type = List.class)
    public List<String> executableItemsItems;

    @ConfigOption(key = "permission.enabled", type = Boolean.class)
    public boolean permissionEnabled;
    @ConfigOption(key = "permission.value", type = String.class)
    public String permissionValue;

    public Config(FileConfiguration configuration) {
        config = this;

        Debug.CONFIG.debug("Creating config...");

        for (Field field : config.getClass().getFields()) {
            if(field.isAnnotationPresent(ConfigOption.class)) {
                ConfigOption annotation = field.getAnnotation(ConfigOption.class);
                String key = annotation.key();
                Class<?> type = annotation.type();
                Debug.CONFIG.debug("Config: Found annotation for field " + field.getName() + " with key " + key + " and type " + type.getName());
                try {
                    Object object = configuration.get(key, type);
                    Debug.CONFIG.debug("Config: " + field.getName() + " -> " + object);
                    if(!type.isInstance(object)) {
                        KeepItems.getPlugin().getLogger().log(Level.SEVERE, "Config option " + key + " is not of type " + type.getName() + "!");
                        continue;
                    }
                    field.set(config, object);
                } catch (Exception e) {
                    KeepItems.getPlugin().getLogger().log(Level.SEVERE, "Could not set config value for key " + key + " to type " + type.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public static Config getInstance() {
        if(config == null) {
            reload();
        }
        return config;
    }

    public static void reload() {
        FileConfiguration configuration = KeepItems.getPlugin().getConfig();
        config = new Config(configuration);
    }

    public boolean isMaterialFilterEnabled() {
        return materialFilterEnabled;
    }

    public List<Material> getMaterials() {
        return materials.stream().map(material -> {
            try {
                return Material.valueOf(material.toUpperCase());
            } catch (Exception exception) {
                KeepItems.getPlugin().getLogger().log(Level.SEVERE, "The specified material \"" + material + "\" is invalid!");
                return null;
            }
        }).collect(Collectors.toList());
    }

    public boolean isCustomNameFilterEnabled() {
        return customNameFilterEnabled;
    }

    public boolean customNameShouldCheckContains() {
        return customNameCheckContains;
    }

    public List<String> getCustomNames() {
        return customNames.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    public boolean isCustomCraftingFilterEnabled() {
        return customCraftingFilterEnabled;
    }

    public List<String> getCustomCraftingItems() {
        return customCraftingItems;
    }

    public boolean isExecutableItemsFilterEnabled() {
        return executableItemsFilterEnabled;
    }

    public List<String> getExecutableItemsItems() {
        return executableItemsItems;
    }

    public boolean isPermissionEnabled() {
        return permissionEnabled;
    }

    public String getPermissionValue() {
        return permissionValue;
    }
}
