package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.shawty.Utilities.Random.getHeadPose;

public class Lumberjack implements IMinion {
    Minion minion;
    ArmorStand stand;
    private final BlockFace[] blockFaces = new BlockFace[]{BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH};
    private final Material[] wood_blocks = new Material[]{Material.OAK_LOG, Material.DARK_OAK_LOG, Material.BIRCH_LOG, Material.MANGROVE_LOG, Material.JUNGLE_LOG, Material.SPRUCE_LOG, Material.ACACIA_LOG};
    private final Material[] wood_leaves = new Material[]{Material.OAK_LEAVES, Material.DARK_OAK_LEAVES, Material.BIRCH_LEAVES, Material.MANGROVE_LEAVES, Material.JUNGLE_LEAVES, Material.SPRUCE_LEAVES, Material.ACACIA_LEAVES, Material.VINE};

    public Lumberjack(Minion minion, ArmorStand stand) {
        this.minion = minion;
        this.stand = stand;
    }

    @Override
    public void action() {
        Location location = minion.getLocation().toLocation();
        BoundingBox box = BoundingBox.of(location.clone().subtract(8, 0, 8), location.clone().add(8, 32, 8));
        List<Block> blocks = Random.getBlocksFromBoundingBox(box, location.getWorld()).stream().filter(b -> Arrays.stream(wood_blocks).anyMatch(w -> b.getType().equals(w))).collect(Collectors.toList());

        if (blocks.isEmpty()) stand.setHeadPose(new EulerAngle(0, 0, 0));
        else {
            blocks.sort((a, b) -> b.getY() - a.getY());
            Block block = blocks.get(0);
            Vector direction = block.getLocation().subtract(stand.getLocation()).toVector().normalize();
            float yaw = (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(direction.getY()));
            stand.setRotation(yaw, pitch);
            stand.setHeadPose(getHeadPose(stand.getLocation(), block.getLocation()));
            Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
            block.breakNaturally(true, true);
            for (final BlockFace blockFace : this.blockFaces) {
                final Block relative = block.getRelative(blockFace);
                if (Arrays.stream(this.wood_leaves).anyMatch(m -> m.equals(relative.getType()))) {
                    relative.setType(Material.AIR);
                }
            }
        }

    }

}
