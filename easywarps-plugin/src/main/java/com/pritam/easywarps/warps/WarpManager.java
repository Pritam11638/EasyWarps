package com.pritam.easywarps.warps;

import com.pritam.easywarps.api.warps.Warp;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WarpManager implements com.pritam.easywarps.api.warps.WarpManager {
    @Getter private final HashMap<String, Warp> warps = new HashMap<>();

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
}
