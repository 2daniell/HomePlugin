package com.daniel.home.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Utils {

    public static String getSerializedLocation(Location loc) {
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getName() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    public static Location getDeserializedLocation(String s) {
        String[] parts = s.split(";");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        String name = parts[3];
        World w = Bukkit.getWorld(name);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(w, x, y, z, yaw, pitch);
    }
}
