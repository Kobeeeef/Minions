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

import static org.shawty.Utilities.Random.getBlocksFromBoundingBox;
import static org.shawty.Utilities.Random.getHeadPose;

public class Miner implements IMinion {
    Minion minion;
    ArmorStand stand;

    public Miner(Minion minion, ArmorStand stand) {
        this.minion = minion;
        this.stand = stand;
    }

    @Override
    public void action() {
        Location location = minion.getLocation().toLocation();
        BoundingBox boundingBox = BoundingBox.of(location.clone().subtract(2, 1, 2), location.clone().subtract(0, 1, 0).add(2, 0, 2));
        List<Block> blocks = getBlocksFromBoundingBox(boundingBox, location.getWorld()).stream().filter(b -> (!b.isLiquid()) && (!b.isEmpty()) && (!(b.getState() instanceof InventoryHolder)) && b.isSolid()).collect(Collectors.toList());

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
