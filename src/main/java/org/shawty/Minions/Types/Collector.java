package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.shawty.Utilities.Random.getHeadPose;

public class Collector implements IMinion {
    Minion minion;
    ArmorStand stand;

    public Collector(Minion minion, ArmorStand stand) {
        this.minion = minion;
        this.stand = stand;
    }

    @Override
    public void action() {
        Location location = minion.getLocation().toLocation();
        collectItems(minion);
        List<Item> items = location.getNearbyEntitiesByType(Item.class, 8).stream().filter(i -> i.getLocation().distance(location) > 1).collect(Collectors.toList());
        for (Item item : items) {
            item.setCanMobPickup(false);
            double distance = location.distance(item.getLocation());
            double speed = (0.35 * distance);
            item.setVelocity(location.subtract(item.getLocation()).toVector().normalize().multiply(speed));
            Vector direction = item.getLocation().subtract(stand.getLocation()).toVector().normalize();
            float yaw = (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(direction.getY()));
            stand.setRotation(yaw, pitch);
            stand.setHeadPose(getHeadPose(stand.getLocation(), item.getLocation()));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                collectItems(minion);
            }
        }.runTaskLater(Core.getPlugin(), 10L);
    }

    private void collectItems(Minion minion) {
        Location location = minion.getLocation().toLocation();
        Collection<Item> itemsToPickup = location.getNearbyEntitiesByType(Item.class, 2);
        for (Item item : itemsToPickup) {
            location.getWorld().playSound(location, Sound.ENTITY_ITEM_PICKUP, 1, 1);
            item.remove();
        }
    }
}
