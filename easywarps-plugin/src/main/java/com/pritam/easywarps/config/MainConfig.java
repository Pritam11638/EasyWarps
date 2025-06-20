package com.pritam.easywarps.config;

import com.pritam.easywarps.api.config.Config;
import com.pritam.easywarps.api.warps.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainConfig extends Config {
    public MainConfig(JavaPlugin plugin) throws IOException {
        super("config", plugin.getDataFolder(), plugin);

        addDefaults(Map.of(
                "warps", Map.of()
        ));

        save();
    }

    public void saveWarps(HashMap<String, Warp> warps) throws IOException {
        warps.forEach((name, warp) -> {
            set("warps." + name + ".location", warp.location());
            set("warps." + name + ".perm", warp.perm().getName());
        });

        save();
    }

    public HashMap<String, Warp> getWarps() {
        HashMap<String, Warp> warps = new HashMap<>();

        ConfigurationSection warpsSection = getConfigurationSection("warps");
        if (warpsSection == null) return warps;

        Set<String> warpNames = warpsSection.getKeys(false);
        for (String name : warpNames) {
            Location location = getLocation("warps." + name + ".location");
            String permS = getString("warps." + name + ".perm");
            Permission perm = Bukkit.getPluginManager().getPermission(permS) == null ?
                    new Permission(permS) : Bukkit.getPluginManager().getPermission(permS);

            warps.put(name, new Warp(location, perm));
        }

        return warps;
    }
}