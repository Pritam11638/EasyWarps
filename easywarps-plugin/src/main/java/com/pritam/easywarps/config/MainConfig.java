package com.pritam.easywarps.config;

import com.pritam.easywarps.EasyWarps;
import com.pritam.easywarps.api.config.Config;
import com.pritam.easywarps.api.warps.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.permissions.Permission;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainConfig {
    private static Config instance;

    public MainConfig() {
        instance = new Config("config.yml", EasyWarps.getInstance().getDataFolder(), EasyWarps.getInstance());

        instance.addDefaults(Map.of(
                "warps", Map.of()
        ));
    }

    public static void saveWarps(HashMap<String, Warp> warps) throws IOException {
        warps.forEach((name, warp) -> {
            instance.set("warps." + name + ".location", warp.location());
            instance.set("warps." + name + ".perm", warp.perm().getName());
        });

        instance.save();
    }

    public static HashMap<String, Warp> getWarps() {
        HashMap<String, Warp> warps = new HashMap<>();

        Set<String> warpNames = instance.getConfigurationSection("warps").getKeys(false);
        for (String name : warpNames) {
            Location location = instance.getLocation("warps." + name + ".location");
            String permS = instance.getString("warps." + name + ".perm");
            Permission perm = Bukkit.getPluginManager().getPermission(permS) == null ? new Permission(permS) : Bukkit.getPluginManager().getPermission(permS);
            warps.put(name, new Warp(location, perm));
        }

        return warps;
    }
}
