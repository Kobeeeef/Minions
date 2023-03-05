package org.shawty.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.Animations;

import java.util.List;

public class PlayerQuitEvent implements Listener {

    public PlayerQuitEvent() {
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());
    }

    @EventHandler
    public static void onPlayerQuitEvent(org.bukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();
        List<Minion> minions = Core.getMinionsClass().getMinionsByOwner(player.getUniqueId());
        if(!minions.isEmpty()) {
            for(Minion minion : minions) {
                if(minion.getLocation().toLocation().getChunk().isEntitiesLoaded() && minion.getStand() != null) {
                    Animations.performAnimation(minion.getStand(), Animations.Animation.HEAD_DOWN);
                }
                MinionManager.unregisterMinion(minion);
            }
        }
    }
}
