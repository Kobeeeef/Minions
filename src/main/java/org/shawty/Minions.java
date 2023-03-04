package org.shawty;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.shawty.Commands.Minion;
import org.shawty.Database.Config;
import org.shawty.Events.PlayerInteractEvent;
import org.shawty.Manager.MinionManager;
import pl.socketbyte.opengui.OpenGUI;

import java.util.*;
import java.util.logging.Level;

public final class Minions extends JavaPlugin {
    public static Map<UUID, Integer> minionTasks = new HashMap<>();


    @Override
    public void onEnable() {
        // Setup config files
        saveDefaultConfig();
        Config config = getConfigClass();
        if (!config.getLicense().equals("ok")) {
            getLogger().log(Level.SEVERE, config.getPrefix() + " Your license is invalid! Please contact support @ our discord!");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            new PlayerInteractEvent();
            getCommand("minion").setExecutor(new Minion());
            OpenGUI.INSTANCE.register(this);
            org.shawty.Files.Minions.setup();
            org.shawty.Files.Minions.get().addDefault("Minions", new ArrayList<Location>());
            org.shawty.Files.Minions.get().options().copyDefaults(true);
            org.shawty.Files.Minions.save();

            startMinions();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Minions getPlugin() {
        return Minions.getPlugin(Minions.class);
    }

    public static Config getConfigClass() {
        return new Config(getPlugin().getConfig());

    }

    public static org.shawty.Database.Minions getMinionsClass() {
        return new org.shawty.Database.Minions(org.shawty.Files.Minions.get());

    }

    private void startMinions() {
        List<org.shawty.Database.Minion> minions = getMinionsClass().getMinions();
        for (org.shawty.Database.Minion minion : minions) {
            MinionManager.registerMinion(minion);
        }
    }
}
