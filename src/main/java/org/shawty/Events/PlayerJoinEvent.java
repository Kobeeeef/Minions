package org.shawty.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.Animations;

import java.util.List;

public class PlayerJoinEvent implements Listener {

    public PlayerJoinEvent() {
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());
    }

    @EventHandler
    public static void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<Minion> minions = Core.getMinionsClass().getMinionsByOwner(player.getUniqueId());
        if (!minions.isEmpty()) {
            for (Minion minion : minions) {
                if (minion.getLocation().toLocation().getChunk().isEntitiesLoaded() && minion.getStand() != null) {
                    int time = Animations.performAnimation(minion.getStand(), Animations.Animation.HEAD_UP);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            MinionManager.registerMinion(minion);
                        }
                    }.runTaskLater(Core.getPlugin(), time);
                }
            }
        }
    }
}
