package com.pritam.easywarps.api;

import com.pritam.easywarps.api.warps.WarpManager;
import org.bukkit.Bukkit;

public interface EasyWarpsApi {
    static EasyWarpsApi getApiInstance() {
        return Bukkit.getServicesManager().load(EasyWarpsApi.class);
    }

    WarpManager getWarpManager();
}
