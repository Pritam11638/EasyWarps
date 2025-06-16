package com.pritam.easywarps.api.warps;

import org.bukkit.entity.Player;

public interface WarpManager {
    void setWarp(String name, Warp warp);
    Warp getWarp(String name);
    void removeWarp(String name);
    void teleportPlayerToWarp(Player player, String warpName);
}
