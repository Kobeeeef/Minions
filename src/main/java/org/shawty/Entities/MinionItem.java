package org.shawty.Entities;

import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.shawty.Minions;

public class MinionItem {
    PersistentDataContainer container;

    public MinionItem(ItemStack itemStack) {
        if (itemStack == null) this.container = null;
        else this.container = itemStack.getItemMeta().getPersistentDataContainer();
    }

    public Integer getLevel() {
        if(container == null) return null;
        return container.get(new NamespacedKey(Minions.getPlugin(), "LEVEL"), PersistentDataType.INTEGER);
    }
    public MinionType getType() {
        if(container == null) return null;
        return new Gson().fromJson(container.get(new NamespacedKey(Minions.getPlugin(), "TYPE"), PersistentDataType.STRING), MinionType.class);
    }
    public boolean isMinion() {
        if(container == null) return false;
        return container.has(new NamespacedKey(Minions.getPlugin(), "MINION"));
    }

    public enum MinionType {
        MINER(ChatColor.GOLD + "Miner Minion", Minions.getConfigClass().getMinerMinionCost()),
        FISHER(ChatColor.GOLD + "Fisher Minion", Minions.getConfigClass().getFisherMinionCost()),
        SLAYER(ChatColor.GOLD + "Slayer Minion", Minions.getConfigClass().getSlayerMinionCost()),
        FARMER(ChatColor.GOLD + "Farmer Minion", Minions.getConfigClass().getFarmerMinionCost()),
        SELLER(ChatColor.GOLD + "Seller Minion", Minions.getConfigClass().getFarmerMinionCost());
        private final String name;
        private final int cost;

        public int getCost() {
            return cost;
        }

        MinionType(String name, int cost) {

            this.name = name;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

    }
}
