package yt.sehrschlecht.keepitems.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import yt.sehrschlecht.keepitems.KeepItems;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Config {
    private static Config config = null;

    private final boolean materialListEnabled;
    private final List<String> materials;

    private final boolean customCraftingItemsEnabled;
    private final List<String> customCraftingItems;
    private final boolean permissionEnabled;
    private final String permissionValue;

    public Config(boolean materialListEnabled, List<String> materials, boolean nbtEnabled, List<String> customCraftingItems, boolean permissionEnabled, String permissionValue) {
        this.materialListEnabled = materialListEnabled;
        this.materials = materials;
        this.customCraftingItemsEnabled = nbtEnabled;
        this.customCraftingItems = customCraftingItems;
        this.permissionEnabled = permissionEnabled;
        this.permissionValue = permissionValue;
        config = this;
    }

    public static Config getInstance() {
        if(config == null) {
            config = reload();
        }
        return config;
    }

    public static Config reload() {
        FileConfiguration configuration = KeepItems.getPlugin().getConfig();
        return new Config(
                configuration.getBoolean("material-list.enabled"),
                configuration.getStringList("material-list.materials"),

                configuration.getBoolean("customcrafting-item.enabled"),
                configuration.getStringList("customcrafting-item.items"),

                configuration.getBoolean("permission.enabled"),
                configuration.getString("permission.value"));
    }

    public boolean isMaterialListEnabled() {
        return materialListEnabled;
    }

    public List<Material> getMaterials() {
        return materials.stream().map(material -> {
            try {
                return Material.valueOf(material.toUpperCase(Locale.ENGLISH));
            } catch (Exception exception) {
                KeepItems.getPlugin().getLogger().log(Level.SEVERE, "The specified material \"" + material + "\" is invalid!");
                return null;
            }
        }).collect(Collectors.toList());
    }

    public boolean isCustomCraftingItemsEnabled() {
        return customCraftingItemsEnabled;
    }

    public List<String> getCustomCraftingItems() {
        return customCraftingItems;
    }

    public boolean isPermissionEnabled() {
        return permissionEnabled;
    }

    public String getPermissionValue() {
        return permissionValue;
    }
}
