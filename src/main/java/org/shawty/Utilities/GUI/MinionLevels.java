package org.shawty.Utilities.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shawty.Database.Minion;
import org.shawty.Utilities.Messages;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.Rows;

import java.util.ArrayList;
import java.util.List;

public class MinionLevels extends GUIExtender {
    static ItemBuilder filler = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

    // Simple custom GUI, here you can add your items and prepare it for show.
    // Functionality of this object will be extended in the future.
    public MinionLevels(Minion minion, Player player) {
        super(new GUI(ChatColor.GRAY + "Minion Upgrades", Rows.ONE));
        // Create GUIExtender class and provide the GUI information.
        // Some additional GUI settings like entering/dragging items.
        getGuiSettings().setCanEnterItems(false);
        getGuiSettings().setCanDrag(false);
        // Adding the filler items
        setItem(1, filler);
        setItem(7, filler);
        setItem(8, filler);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backItemMeta = back.getItemMeta();
        backItemMeta.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "BACK");
        List<String> backLore = new ArrayList<>();
        backLore.add(ChatColor.WHITE + "Click this to go to previous page.");
        backItemMeta.setLore(backLore);
        back.setItemMeta(backItemMeta);
        setItem(0, new ItemBuilder().setItem(back), event -> new MinionGUI(minion, player).openInventory(player));

        ItemStack levels = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta levelsMeta = levels.getItemMeta();
        levelsMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        levelsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        levelsMeta.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "LEVEL 1");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Speed: " + ChatColor.DARK_PURPLE + "10.0s");
        lore.add("");
        lore.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "ALREADY BOUGHT!");
        levelsMeta.setLore(lore);
        levels.setItemMeta(levelsMeta);
        setItem(4, new ItemBuilder().setItem(levels), response -> {
            player.sendMessage(Messages.LEVEL_ALREADY_REACHED.getMessage());
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.1f);
        });


    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }
}
