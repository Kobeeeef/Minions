package org.shawty.Utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Block> getBlocksFromBoundingBox(BoundingBox box, World world) {
        List<Block> blocks = new ArrayList<>();
        for (int x = (int) box.getMinX(); x <= box.getMaxX(); x++) {
            for (int y = (int) box.getMinY(); y <= box.getMaxY(); y++) {
                for (int z = (int) box.getMinZ(); z <= box.getMaxZ(); z++) {
                    Block block = world.getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
}
