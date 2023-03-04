package org.shawty.Utilities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Random {
    public static Integer getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    public static Location getLocation(Entity entity, Location to) {
        if (entity.getWorld() != to.getWorld())
            return null;

        Location fromLocation = entity.getLocation();
        double xDiff = to.getX() - fromLocation.getX();
        double yDiff = to.getY() - fromLocation.getY();
        double zDiff = to.getZ() - fromLocation.getZ();
        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        double pitch = Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D;

        if (zDiff < 0.0D)
            yaw += Math.abs(180.0D - yaw) * 2.0D;

        Location loc = entity.getLocation();
        loc.setYaw((float) (yaw - 90.0F));
        loc.setPitch((float) (pitch - 90.0F));
        return loc;
    }
}
