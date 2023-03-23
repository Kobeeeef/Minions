package org.shawty.Commands.Entities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class PlayersManager implements PlayerRunnable {
    public PlayersManager(String s, Player sender) {
        if (s == null) {
            this.run(sender);
        } else if (s.trim().equals("*")) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player != null && player.isOnline()) this.run(player);
            }
        } else this.run(Bukkit.getPlayer(s));

    }

}
