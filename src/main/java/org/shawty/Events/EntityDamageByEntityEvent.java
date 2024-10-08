package org.shawty.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.shawty.Core;
import org.shawty.Database.Minion;

public class EntityDamageByEntityEvent implements Listener {

    public EntityDamageByEntityEvent() {
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());
    }

    @EventHandler
    public static void onEntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (entity instanceof ArmorStand && damager instanceof Player) {
            ArmorStand armorStand = (ArmorStand) entity;
            Player player = ((Player) damager).getPlayer();
            Minion minion = Core.getMinionsClass().getMinion(armorStand.getUniqueId());
            if (minion != null) {
                event.setCancelled(true);

            }
        }

    }
}
