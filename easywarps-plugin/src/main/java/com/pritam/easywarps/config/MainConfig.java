package com.pritam.easywarps.config;

import com.pritam.easywarps.EasyWarps;
import com.pritam.easywarps.api.config.Config;

import java.util.Map;

public class MainConfig {
    private static Config instance;

    public MainConfig() {
        instance = new Config("config.yml", EasyWarps.getInstance().getDataFolder(), EasyWarps.getInstance());

        instance.addDefaults(Map.of(
                "warps", Map.of()
        ));
    }
}
