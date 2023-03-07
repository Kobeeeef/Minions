package org.shawty.Events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Database.Minions;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.Animations;

public class ChunkLoadEvent implements Listener {

    public ChunkLoadEvent() {
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());
    }

    @EventHandler
    public static void onChunkLoadEvent(org.bukkit.event.world.ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        if (chunk.isEntitiesLoaded()) {
            Entity[] entities = chunk.getEntities();
            Minions minions = Core.getMinionsClass();
            for (Entity entity : entities) {
                if (!(entity instanceof ArmorStand)) continue;
                ArmorStand stand = (ArmorStand) entity;
                Minion minion = minions.getMinion(stand.getUniqueId());
                if (minion != null) {
                    if (!MinionManager.isMinionRegistered(minion.getId())) {
                        Bukkit.getLogger().info(Core.getConfigClass().getPrefix() + " Minion " + minion.getId() + " was loaded! Adding to tasks!");
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
}
