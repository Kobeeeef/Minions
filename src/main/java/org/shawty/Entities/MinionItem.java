package org.shawty.Entities;

import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.shawty.Core;

public class MinionItem {
    PersistentDataContainer container;

    public MinionItem(ItemStack itemStack) {
        if (itemStack == null) this.container = null;
        else this.container = itemStack.getItemMeta().getPersistentDataContainer();
    }

    public Integer getLevel() {
        if(container == null) return null;
        return container.get(new NamespacedKey(Core.getPlugin(), "LEVEL"), PersistentDataType.INTEGER);
    }
    public MinionType getType() {
        if(container == null) return null;
        return new Gson().fromJson(container.get(new NamespacedKey(Core.getPlugin(), "TYPE"), PersistentDataType.STRING), MinionType.class);
    }
    public boolean isMinion() {
        if(container == null) return false;
        return container.has(new NamespacedKey(Core.getPlugin(), "MINION"));
    }

    public enum MinionType {
        MINER(ChatColor.GOLD + "Miner Minion", Core.getConfigClass().getMinerMinionCost()),
        FISHER(ChatColor.GOLD + "Fisher Minion", Core.getConfigClass().getFisherMinionCost()),
        SLAYER(ChatColor.GOLD + "Slayer Minion", Core.getConfigClass().getSlayerMinionCost()),
        FARMER(ChatColor.GOLD + "Farmer Minion", Core.getConfigClass().getFarmerMinionCost()),
        SELLER(ChatColor.GOLD + "Seller Minion", Core.getConfigClass().getFarmerMinionCost());
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
