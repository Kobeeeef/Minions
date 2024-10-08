package org.shawty.Utilities.GUI;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shawty.Core;
import org.shawty.Database.Minion;
import org.shawty.Database.Minions;
import org.shawty.Entities.MinionType;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.EventWaiter;
import org.shawty.Utilities.Inventorys;
import org.shawty.Utilities.Messages;
import pl.socketbyte.opengui.GUI;
import pl.socketbyte.opengui.GUIExtender;
import pl.socketbyte.opengui.ItemBuilder;
import pl.socketbyte.opengui.Rows;

import java.util.ArrayList;
import java.util.List;

public class MinionGUI extends GUIExtender {
    static ItemBuilder filler = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

    public MinionGUI(Minion minion, Player player) {
        super(new GUI(ChatColor.GRAY + "Minion Settings", Rows.ONE));
        Minions minions = Core.getMinionsClass();
        OfflinePlayer owner = Bukkit.getOfflinePlayer(minion.getOwnerId());
        ItemStack upgrades = new ItemStack(Material.COAL);
        ItemMeta upgradesMeta = upgrades.getItemMeta();
        upgradesMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        upgradesMeta.setDisplayName(ChatColor.GOLD + "UPGRADES");
        upgrades.setItemMeta(upgradesMeta);
        ItemStack stats = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) stats.getItemMeta();
        meta.setOwningPlayer(owner);
        meta.setDisplayName(ChatColor.GOLD + "STATS");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Owner: " + ChatColor.DARK_PURPLE + owner.getName());
        lore.add(ChatColor.WHITE + "Level: " + ChatColor.DARK_PURPLE + minion.getLevel());
        meta.setLore(lore);
        stats.setItemMeta(meta);

        // Some additional GUI settings like entering/dragging items.
        getGuiSettings().setCanEnterItems(false);
        getGuiSettings().setCanDrag(false);
        // Adding the filler items
        setItem(0, filler);
        setItem(1, filler);
        setItem(2, filler);
        setItem(6, filler);
        setItem(7, filler);
        addItem(new ItemBuilder().setItem(upgrades), response -> new MinionLevels(minion, player).openInventory(player));
        addItem(new ItemBuilder().setItem(stats));

        ItemStack pickup = new ItemStack(Material.BARRIER);
        ItemMeta pickupItemMeta = pickup.getItemMeta();
        pickupItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickupItemMeta.setDisplayName(ChatColor.RED + "PICKUP");
        pickup.setItemMeta(pickupItemMeta);
        addItem(new ItemBuilder().setItem(pickup), response -> {
            if (new Inventorys(player.getInventory()).isFull()) {
                player.sendMessage(Messages.INVENTORY_FULL_ERROR.getMessage());
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.1f);
            } else {
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                minions.removeMinion(minion);
                MinionManager.unregisterMinion(minion);
                minion.getLocation().toLocation().getWorld().getEntity(minion.getId()).remove();
                player.getInventory().addItem(org.shawty.Items.Minion.getItem(minion.getLevel(), minion.getType()));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5f, 0.5f);
            }
        });

        if (minion.getType().equals(MinionType.COLLECTOR)) {
            ItemStack chestItem = new ItemStack(Material.CHEST);
            ItemMeta chestItemMeta = chestItem.getItemMeta();
            chestItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            chestItemMeta.setDisplayName(ChatColor.GOLD + "CHEST");
            List<String> chestLore = new ArrayList<>();
            Chest chest = minion.getChest();
            chestLore.add("");
            chestLore.add(chest == null ? ChatColor.GOLD + ChatColor.BOLD.toString() + "CLICK TO SET!" : ChatColor.GREEN + ChatColor.BOLD.toString() + String.format("x:%1$d y:%2$d z:%3$d", chest.getLocation().getBlockX(), chest.getLocation().getBlockY(), chest.getLocation().getBlockZ()));
            chestItemMeta.setLore(chestLore);
            chestItem.setItemMeta(chestItemMeta);
            addItem(new ItemBuilder().setItem(chestItem), response -> {
                player.closeInventory();
                player.sendMessage(Messages.CHEST_LINK.getMessage());
                EventWaiter<PlayerInteractEvent> playerJoinWaiter = new EventWaiter<>(PlayerInteractEvent.class, event -> {
                    Player player1 = event.getPlayer();
                    if (!player1.getUniqueId().equals(player.getUniqueId())) return;
                    Block block = event.getClickedBlock();
                    if(block == null) return;
                    if (!(block.getState() instanceof InventoryHolder)) {
                        player1.sendMessage(Messages.INVALID_CHEST.getMessage());
                        return;
                    }
                    minion.setChest(block.getLocation());
                    Core.getMinionsClass().editMinion(minion.getId(), minion);
                    player1.sendMessage(Messages.CHEST_LINKED.getMessage());
                    event.setCancelled(true);
                });
                playerJoinWaiter.waitForEvent(100L);
            });
        } else setItem(8, filler);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }
}
