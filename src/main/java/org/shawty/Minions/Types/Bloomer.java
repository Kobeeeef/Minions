package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.BoundingBox;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Random;

import java.util.List;

public class Bloomer implements IMinion {
    Minion minion;
    ArmorStand stand;
    private final Material[] BONEMEALABLE_BLOCKS = new Material[]{Material.PUMPKIN_STEM, Material.MELON_STEM, Material.WHEAT, Material.BEETROOTS, Material.POTATOES, Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.BIRCH_SAPLING, Material.JUNGLE_SAPLING, Material.SPRUCE_SAPLING, Material.ACACIA_SAPLING};

    public Bloomer(Minion minion, ArmorStand stand) {
        this.minion = minion;
        this.stand = stand;
    }

    @Override
    public void action() {
        Location location = minion.getLocation().toLocation();
        BoundingBox box = BoundingBox.of(location.clone().subtract(8, 0, 8), location.clone().add(8, 8, 8));
        List<Block> blocks = Random.getBlocksFromBoundingBox(box, location.getWorld());
        for (Block block : blocks) {
            block.applyBoneMeal(BlockFace.DOWN);
        }
    }
}
