package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (!blocks.isEmpty()) {
            Block block = blocks.get(Random.getRandomNumber(0, blocks.size() - 1));
            Vector direction = block.getLocation().subtract(stand.getLocation()).toVector().normalize();
            float yaw = (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(direction.getY()));
            stand.setRotation(yaw, pitch);
            stand.setHeadPose(getHeadPose(stand.getLocation(), block.getLocation()));
            int time = Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
            new BukkitRunnable() {
                @Override
                public void run() {
                    block.breakNaturally();
                    location.getWorld().playSound(location, Sound.BLOCK_STONE_BREAK, 1, 1);
                }
            }.runTaskLater(Core.getPlugin(), time / 2);

        } else {
            stand.setHeadPose(new EulerAngle(0, 0, 0));
        }
    }

    private List<Block> getBlocksFromBoundingBox(BoundingBox box, World world) {
        List<Block> blocks = new ArrayList<>();
        System.out.println(box.getMinX() + " " + box.getMaxX());
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
