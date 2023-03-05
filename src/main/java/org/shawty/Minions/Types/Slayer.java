package org.shawty.Minions.Types;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
            Location toFace = Random.getLocation(stand, entity.getLocation());
            stand.setRotation(toFace.getYaw(), toFace.getPitch());
            int time = Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
            new BukkitRunnable() {
                @Override
                public void run() {
                    entity.damage(100);
                }
            }.runTaskLater(Core.getPlugin(), time / 2);
        }
    }
}
