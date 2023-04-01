package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Inventorys;

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
        Chest chest = minion.getChest();
        if(chest == null) return;
        if(new Inventorys(chest.getInventory()).isFull()) return;
        collectItems(minion);
        List<Item> items = location.getNearbyEntitiesByType(Item.class, 9).stream().filter(i -> i.getLocation().distance(location) > 1 && i.getLocation().getBlockY() >= stand.getLocation().getBlockY()).collect(Collectors.toList());
        if (items.size() > 5) collectAllItems(minion);
        else if (!items.isEmpty()) {
            for (Item item : items) {
                double distance = location.distance(item.getLocation());
                double speed = (0.35 * distance);
                if (!item.isOnGround()) speed /= 2;
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
    }

    private void collectItems(Minion minion) {
        Location location = minion.getLocation().toLocation();
        Collection<Item> itemsToPickup = location.getNearbyEntitiesByType(Item.class, 2);
        if (!itemsToPickup.isEmpty()) {
            for (Item item : itemsToPickup) {
                item.remove();
                minion.getChest().getInventory().addItem(item.getItemStack());
            }
            location.getWorld().playSound(location, Sound.ENTITY_ITEM_PICKUP, 1, 1);
        }
    }

    private void collectAllItems(Minion minion) {
        Location location = minion.getLocation().toLocation();
        Collection<Item> itemsToPickup = location.getNearbyEntitiesByType(Item.class, 9);
        if (!itemsToPickup.isEmpty()) {
            for (Item item : itemsToPickup) {
                item.remove();
                minion.getChest().getInventory().addItem(item.getItemStack());
            }
            location.getWorld().playSound(location, Sound.ENTITY_ITEM_PICKUP, 1, 1);
        }
    }
}
