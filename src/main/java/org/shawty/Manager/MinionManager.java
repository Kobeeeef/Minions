package org.shawty.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.shawty.Core;
import org.shawty.Database.BlockLocation;
import org.shawty.Database.Minion;
import org.shawty.Entities.MinionType;
import org.shawty.Minions.Types.*;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.UUID;

public class MinionManager {

    public static void registerMinion(Minion minion) {
        unregisterMinion(minion);

        BlockLocation blockLocation = minion.getLocation();
        World world = Bukkit.getWorld(UUID.fromString(blockLocation.getWorldUUID()));
        assert world != null;
        ArmorStand armorStand = (ArmorStand) world.getEntity(minion.getId());
        int time = Animations.performAnimation(armorStand, Animations.Animation.HEAD_YAWN);
        int time2 = Animations.performAnimation(armorStand, Animations.Animation.RIGHT_ARM_YAWN);
        int time3 = Animations.performAnimation(armorStand, Animations.Animation.LEFT_ARM_YAWN);
        MinionType type = minion.getType();
        Lumberjack lumberjack = type.equals(MinionType.LUMBERJACK) ? new Lumberjack(minion, armorStand) : null;
        Slayer slayer = type.equals(MinionType.SLAYER) ? new Slayer(minion, armorStand) : null;
        Miner miner = type.equals(MinionType.MINER) ? new Miner(minion, armorStand) : null;
        Bloomer bloomer = type.equals(MinionType.BLOOMER) ? new Bloomer(minion, armorStand) : null;
        Collector collector = type.equals(MinionType.COLLECTOR) ? new Collector(minion, armorStand) : null;

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = minion.getLocation().toLocation();
                ArmorStand armorStand = (ArmorStand) world.getEntity(minion.getId());
                if (!location.getChunk().isEntitiesLoaded()) {
                    Bukkit.getLogger().info(Core.getConfigClass().getPrefix() + " Minion " + minion.getId() + " is not loaded! Removing minion from task!");
                    unregisterMinion(minion);
                } else if (armorStand == null) {
                    Bukkit.getLogger().info(Core.getConfigClass().getPrefix() + " Minion " + minion.getId() + " could not be found! Removing minion from task!");
                    unregisterMinion(minion);
                } else if (!Bukkit.getOfflinePlayer(minion.getOwnerId()).isOnline()) {
                    unregisterMinion(minion);
                    if (location.getChunk().isEntitiesLoaded()) {
                        Animations.performAnimation(armorStand, Animations.Animation.HEAD_DOWN);
                    }
                } else if (location.getChunk().isEntitiesLoaded()) {

                    if (type.equals(MinionType.SLAYER)) slayer.action();
                    else if (type.equals(MinionType.MINER)) miner.action();
                    else if (type.equals(MinionType.COLLECTOR)) collector.action();
                    else if (type.equals(MinionType.LUMBERJACK)) lumberjack.action();
                    else if (type.equals(MinionType.BLOOMER)) bloomer.action();
                }

            }
        }.runTaskTimer(Core.getPlugin(), Random.getRandomNumber(10, 20) + Math.max(time, Math.max(time2, time3)), minion.getLevel() == 11 ? minion.getType().getTicksPerActionForMaxLevel() : 240 - minion.getLevel() * 20L);
        Core.minionTasks.put(minion.getId(), task.getTaskId());
    }

    public static boolean isMinionRegistered(UUID minionId) {
        return Core.minionTasks.containsKey(minionId);
    }

    public static void unregisterMinion(Minion minion) {
        if (Core.minionTasks.containsKey(minion.getId())) {
            Bukkit.getScheduler().cancelTask(Core.minionTasks.get(minion.getId()));
            Core.minionTasks.remove(minion.getId());
        }
    }
}
