package org.shawty.Commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.shawty.Commands.Entities.PlayersManager;
import org.shawty.Entities.MinionItem;
import org.shawty.Utilities.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Minion implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = ((Player) sender).getPlayer();
        assert player != null;
        if (args[0].equals("give")) {
            int level = Integer.parseInt(args[2]);
            MinionItem.MinionType type = args[1].equals("slayer") ? MinionItem.MinionType.SLAYER : args[1].equals("miner") ? MinionItem.MinionType.MINER : args[1].equals("farmer") ? MinionItem.MinionType.FARMER : args[1].equals("fisher") ? MinionItem.MinionType.FISHER : args[1].equals("collector") ? MinionItem.MinionType.COLLECTOR : null;
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
                }
            };
            return true;
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("give");
            completions.add("shop");
            completions.add("reload");
        } else if (args.length == 2) {
            if (args[0].equals("give")) {
                completions.add("miner");
                completions.add("slayer");
                completions.add("fisher");
                completions.add("farmer");
                completions.add("collector");
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

