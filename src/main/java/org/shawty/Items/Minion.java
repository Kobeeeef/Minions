package org.shawty.Items;

import com.google.gson.Gson;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.shawty.Core;
import org.shawty.Entities.MinionType;

import java.util.ArrayList;
import java.util.List;

public class Minion extends ItemStack {
    protected Minion() {
        super();
    }

    public static ItemStack getItem(int level, MinionType type) {
        ItemStack item = new ItemStack(Material.VILLAGER_SPAWN_EGG, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(type.getName());
        List<String> lore = new ArrayList<>();
        lore.add("§7Place this minion and it will start to work!");
        lore.add("§7Minions will not work when you are offline.");
        lore.add("§6Level: " + level);
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON);
        meta.getPersistentDataContainer().set(new NamespacedKey(Core.getPlugin(), "TYPE"), PersistentDataType.STRING, new Gson().toJson(type));
        meta.getPersistentDataContainer().set(new NamespacedKey(Core.getPlugin(), "MINION"), PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(new NamespacedKey(Core.getPlugin(), "LEVEL"), PersistentDataType.INTEGER, level);
        item.setItemMeta(meta);
        return item;
    }
}
