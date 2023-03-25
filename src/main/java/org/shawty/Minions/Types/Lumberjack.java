package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.List;
import java.util.stream.Collectors;

import static org.shawty.Utilities.Random.getHeadPose;

public class Lumberjack implements IMinion {
    Minion minion;
    ArmorStand stand;

    public Lumberjack(Minion minion, ArmorStand stand) {
        this.minion = minion;
        this.stand = stand;
    }

    @Override
    public void action() {
        Location location = minion.getLocation().toLocation();
        BoundingBox box = BoundingBox.of(location.clone().subtract(8, 0, 8), location.clone().add(8, 10, 8));
        List<Block> blocks = Random.getBlocksFromBoundingBox(box, location.getWorld()).stream().filter(b -> (!b.isLiquid()) && (!b.isEmpty()) && (!(b.getState() instanceof InventoryHolder)) && b.isSolid()).collect(Collectors.toList());
        if (blocks.isEmpty()) stand.setHeadPose(new EulerAngle(0, 0, 0));
        else {
            Block block = blocks.get(Random.getRandomNumber(0, blocks.size() - 1));
            Vector direction = block.getLocation().subtract(stand.getLocation()).toVector().normalize();
            float yaw = (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(direction.getY()));
            stand.setRotation(yaw, pitch);
            stand.setHeadPose(getHeadPose(stand.getLocation(), block.getLocation()));
            Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
            block.breakNaturally(true, true);
        }

    }

}
