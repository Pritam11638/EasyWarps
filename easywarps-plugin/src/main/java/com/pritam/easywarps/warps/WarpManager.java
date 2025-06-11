package com.pritam.easywarps.warps;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WarpManager {
    private final HashMap<String, Warp> warps = new HashMap();

    public List<String> getWarpList() {
        return new ArrayList<>(warps.keySet());
    }

    public void setWarp(String name, Warp warp) {
        warps.put(name, warp);
    }

    public Warp getWarp(String name) {
        return warps.get(name);
    }

    public void removeWarp(String name) {
        warps.remove(name);
    }

    public void teleportPlayerToWarp(Player player, String warpName) {
        Warp warp = getWarp(warpName);

        if (warp == null) {
            player.sendMessage(Component.text("Warp not found: " + warpName)
                    .color(NamedTextColor.RED));
        } else if (!player.hasPermission(warp.perm())) {
            player.sendMessage(Component.text("You do not have permission to warp to: " + warpName)
                    .color(NamedTextColor.RED));
        } else {
            player.teleport(warp.location());
            player.sendMessage(Component.text("Teleported to warp: " + warpName)
                    .color(NamedTextColor.GREEN));
        }
    }

    // TODO: Implement a method to load warps from the configuration file
}
