package org.shawty.Database;

import com.google.gson.Gson;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.InventoryHolder;
import org.shawty.Entities.MinionItem;

public class Minion {
    private int level;
    private int fortune;
    private String chest;

    private String owner;
    private String type;
    private String id;
    private String location;

    public Minion() {}

    public BlockLocation getLocation() {
        return new Gson().fromJson(location, BlockLocation.class);
    }

    public Minion setLocation(BlockLocation blockLocation) {
        this.location = new Gson().toJson(blockLocation);
        return this;
    }
    public Chest getChest() {
        if(chest == null) return null;
        return (Chest) new Gson().fromJson(chest, BlockLocation.class).toLocation().getBlock().getState();
    }

    public Minion setChest(Location location) {
        if(!(location.getBlock().getState() instanceof InventoryHolder)) return this;
        this.chest = new Gson().toJson(new BlockLocation(location));
        return this;

    }
    public ArmorStand getStand() {
        return (ArmorStand) getLocation().toLocation().getWorld().getEntity(
                getId());
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    public Minion setId(UUID id) {
        this.id = id.toString();
        return this;
    }

    public UUID getOwnerId() {
        return UUID.fromString(owner);
    }

    public MinionItem.MinionType getType() {
        return new Gson().fromJson(type, MinionItem.MinionType.class);
    }

    public int getLevel() {
        return level;
    }

    public Minion setLevel(int level) {
        this.level = level;
        return this;
    }

    public Minion setType(MinionItem.MinionType type) {
        this.type = new Gson().toJson(type);
        return this;
    }

    public Minion setOwnerId(UUID id) {
        this.owner = id.toString();
        return this;
    }
    public int getFortune() {
        return fortune;
    }

    public Minion setFortune(int fortune) {
        this.fortune = fortune;
        return this;
    }
}
