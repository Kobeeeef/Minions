package org.shawty.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Utilities.GUI.MinionGUI;

public class PlayerInteractAtEntityEvent implements Listener {

    public PlayerInteractAtEntityEvent() {
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());
    }

    @EventHandler
    public static void onPlayerInteractAtEntity(org.bukkit.event.player.PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        org.shawty.Database.Minions minions = Core.getMinionsClass();
        Minion minion = minions.getMinion(entity.getUniqueId());
        if (minion != null) {
            event.setCancelled(true);
            new MinionGUI(minion, player).openInventory(player);
        }
    }
}
