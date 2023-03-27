package org.shawty.Entities;

import org.bukkit.ChatColor;
import org.shawty.Core;

public enum MinionType {
    LUMBERJACK(ChatColor.GOLD + "Lumberjack Minion",
            Core.getConfigClass().getMinerMinionCost(), 10, 7),
    BLOOMER(ChatColor.GOLD + "Bloomer Minion",
            Core.getConfigClass().getMinerMinionCost(), 10, 15),
    MINER(ChatColor.GOLD + "Miner Minion",
            Core.getConfigClass().getMinerMinionCost(), 5, 15),
    FISHER(ChatColor.GOLD + "Fisher Minion",
            Core.getConfigClass().getFisherMinionCost(), 6, 15),
    SLAYER(ChatColor.GOLD + "Slayer Minion",
            Core.getConfigClass().getSlayerMinionCost(), 4, 15),
    FARMER(ChatColor.GOLD + "Farmer Minion",
            Core.getConfigClass().getFarmerMinionCost(), 7, 15),
    SELLER(ChatColor.GOLD + "Seller Minion",
            Core.getConfigClass().getFarmerMinionCost(), 8, 15),
    COLLECTOR(ChatColor.GOLD + "Collector Minion",
            Core.getConfigClass().getFisherMinionCost(), 9, 15);
    private final String name;
    private final int cost;
    private final int equipmentType;
    private final int ticksPerActionForMaxLevel;

    MinionType(
            String name, int cost, int equipmentType, int ticksPerActionForMaxLevel)
    {
        this.equipmentType = equipmentType;
        this.name = name;
        this.cost = cost;
        this.ticksPerActionForMaxLevel = ticksPerActionForMaxLevel;
    }

    public int getCost() {
        return cost;
    }

    public int getEquipmentType() {
        return equipmentType;
    }

    public int getTicksPerActionForMaxLevel() {
        return ticksPerActionForMaxLevel;
    }

    public String getName() {
        return name;
    }
}