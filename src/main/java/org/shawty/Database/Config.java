package org.shawty.Database;

import org.bukkit.configuration.file.FileConfiguration;
import org.shawty.Minions;

public class Config {
    FileConfiguration fileConfiguration;
    public Config(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }
    public String getPrefix() {
       return fileConfiguration.getString("prefix");
    }
    public void setPrefix(String prefix) {
        fileConfiguration.set("prefix", prefix);
        Minions.getPlugin().saveConfig();
    }
    public String getLicense() {
        return fileConfiguration.getString("license");
    }
    public Integer getMinerMinionCost() {
        return fileConfiguration.getInt("minions.Miner.cost");
    }
    public Integer getSlayerMinionCost() {
        return fileConfiguration.getInt("minions.Slayer.cost");
    }
    public Integer getFisherMinionCost() {
        return fileConfiguration.getInt("minions.Fisher.cost");
    }
    public Integer getFarmerMinionCost() {
        return fileConfiguration.getInt("minions.Farmer.cost");
    }
}
