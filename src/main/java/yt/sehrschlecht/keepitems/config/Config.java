package yt.sehrschlecht.keepitems.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import yt.sehrschlecht.classconfig.SimpleClassConfig;
import yt.sehrschlecht.classconfig.options.ConfigOption;
import yt.sehrschlecht.classconfig.options.MigrateOption;
import yt.sehrschlecht.keepitems.KeepItems;
import yt.sehrschlecht.schlechteutils.data.Pair;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Config extends SimpleClassConfig {

    private static Config instance = null;

    @ConfigOption(key = "filter.everything.enabled", type = boolean.class, comments = {
            "If the everything filter is enabled, all items will be kept on death."
    })
    public boolean everythingFilterEnabled = false;

    @ConfigOption(key = "filter.material.enabled", type = boolean.class, comments = {
            "If the material filter is enabled, only items that are in this list will be kept on death."
    })
    public boolean materialFilterEnabled = false;
    @ConfigOption(key = "filter.material.materials", type = List.class, comments = {
            "List of Materials: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html"
    })
    public List<String> materials = Collections.singletonList("BUNDLE");

    @ConfigOption(key = "filter.custom-name.enabled", type = boolean.class, comments = {
            "If the custom name filter is enabled, only items with the custom names in this list will be kept on death."
    })
    public boolean customNameFilterEnabled = false;
    @ConfigOption(key = "filter.custom-name.check-contains", type = boolean.class, comments = {
            "If this is enabled, the plugin will check if the custom name of an item contains the specified name."
    })
    public boolean customNameCheckContains = true;
    @ConfigOption(key = "filter.custom-name.names", type = List.class)
    public List<String> customNames = Collections.singletonList("&aEmerald sword");

    @ConfigOption(key = "filter.custom-model-data.enabled", type = boolean.class, comments = {
            "If the custom model data filter is enabled, only items with the type + custom model data in this list will be kept on death."
    })
    public boolean customModelDataFilterEnabled = false;
    @ConfigOption(key = "filter.custom-model-data.items", type = List.class, comments = {
            "Syntax: ITEM_TYPE:CUSTOM_MODEL_DATA | List of Materials: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html"
    })
    public List<String> customModelDataItems = Collections.singletonList("DIAMOND_SWORD:12345");

    //ToDo somehow broken
    @ConfigOption(key = "filter.custom-crafting.enabled", type = boolean.class, comments = {
            "If this filter is enabled, all items created by the custom crafting plugin by WolfyScript that are defined in the list will be kept on death."
    })
    public boolean customCraftingFilterEnabled = false;
    @ConfigOption(key = "filter.custom-crafting.items", type = List.class, comments = {
            "Seperate folder and key with a \".\"."
    })
    public List<String> customCraftingItems = Collections.singletonList("myitems:emerald_sword");

    @ConfigOption(key = "filter.executable-items.enabled", type = boolean.class, comments = {
            "If this filter is enabled, all items created by the executable items plugin by Ssomar that are defined in the list will be kept on death."
    })
    public boolean executableItemsFilterEnabled = false;
    @ConfigOption(key = "filter.executable-items.items", type = List.class, comments = {
            "Use the id you entered when creating the item"
    })
    public List<String> executableItemsItems = Collections.singletonList("emerald_sword");

    @ConfigOption(key = "permission.enabled", type = boolean.class)
    public boolean permissionEnabled = false;
    @ConfigOption(key = "permission.value", type = String.class)
    public String permissionValue = "keepitems.use";

    @ConfigOption(key = "clear-items", type = boolean.class, comments = {
            "If this is enabled, items will be removed instead of kept on player death."
    })
    public boolean clearItems = false;

    @Deprecated
    @MigrateOption(oldKeys = "debug-config", deleteOldKeys = true)
    @ConfigOption(key = "debug-config", type = boolean.class)
    public boolean configDebug = false;

    public Config(File file) {
        super(file);
    }

    public List<Material> getMaterials() { //ToDo replace with normal material list
        return materials.stream().map(material -> {
            try {
                return Material.valueOf(material.toUpperCase());
            } catch (Exception exception) {
                KeepItems.getPlugin().getLogger().log(Level.SEVERE, "The specified material \"" + material + "\" is invalid!");
                return null;
            }
        }).collect(Collectors.toList());
    }

    public List<String> getCustomNames() {
        return customNames.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    public static Config getInstance() {
        return instance;
    }

    public List<Pair<Material, Integer>> getCustomModelDataItems() {
        return customModelDataItems.stream()
                .map(string -> {
                    String first = string.split(":")[0];
                    String second = string.split(":")[1];

                    Material type;
                    try {
                        type = Material.valueOf(first.toUpperCase());
                    } catch (Exception exception) {
                        KeepItems.getPlugin().getLogger().log(Level.SEVERE, "The specified material \"" + first + "\" is invalid!");
                        return null;
                    };

                    int data;
                    try {
                        data = Integer.parseInt(second);
                    } catch (Exception exception) {
                        KeepItems.getPlugin().getLogger().log(Level.SEVERE, "The specified custom model data \"" + second + "\" is invalid!");
                        return null;
                    };

                    return new Pair<>(type, data);
                }).collect(Collectors.toList());
    }
}
