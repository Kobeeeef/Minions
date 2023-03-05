package org.shawty.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.shawty.Database.BlockLocation;
import org.shawty.Database.Minion;
import org.shawty.Entities.MinionItem;
import org.shawty.Minions;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = minion.getLocation().toLocation();

                ArmorStand armorStand = (ArmorStand) world.getEntity(minion.getId());
                if (armorStand == null) {
                    Minions.getMinionsClass().removeMinion(minion);
                    unregisterMinion(minion);
                } else if (!Bukkit.getOfflinePlayer(minion.getOwnerId()).isOnline()) unregisterMinion(minion);
                else if (location.getChunk().isLoaded() && location.getChunk().isEntitiesLoaded()) {
                    armorStand.setRightArmPose(new EulerAngle(-0.5, 0, 0));

                    if (minion.getType().equals(MinionItem.MinionType.SLAYER)) {
                        List<LivingEntity> nearbyEntities = armorStand.getNearbyEntities(8, 8, 8).stream().filter(m -> m instanceof LivingEntity && !m.isDead() && m != armorStand && (!(m instanceof Player)) && (!(m instanceof ArmorStand))).map(m -> (LivingEntity) m).collect(Collectors.toList());
                        if (nearbyEntities.size() > 0) {
                            LivingEntity entity = nearbyEntities.get(0);
                            Location toFace = Random.getLocation(armorStand, entity.getLocation());
                            armorStand.setRotation(toFace.getYaw(), toFace.getPitch());
                            int time = Animations.performAnimation(armorStand, Animations.Animation.RIGHT_ARM_HIT);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    entity.damage(100);
                                }
                            }.runTaskLater(Minions.getPlugin(), time / 2);
                        }

                    }
                }

            }
        }.runTaskTimer(Minions.getPlugin(), Random.getRandomNumber(10, 20) + Math.max(time, Math.max(time2, time3)), 240 - minion.getLevel() * 20L);
        Minions.minionTasks.put(minion.getId(), task.getTaskId());
    }

    public static void unregisterMinion(Minion minion) {
        if (Minions.minionTasks.containsKey(minion.getId())) {
            Bukkit.getScheduler().cancelTask(Minions.minionTasks.get(minion.getId()));
            Minions.minionTasks.remove(minion.getId());
        }
    }
}
