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
import org.shawty.Utilities.Animations;

import java.util.UUID;

public class test implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = ((Player) sender).getPlayer();
        ArmorStand stand = (ArmorStand) player.getWorld().getEntity(UUID.fromString("a041997e-9355-4530-af13-a8668eed24f0"));
//        double x = Double.parseDouble(args[0]);
//        double z = Double.parseDouble(args[1]);
//        stand.setRightArmPose(new EulerAngle(Math.toRadians(x),0, Math.toRadians(z)));

        Animations.performAnimation(stand, Animations.Animation.RIGHT_ARM_HIT);
        return false;
    }
}
