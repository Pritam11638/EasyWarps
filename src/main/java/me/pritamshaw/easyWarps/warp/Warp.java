package me.pritamshaw.easyWarps.warp;

import org.bukkit.Location;

public class Warp {
    private final String name;
    private final Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }
}