package yt.sehrschlecht.keepitems.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import jdk.internal.net.http.common.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import yt.sehrschlecht.keepitems.KeepItems;
import yt.sehrschlecht.keepitems.utils.Debug;
import yt.sehrschlecht.schlechteutils.config.AbstractConfig;
import yt.sehrschlecht.schlechteutils.config.ConfigOption;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Config extends AbstractConfig {

    private static Config instance = null;

    @ConfigOption(key = "filter.everything.enabled", type = Boolean.class)
    public boolean everythingFilterEnabled;

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

    @ConfigOption(key = "filter.custom-model-data.enabled", type = Boolean.class)
    public boolean customModelDataFilterEnabled;
    @ConfigOption(key = "filter.custom-model-data.items", type = List.class)
    public List<String> customModelDataItems;

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

    @ConfigOption(key = "clear-items", type = Boolean.class)
    public boolean clearItems;

    public Config(YamlDocument configDocument) {
        super(configDocument);
        instance = this;
        reload(configDocument);
        Debug.CONFIG.send("Loaded config");
    }

    public static void reload(YamlDocument configuration) {
        try {
            configuration.reload();
            if(getInstance() != null) getInstance().load(configuration);
        } catch (IOException e) {
            KeepItems.getPlugin().getLogger().log(Level.SEVERE, "Config: Could not reload configuration: " + e.getMessage());
        }
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
