package com.pritam.easywarps;

import com.pritam.easywarps.api.EasyWarpsApi;
import lombok.Getter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyWarps extends JavaPlugin implements EasyWarpsApi {
    @Getter private static EasyWarps instance;

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");

        instance = this;

        getServer().getServicesManager().register(
                EasyWarpsApi.class,
                this,
                this,
                ServicePriority.Highest
        );

    }

    @Override
    public void onDisable() {
        getLogger().info("EasyWarps has been disabled!");
    }
}
