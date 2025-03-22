package me.pritamshaw.easyWarps.warp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class WarpsManager {
    private final Set<Warp> warps = new HashSet<>();
    private final JavaPlugin plugin;

    public WarpsManager(JavaPlugin pluginInstance) {
        plugin = pluginInstance;

        FileConfiguration config = plugin.getConfig();

        config.getKeys(false).forEach(key -> warps.add(new Warp(key, config.getLocation(key))));
    }

    public boolean registerNewWarp(Warp warp) {
        if (!warpExists(warp)) {
            warps.add(warp);
            save();
            return true;
        }

        return false;
    }

    public boolean unregisterWarp(Warp warp) {
        if (warpExists(warp)) {
            warps.remove(warp);
            save();
            return true;
        } else {
            return false;
        }
    }

    public boolean unregisterWarp(String warpName) {
        if (warpExists(warpName)) {
            return unregisterWarp(getWarp(warpName));
        }

        return false;
    }

    public Warp getWarp(String warpName) {
        return warps.stream().filter(warp -> warp.name().equals(warpName)).findFirst().orElse(null);
    }

    public boolean warpExists(Warp warp) {
        return warps.contains(warp);
    }

    public boolean warpExists(String warpName) {
        return getWarp(warpName) != null;
    }

    public void warpPlayer(Player player, String warpName) {
        warps.stream().filter(warp -> warp.name().equals(warpName)).findFirst().ifPresent(warp -> {
            player.sendMessage(
                    Component
                            .text("Warping to " + warp.name())
                            .color(NamedTextColor.GRAY)
                            .decoration(TextDecoration.ITALIC, false)
            );

            player.teleport(warp.location());
        });
    }

    public Set<Warp> getAllWarps() {
        return warps;
    }

    private void save() {
        FileConfiguration config = plugin.getConfig();

        config.getKeys(false).forEach(warp -> config.set(warp, null));

        warps.forEach(warp -> config.set(warp.name(), warp.location()));

        plugin.saveConfig();
    }
}
