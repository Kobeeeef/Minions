package org.shawty.Database;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class BlockLocation {
    int blockX;
    int blockY;
    int blockZ;
    String worldUUID;

    public BlockLocation(Location location) {
        this.blockX = location.getBlockX();
        this.blockY = location.getBlockY();
        this.blockZ = location.getBlockZ();
        this.worldUUID = location.getWorld().getUID().toString();
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public String getWorldUUID() {
        return worldUUID;
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(UUID.fromString(getWorldUUID())), getBlockX(), getBlockY(), getBlockZ());
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}
