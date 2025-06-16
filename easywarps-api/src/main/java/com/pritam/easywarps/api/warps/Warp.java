package com.pritam.easywarps.api.warps;

import org.bukkit.Location;
import org.bukkit.permissions.Permission;

public record Warp(Location location, Permission perm) {}
