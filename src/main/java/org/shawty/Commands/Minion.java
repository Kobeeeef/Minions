package org.shawty.Commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.shawty.Entities.MinionItem;
import org.shawty.Utilities.Messages;

import java.util.ArrayList;
import java.util.List;

public class Minion implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = ((Player) sender).getPlayer();
        assert player != null;
        int level = Integer.parseInt(args[0]);
        MinionItem.MinionType type = args[1].equals("slayer") ? MinionItem.MinionType.SLAYER : args[1].equals("miner") ? MinionItem.MinionType.MINER : args[1].equals("farmer") ? MinionItem.MinionType.FARMER : args[1].equals("fisher") ? MinionItem.MinionType.FISHER : args[1].equals("collector") ? MinionItem.MinionType.COLLECTOR :null;
        if(type == null) {
            player.sendMessage(Messages.INVALID_MINION.getMessage());
            return false;
        }
        Inventory inv = player.getInventory();
        inv.addItem(org.shawty.Items.Minion.getItem(level, type));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
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
        }  else if (args.length == 2) {
            completions.add("miner");
            completions.add("slayer");
            completions.add("fisher");
            completions.add("farmer");
            completions.add("collector");
        }
        return completions;
    }
}

