package org.shawty.Files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shawty.Core;

import java.io.File;
import java.io.IOException;

public class Minions {
    private static File file;
    private static FileConfiguration configFile;
    public static void setup() {
        file = new File(Core.getPlugin().getDataFolder(), "minions.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                Bukkit.getLogger().severe("Cannot create the minions.yml file!");
            }
        }
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return configFile;
    }
    public static void save() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Cannot save the minions.yml file!");
        }
    }
    public static void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}
