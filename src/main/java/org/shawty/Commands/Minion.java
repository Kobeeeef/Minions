package org.shawty.Commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.shawty.Commands.Entities.PlayersManager;
import org.shawty.Core;
import org.shawty.Entities.MinionType;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.EventWaiter;
import org.shawty.Utilities.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Minion implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = ((Player) sender).getPlayer();
        assert player != null;
        if(args.length == 0) {
            player.sendMessage(Messages.INVALID_COMMAND.getMessage());
            return false;
        }
        switch (args[0]) {
            case "give":
                if(args.length < 3) {
                    player.sendMessage(Messages.INVALID_COMMAND.getMessage());
                    return false;
                }
                int level = Integer.parseInt(args[2]);
                MinionType type = args[1].equals("slayer") ? MinionType.SLAYER : args[1].equals("miner") ? MinionType.MINER : args[1].equals("farmer") ? MinionType.FARMER : args[1].equals("fisher") ? MinionType.FISHER : args[1].equals("collector") ? MinionType.COLLECTOR : args[1].equals("lumberjack") ? MinionType.LUMBERJACK : args[1].equals("bloomer") ? MinionType.BLOOMER : null;
                if (type == null) {
                    player.sendMessage(Messages.INVALID_MINION.getMessage());
                    return false;
                }
                new PlayersManager(args.length > 3 ? args[3] : null, player) {
                    @Override
                    public void run(Player target) {
                        if (target == null) {
                            player.sendMessage(Messages.INVALID_PLAYER.getMessage());
                            return;
                        }
                        Inventory inv = target.getInventory();
                        inv.addItem(org.shawty.Items.Minion.getItem(level, type));
                        target.sendMessage(Core.getConfigClass().getPrefix() + " You have been given a level " + level + " " + type + " minion!");
                    }
                };
                return true;
            case "stop":
                player.sendMessage(Core.getConfigClass().getPrefix() + " " + Core.minionTasks.size() + " minions have been stopped!");
                Core.getMinionsClass().getMinions().forEach(MinionManager::unregisterMinion);
                break;
            case "restart":
                Core.getMinionsClass().getMinions().forEach(MinionManager::registerMinion);
                player.sendMessage(Core.getConfigClass().getPrefix() + " " + Core.minionTasks.size() + " minions have been restarted!");
                break;
            case "removeall":
                List<org.shawty.Database.Minion> minions = Core.getMinionsClass().getMinions();
                for (org.shawty.Database.Minion minion : minions) {
                   if(minion.getStand() != null) minion.getStand().remove();
                   Core.getMinionsClass().removeMinion(minion);
                }
                player.sendMessage(Core.getConfigClass().getPrefix() + " " + minions.size() + " minions have been removed from the server.");
                break;
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("stop");
            completions.add("restart");
            completions.add("give");
            completions.add("shop");
            completions.add("reload");
            completions.add("removeall");
        } else if (args.length == 2) {
            if (args[0].equals("give")) {
                completions.add("miner");
                completions.add("slayer");
                completions.add("fisher");
                completions.add("farmer");
                completions.add("collector");
                completions.add("lumberjack");
                completions.add("bloomer");
            }
        } else if (args.length == 3) {
            if (args[0].equals("give")) {
                completions.add("1");
                completions.add("2");
                completions.add("3");
                completions.add("4");
                completions.add("5");
                completions.add("6");
                completions.add("7");
                completions.add("8");
                completions.add("9");
                completions.add("10");
                completions.add("11");
            }
        } else if (args.length == 4) {
            if (args[0].equals("give")) {
                completions.add("*");
                completions.addAll(Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            }
        }
        return completions;
    }
}

