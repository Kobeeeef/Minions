package org.shawty.Events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shawty.Core;
import org.shawty.Database.BlockLocation;
import org.shawty.Database.Minion;
import org.shawty.Entities.MinionItem;
import org.shawty.Manager.MinionManager;
import org.shawty.Utilities.Inventorys;

@SuppressWarnings("deprecation")
public class PlayerInteractEvent implements Listener {

    public PlayerInteractEvent() {
        Bukkit.getPluginManager().registerEvents(this, Core.getPlugin());
    }

    @EventHandler
    public static void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack itemStack = event.getItem();
        if (action.isRightClick()) {
            MinionItem minionItem = new MinionItem(itemStack);
            if (minionItem.isMinion() && block != null) {
                new Inventorys(player.getInventory()).removeItem(itemStack, 1);
                event.setCancelled(true);
                Location location = block.getLocation().add(0.5, 1, 0.5);
                ArmorStand stand = (ArmorStand) block.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                stand.setInvulnerable(true);
                stand.setCollidable(false);
                stand.setGliding(false);
                stand.setArms(true);
                stand.setGlowing(true);
                stand.setGravity(false);
                stand.setCustomNameVisible(true);
                stand.setBasePlate(false);

                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skull = (SkullMeta) head.getItemMeta();
                skull.setDisplayName(player.getName());
                skull.setOwningPlayer(player);
                head.setItemMeta(skull);
                stand.getEquipment().setHelmet(head);
                stand.setCustomName(org.bukkit.ChatColor.RED + player.getName() + org.bukkit.ChatColor.WHITE + "'s " + org.bukkit.ChatColor.RED + minionItem.getType().getName() + ChatColor.WHITE + " (Lvl. " + minionItem.getLevel() + ")");
                stand.getEquipment().setChestplate(getEquipment(1, minionItem.getLevel()));
                stand.getEquipment().setLeggings(getEquipment(2, minionItem.getLevel()));
                stand.getEquipment().setBoots(getEquipment(3, minionItem.getLevel()));
                stand.getEquipment().setItemInMainHand(getEquipment(minionItem.getType().getEquipmentType(), minionItem.getLevel()));
                Minion minion = new Minion()
                        .setId(stand.getUniqueId())
                        .setLocation(new BlockLocation(location.getBlock().getLocation()))
                        .setOwnerId(player.getUniqueId())
                        .setType(minionItem.getType())
                        .setLevel(minionItem.getLevel());
                org.shawty.Database.Minions minions = Core.getMinionsClass();
                minions.addMinion(minion);
            }


        }
    }

    public static ItemStack glowEquipment(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getEquipment(int type, int level) {
        if (type == 0) return null;
        ItemStack armor = null;
        if ((level == 1 || level == 2) && type == 1) {
            armor = new ItemStack(Material.LEATHER_CHESTPLATE);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 1) {
            armor = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 1) {
            armor = new ItemStack(Material.IRON_CHESTPLATE);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 1) {
            armor = new ItemStack(Material.DIAMOND_CHESTPLATE);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level == 9 || level == 10) && type == 1) {
            armor = new ItemStack(Material.NETHERITE_CHESTPLATE);
            if (level == 10) {
                glowEquipment(armor);
            }
        } else if (level == 11 && type == 1) {
            ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();
            leatherArmorMeta.setColor(Color.RED);
            item.setItemMeta(leatherArmorMeta);
            armor = glowEquipment(item);
        } else if ((level == 1 || level == 2) && type == 2) {
            armor = new ItemStack(Material.LEATHER_LEGGINGS);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 2) {
            armor = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 2) {
            armor = new ItemStack(Material.IRON_LEGGINGS);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 2) {
            armor = new ItemStack(Material.DIAMOND_LEGGINGS);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level == 9 || level == 10) && type == 2) {
            armor = new ItemStack(Material.NETHERITE_LEGGINGS);
            if (level == 10) {
                glowEquipment(armor);
            }
        } else if (level == 11 && type == 2) {
            ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();
            leatherArmorMeta.setColor(Color.RED);
            item.setItemMeta(leatherArmorMeta);
            armor = glowEquipment(item);
        } else if ((level == 1 || level == 2) && type == 3) {
            armor = new ItemStack(Material.LEATHER_BOOTS);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 3) {
            armor = new ItemStack(Material.CHAINMAIL_BOOTS);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 3) {
            armor = new ItemStack(Material.IRON_BOOTS);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 3) {
            armor = new ItemStack(Material.DIAMOND_BOOTS);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level == 9 || level == 10) && type == 3) {
            armor = new ItemStack(Material.NETHERITE_BOOTS);
            if (level == 10) {
                glowEquipment(armor);
            }
        } else if (level == 11 && type == 3) {
            ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();
            leatherArmorMeta.setColor(Color.RED);
            item.setItemMeta(leatherArmorMeta);
            armor = glowEquipment(item);
        } else if ((level == 1 || level == 2) && type == 4) {
            armor = new ItemStack(Material.WOODEN_SWORD);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 4) {
            armor = new ItemStack(Material.STONE_SWORD);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 4) {
            armor = new ItemStack(Material.IRON_SWORD);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 4) {
            armor = new ItemStack(Material.DIAMOND_SWORD);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level == 9 || level == 10) && type == 4) {
            armor = new ItemStack(Material.NETHERITE_SWORD);
            if (level == 10) {
                glowEquipment(armor);
            }
        } else if (level == 11 && type == 4) {
            armor = glowEquipment(new ItemStack(Material.NETHERITE_AXE));
        } else if ((level == 1 || level == 2) && type == 5) {
            armor = new ItemStack(Material.WOODEN_PICKAXE);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 5) {
            armor = new ItemStack(Material.STONE_PICKAXE);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 5) {
            armor = new ItemStack(Material.IRON_PICKAXE);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 5) {
            armor = new ItemStack(Material.DIAMOND_PICKAXE);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level >= 9) && type == 5) {
            armor = new ItemStack(Material.NETHERITE_PICKAXE);
            if (level >= 10) {
                glowEquipment(armor);
            }
        } else if (type == 6) {
            armor = new ItemStack(Material.FISHING_ROD);
            if (level >= 2) {
                glowEquipment(armor);
            }
        } else if ((level == 1 || level == 2) && type == 7) {
            armor = new ItemStack(Material.WOODEN_HOE);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 7) {
            armor = new ItemStack(Material.STONE_HOE);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 7) {
            armor = new ItemStack(Material.IRON_HOE);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 7) {
            armor = new ItemStack(Material.DIAMOND_HOE);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level >= 9) && type == 7) {
            armor = new ItemStack(Material.NETHERITE_HOE);
            if (level >= 10) {
                glowEquipment(armor);
            }
        } else if (type == 8) {
            armor = new ItemStack(Material.EMERALD);
            if (level >= 2) {
                glowEquipment(armor);
            }
        } else if (type == 9) {
            armor = new ItemStack(Material.HOPPER);
            if (level >= 2) {
                glowEquipment(armor);
            }
        } else if ((level == 1 || level == 2) && type == 10) {
            armor = new ItemStack(Material.WOODEN_AXE);
            if (level == 2) {
                glowEquipment(armor);
            }
        } else if ((level == 3 || level == 4) && type == 10) {
            armor = new ItemStack(Material.STONE_AXE);
            if (level == 4) {
                glowEquipment(armor);
            }
        } else if ((level == 5 || level == 6) && type == 10) {
            armor = new ItemStack(Material.IRON_AXE);
            if (level == 6) {
                glowEquipment(armor);
            }
        } else if ((level == 7 || level == 8) && type == 10) {
            armor = new ItemStack(Material.DIAMOND_AXE);
            if (level == 8) {
                glowEquipment(armor);
            }
        } else if ((level >= 9) && type == 10) {
            armor = new ItemStack(Material.NETHERITE_AXE);
            if (level >= 10) {
                glowEquipment(armor);
            }
        }
        return armor;
    }
}