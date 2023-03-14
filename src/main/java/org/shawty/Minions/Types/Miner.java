package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.BoundingBox;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;

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
        BoundingBox boundingBox = BoundingBox.of(location.subtract(2,1,2), location.subtract(-2, 1, -2));
    }
}
