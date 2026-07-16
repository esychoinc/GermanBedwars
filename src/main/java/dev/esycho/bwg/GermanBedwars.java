package dev.esycho.bwg;

import dev.esycho.bwg.commands.SetupCommand;
import dev.esycho.bwg.commands.StatsCommand;
import dev.esycho.bwg.listeners.*;
import dev.esycho.bwg.managers.LobbyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GermenBedwars extends JavaPlugin {

    private static GermenBedwars instance;
    private LobbyManager lobbyManager;

    @Override
    public void onEnable() {
        instance = this;

        // 1. Load Configs
        loadConfigurations();

        // 2. Initialize Managers
        this.lobbyManager = new LobbyManager();

        // 3. Register Listeners
        registerListeners();

        // 4. Register Commands
        registerCommands();

        Bukkit.getConsoleSender().sendMessage("§8----------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§8[§cGermanBedwars§8] §aPlugin has been enabled!");
        Bukkit.getConsoleSender().sendMessage("§8[§cGermanBedwars§8] §7Version: §e1.8.8 §8| §7Mode: §eMLG Rush");
        Bukkit.getConsoleSender().sendMessage("§8[§cGermanBedwars§8] §7Author: §bEsycho");
        Bukkit.getConsoleSender().sendMessage("§8----------------------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§8[§cGermanBedwars§8] §cPlugin disabled! Saving data...");
        instance = null;
    }

    private void loadConfigurations() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new MLGCombatListener(), this);
        Bukkit.getPluginManager().registerEvents(new BedBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        // LobbyManager register kora ache constructor e, tai extra lagbe na
    }

    private void registerCommands() {
        getCommand("bw").setExecutor(new SetupCommand());
        getCommand("stats").setExecutor(new StatsCommand());
    }

    public static GermenBedwars getInstance() {
        return instance;
    }

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }
}