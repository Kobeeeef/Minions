package org.shawty;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
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

    private Economy economy;

    public static Core getPlugin() {
        return Core.getPlugin(Core.class);
    }

    public Economy getEconomy() {
        return economy;
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

            if (!setupEconomy()) {
                Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency or provider found!", getDescription().getName()));
                getServer().getPluginManager().disablePlugin(this);
                return;
            } else {
                Bukkit.getLogger().info(String.format("[%1$s] - %2$s hooked!", getDescription().getName(), getEconomy().getName()));

            }

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
            Bukkit.getLogger().info(String.format("[%1$s] - Starting %2$d minions!", getDescription().getName(), getMinionsClass().getMinions().size()));
            startMinions();
            Bukkit.getLogger().info(String.format("[%1$s] - Started %2$d/%3$d minions!", getDescription().getName(), minionTasks.size(),getMinionsClass().getMinions().size()));
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

    private boolean setupEconomy() {
        if (Arrays.stream(Bukkit.getPluginManager().getPlugins()).noneMatch(p -> p.getName().equals("Vault")))
            return false;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        this.economy = rsp.getProvider();
        return true;
    }
}
