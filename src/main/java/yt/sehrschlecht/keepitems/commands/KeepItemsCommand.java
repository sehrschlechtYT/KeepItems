package yt.sehrschlecht.keepitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.keepitems.KeepItems;
import yt.sehrschlecht.keepitems.config.Config;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeepItemsCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            Config.reload(KeepItems.getConfiguration());
            sender.sendMessage(KeepItems.getPrefix() + "You've §asuccessfully reloaded the config§7!");
            return true;
        }
        sender.sendMessage(KeepItems.getPrefix() + "§7Usage: §c/keepitems reload");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) return Stream.of("reload").filter(string -> string.startsWith(args[0].toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
        return Collections.emptyList();
    }
}
