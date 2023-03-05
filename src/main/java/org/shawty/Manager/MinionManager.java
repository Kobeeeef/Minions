package org.shawty.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.shawty.Core;
import org.shawty.Database.BlockLocation;
import org.shawty.Database.Minion;
import org.shawty.Entities.MinionItem;
import org.shawty.Minions.Types.Slayer;
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
        Slayer slayer = new Slayer(minion, armorStand);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = minion.getLocation().toLocation();
                ArmorStand armorStand = (ArmorStand) world.getEntity(minion.getId());
                if (armorStand == null) {
                    Core.getMinionsClass().removeMinion(minion);
                    unregisterMinion(minion);
                } else if (!Bukkit.getOfflinePlayer(minion.getOwnerId()).isOnline()) {
                    unregisterMinion(minion);
                    if(location.getChunk().isLoaded() && location.getChunk().isEntitiesLoaded()) {
                        Animations.performAnimation(armorStand, Animations.Animation.HEAD_DOWN);
                    }
                }
                else if (location.getChunk().isLoaded() && location.getChunk().isEntitiesLoaded()) {
                    armorStand.setRightArmPose(new EulerAngle(-0.5, 0, 0));

                    if (minion.getType().equals(MinionItem.MinionType.SLAYER)) slayer.action();
                }

            }
        }.runTaskTimer(Core.getPlugin(), Random.getRandomNumber(10, 20) + Math.max(time, Math.max(time2, time3)), 240 - minion.getLevel() * 20L);
        Core.minionTasks.put(minion.getId(), task.getTaskId());
    }

    public static void unregisterMinion(Minion minion) {
        if (Core.minionTasks.containsKey(minion.getId())) {
            Bukkit.getScheduler().cancelTask(Core.minionTasks.get(minion.getId()));
            Core.minionTasks.remove(minion.getId());
        }
    }
}
