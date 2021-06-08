package xyz.refinedev.kitpvp.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Cuboid {

    private final Vector minV;
    private final Vector maxV;

    public Cuboid(Location min, Location max) {
        double xPos1 = Math.min(min.getX(), max.getX());
        double yPos1 = Math.min(min.getY(), max.getY());
        double zPos1 = Math.min(min.getZ(), max.getZ());

        double xPos2 = Math.max(min.getX(), max.getX());
        double yPos2 = Math.max(min.getY(), max.getY());
        double zPos2 = Math.max(min.getZ(), max.getZ());

        this.minV = new Vector(xPos1, yPos1, zPos1);
        this.maxV = new Vector(xPos2, yPos2, zPos2);
    }

    public boolean containsLocation(Location location) {
        return location.toVector().isInAABB(minV, maxV);
    }
}
