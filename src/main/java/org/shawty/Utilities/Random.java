package org.shawty.Utilities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Random {
    public static Integer getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    public static EulerAngle getHeadPose(Location source, Location target) {
        Vector direction = target.toVector().subtract(source.toVector().add(new Vector(0, 1, 0))).normalize();
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();
        double pitch = Math.atan2(-y, Math.sqrt(x * x + z * z));
        return new EulerAngle(pitch, 0, 0);
    }
}
