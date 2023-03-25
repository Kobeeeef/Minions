package org.shawty.Minions.Types;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.List;
import java.util.stream.Collectors;

import static org.shawty.Utilities.Random.getHeadPose;

public class Slayer implements IMinion {
    Minion minion;
    ArmorStand stand;

    public Slayer(Minion minion, ArmorStand stand) {
        this.minion = minion;
        this.stand = stand;
    }

    @Override
    public void action() {
        List<LivingEntity> nearbyEntities = stand.getNearbyEntities(8, 8, 8).stream().filter(m -> m instanceof LivingEntity && !m.isDead() && m != stand && (!(m instanceof Player)) && (!(m instanceof ArmorStand))).map(m -> (LivingEntity) m).collect(Collectors.toList());
        if (nearbyEntities.size() > 0) {
            LivingEntity entity = nearbyEntities.get(Random.getRandomNumber(0, nearbyEntities.size() - 1));
            Vector direction = entity.getLocation().subtract(stand.getLocation()).toVector().normalize();
            float yaw = (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(direction.getY()));
            stand.setRotation(yaw, pitch);
            stand.setHeadPose(getHeadPose(stand.getLocation(), entity.getLocation().add(0, entity.getBoundingBox().getHeight() - 0.95, 0)));

            int time = Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
            new BukkitRunnable() {
                @Override
                public void run() {
                    entity.damage(10, stand);
                    if (!entity.isDead()) {
                        if (entity.getLocation().distance(stand.getLocation()) > 2) {


                        }
                    }
                }
            }.runTaskLater(Core.getPlugin(), time / 2);
        }
    }


}
