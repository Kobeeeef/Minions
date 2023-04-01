package org.shawty;

import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.shawty.Commands.Minion;
import org.shawty.Commands.test;
import org.shawty.Database.Config;
import org.shawty.Events.*;
import org.shawty.Manager.MinionManager;
import pl.socketbyte.opengui.OpenGUI;

import java.util.*;
import java.util.logging.Level;

public final class Core extends JavaPlugin {
    public static Map<UUID, Integer> minionTasks = new HashMap<>();
    private static MythicBukkit mythicMobs;

    private static boolean checkMythicMobs() {
        if (mythicMobs != null)
            return true;
        try {
            MythicBukkit inst = MythicBukkit.inst();
            if (inst != null) {
                mythicMobs = inst;
                return true;
            }
        } catch (NoClassDefFoundError error) {
            Bukkit.getLogger().warning("[Minions] : MythicMobs could not be found.");
        }
        return false;
    }

    /**
     * Return MythicMobs instance
     *
     * @return
     */
    public static MythicBukkit getMythicMobs()
    {
        if (mythicMobs == null)
            checkMythicMobs();
        return mythicMobs;
    }

    public static Core getPlugin() {
        return Core.getPlugin(Core.class);
    }

    public static void reload() {
        org.shawty.Files.Minions.reload();
        getPlugin().reloadConfig();
    }

    public static Config getConfigClass() {
        return new Config(getPlugin().getConfig());

    }

    public static org.shawty.Database.Minions getMinionsClass() {
        return new org.shawty.Database.Minions(org.shawty.Files.Minions.get());

    }


    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Setup config files
        Config config = getConfigClass();
        if (!config.getLicense().equals("ok")) {
            getLogger().log(Level.SEVERE, config.getPrefix() + " Your license is invalid! Please contact support @ our discord!");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            org.shawty.Files.Minions.setup();
            org.shawty.Files.Minions.get().addDefault("Minions", new ArrayList<String>());
            org.shawty.Files.Minions.get().options().copyDefaults(true);
            org.shawty.Files.Minions.save();

            new PlayerInteractEvent();
            new PlayerInteractAtEntityEvent();
            new EntityDamageByEntityEvent();
            new PlayerQuitEvent();
            new ChunkLoadEvent();
            new PlayerJoinEvent();
            getCommand("minion").setExecutor(new Minion());
            getCommand("test").setExecutor(new test());
            OpenGUI.INSTANCE.register(this);
            startMinions();
        }
    }

    @Override
    public void onDisable() {
        org.shawty.Files.Minions.save();
        this.saveConfig();
        // Plugin shutdown logic
    }

    private void startMinions() {
        List<org.shawty.Database.Minion> minions = getMinionsClass().getMinions();
        for (org.shawty.Database.Minion minion : minions) {
            MinionManager.registerMinion(minion);
        }
    }
}
