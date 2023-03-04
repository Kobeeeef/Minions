package org.shawty.Database;

import com.google.gson.Gson;
import org.shawty.Entities.MinionItem;

import java.util.UUID;

public class Minion {
    private int level;
    private String owner;
    private String type;
    private String id;
    private String location;
    public Minion() {
    }
    public BlockLocation getLocation() {
        return new Gson().fromJson(location, BlockLocation.class);
    }
    public Minion setLocation(BlockLocation blockLocation) {
        this.location = new Gson().toJson(blockLocation);
        return this;
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
}
