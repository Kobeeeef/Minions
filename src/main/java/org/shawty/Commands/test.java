package org.shawty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.Animations;

import java.util.List;
import java.util.UUID;

public class test implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = ((Player) sender).getPlayer();
        List<org.shawty.Database.Minion> minions = Core.getMinionsClass().getMinionsByOwner(player.getUniqueId());
        if(!minions.isEmpty()) {
            for(Minion minion : minions) {
                if(minion.getLocation().toLocation().getChunk().isEntitiesLoaded() && minion.getStand() != null) {
                    Animations.performAnimation(minion.getStand(), Animations.Animation.HEAD_DOWN);
                }
                MinionManager.unregisterMinion(minion);
            }
        }
        return false;
    }
}
