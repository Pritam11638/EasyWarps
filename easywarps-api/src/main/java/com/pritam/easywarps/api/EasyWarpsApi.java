package com.pritam.easywarps.api;

import org.bukkit.Bukkit;

public interface EasyWarpsApi {
    static EasyWarpsApi getApiInstance() {
        return Bukkit.getServicesManager().load(EasyWarpsApi.class);
    }
}
