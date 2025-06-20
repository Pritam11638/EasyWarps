package com.pritam.easywarps;

import com.pritam.easywarps.api.EasyWarpsApi;
import com.pritam.easywarps.commands.WarpAdmin;
import com.pritam.easywarps.config.MainConfig;
import com.pritam.easywarps.warps.WarpManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class EasyWarps extends JavaPlugin implements EasyWarpsApi {
    @Getter
    private static EasyWarps instance;
    @Getter
    private WarpManager warpManager;
    @Getter
    private MainConfig mainConfig;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getServicesManager().register(
                EasyWarpsApi.class,
                this,
                this,
                ServicePriority.Highest
        );

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> {
                    event.registrar().register(WarpAdmin.getCommand());
                    event.registrar().register(WarpAdmin.getAlias());
                }
        );

        try {
            mainConfig = new MainConfig(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        warpManager = new WarpManager();

        mainConfig.getWarps().forEach((name, warp) -> warpManager.setWarp(name, warp));
    }

    @Override
    public void onDisable() {
        try {
            mainConfig.saveWarps(warpManager.getWarps());
        } catch (IOException e) {
            getLogger().severe("Failed to save warps: " + e.getMessage());
        }

        getLogger().info("EasyWarps has been disabled!");
    }
}