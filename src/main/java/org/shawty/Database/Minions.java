package org.shawty.Database;

import com.google.gson.Gson;
import org.bukkit.configuration.file.FileConfiguration;
import org.shawty.Manager.MinionManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class Minions {
    Gson gson = new Gson();
    FileConfiguration fileConfiguration;

    public Minions(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public List<Minion> getMinions() {
        return fileConfiguration.getStringList("Minions").stream().map(m -> gson.fromJson(m, Minion.class)).collect(Collectors.toList());
    }

    public List<Minion> getMinionsByOwner(UUID ownerId) {
        return getMinions().stream().filter(m -> m.getOwnerId().equals(ownerId)).collect(Collectors.toList());
    }

    public Minion getMinion(UUID uuid) {
        Optional<Minion> optionalMinion = getMinions().stream().filter(m -> m.getId().equals(uuid)).findFirst();
        return optionalMinion.orElse(null);
    }

    public void addMinion(Minion minion) {
        List<Minion> minions = getMinions();
        minions.add(minion);
        fileConfiguration.set("Minions", minions.stream().map(m -> gson.toJson(m)).collect(Collectors.toList()));
        MinionManager.registerMinion(minion);
        org.shawty.Files.Minions.save();
        org.shawty.Files.Minions.reload();
    }

    public void editMinion(UUID uuid, Minion newMinion) {
        List<String> minions = getMinions().stream().map(m -> gson.toJson(m)).collect(Collectors.toList());
        String minion = gson.toJson(getMinion(uuid));
        minions.set(minions.indexOf(minion), gson.toJson(newMinion));
        MinionManager.registerMinion(newMinion);
        fileConfiguration.set("Minions", minions);
        org.shawty.Files.Minions.save();
        org.shawty.Files.Minions.reload();
    }

    public void removeMinion(Minion minion) {
        List<Minion> minions = getMinions();
        MinionManager.unregisterMinion(minion);
        fileConfiguration.set("Minions", minions.stream().filter(m -> !m.getId().equals(minion.getId())).map(m -> gson.toJson(m)).collect(Collectors.toList()));
        org.shawty.Files.Minions.save();
        org.shawty.Files.Minions.reload();
    }
}
