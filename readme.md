# KeepItems (1.14.4 - 1.19)
### Keeps various items in the inventory upon player death

[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/sehrschlechtYT/KeepItems/graphs/commit-activity)

[![](https://dcbadge.vercel.app/api/server/crHgFwH2Gt)](https://discord.gg/crHgFwH2Gt)
![](https://dcbadge.vercel.app/api/shield/450685365876162573)

## Config
The config should be self explanatory. If you have a question, you can open a ticket on my discord server or add me on Discord (sehrschlecht#2929).


```
# Do NOT edit this!
config-version: 3

filter:
  everything:
    enabled: false #If the everything filter is enabled, all items will be kept on death.
  material:
    enabled: false #If the material filter is enabled, only items that are in this list will be kept on death.
    materials: #List of Materials: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
      - BUNDLE
  custom-name:
    enabled: false #If the custom name filter is enabled, only items with the custom names in this list will be kept on death.
    check-contains: true #If this is enabled, the plugin will check if the custom name of an item contains the specified name.
    names:
      - "&aEmerald sword"
  #Filters that support external plugins:
  custom-crafting:
    enabled: false #If this filter is enabled, all items created by the custom crafting plugin by WolfyScript that are defined in the list will be kept on death.
    items:
      - "myitems:emerald_sword" #Seperate folder and key with a ".".
  executable-items:
    enabled: false #If this filter is enabled, all items created by the executable items plugin by Ssomar that are defined in the list will be kept on death.
    items:
      - "emerald_sword" #Use the id you entered when creating the item
permission:
  enabled: false
  value: "keepitems.use"

# If this is enabled, items will be removed instead of kept on player death.
clear-items: false

# Debugging - If you want to see information about the filters in the console, set this to true.
# Do not use this on a production server as it will spam the console.
debug: false
# The following config setting is only needed if you are making changes to the config.yml file or the Config class of the KeepItems plugin.
# It will also spam the console.
# debug-config: false
```

## Filters

There are currently 4 filters. You can add your own filters by using the [KeepItems API](#api).

- Everything (All items are filtered)
- Material (checks the type of the item)
- Custom Name (checks the custom name of the item)
- Support for the [Custom Crafting](https://www.spigotmc.org/resources/customcrafting-advanced-custom-recipe-plugin-1-16-1-19-free.55883/) plugin
- Support for the [Executable Items](https://www.spigotmc.org/resources/custom-items-free-executable-items.77578/) plugin

## API

The plugin has a small api that you can use to add your own filters. The ItemFilter and FilterManager classes contain javadocs that describe how to use the api.

You have to create a class in your plugin that extends the [ItemFilter](https://github.com/sehrschlechtYT/KeepItems/tree/master/src/main/java/yt/sehrschlecht/keepitems/filters/ItemFilter.java) class.

Examples: [MaterialFilter](https://github.com/sehrschlechtYT/KeepItems/tree/master/src/main/java/yt/sehrschlecht/keepitems/filters/MaterialFilter.java), [CustomNameFilter](https://github.com/sehrschlechtYT/KeepItems/tree/master/src/main/java/yt/sehrschlecht/keepitems/filters/CustomNameFilter.java)

In your onEnable Method, you then have to register that filter using [FilterManager](https://github.com/sehrschlechtYT/KeepItems/tree/master/src/main/java/yt/sehrschlecht/keepitems/filters/FilterManager.java):
`FilterManager.getInstance().registerFilter(new YourFilterClassName())`

