package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Minions.IMinion;
import org.shawty.Utilities.Animations;
import org.shawty.Utilities.Random;

import java.util.List;
import java.util.stream.Collectors;

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
            LivingEntity entity = nearbyEntities.get(0);
            Vector direction = entity.getLocation().subtract(stand.getLocation()).toVector().normalize();
            float yaw = (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(direction.getY()));
            stand.setRotation(yaw, pitch);

            stand.setHeadPose(getHeadPose(stand.getLocation(), entity.getLocation()));

            int time = Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
            new BukkitRunnable() {
                @Override
                public void run() {
                    entity.damage(10);
                }
            }.runTaskLater(Core.getPlugin(), time / 2);
        }
    }
    public static EulerAngle getHeadPose(Location source, Location target) {
        Vector direction = target.toVector().subtract(source.toVector().add(new Vector(0, 1,0))).normalize();
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();
        double pitch = Math.atan2(-y, Math.sqrt(x * x + z * z));
        return new EulerAngle(pitch, 0, 0);
    }
}
