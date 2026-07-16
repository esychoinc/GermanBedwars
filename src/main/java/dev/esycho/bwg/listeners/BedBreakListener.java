package dev.esycho.bwg.listeners;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BedBreakListener implements Listener {

    private final GermenBedwars plugin = GermenBedwars.getInstance();

    @EventHandler
    public void onBedBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check korchi block ta bed kina
        if (block.getType() == Material.BED_BLOCK) {
            
            // Arena check (Eikhane apnar arena system er logic jukto korte hobe)
            // Current code e ami basic check diyechi
            String arenaName = "map1"; // Eta dynamically arena name get korar logic hobe
            
            // Bed break hole sound
            player.getWorld().playSound(block.getLocation(), Sound.WITHER_DEATH, 1.0f, 1.0f);
            
            // Team determine kora (Example: Bed er color onujayi team)
            String team = "Red"; // Logic: check block location against config
            
            // Global Broadcast
            Bukkit.broadcastMessage(ChatColor.RED + "---------------------------------");
            Bukkit.broadcastMessage(ChatColor.BOLD + " BED DESTROYED!");
            Bukkit.broadcastMessage(ChatColor.GRAY + " The " + ChatColor.RED + team + ChatColor.GRAY + " bed was broken by " + player.getName() + "!");
            Bukkit.broadcastMessage(ChatColor.RED + "---------------------------------");

            // Title message
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "BED BROKEN!", ChatColor.GRAY + team + " team respawn disabled!");
            }

            // Logic to disable team respawn
            plugin.getConfig().set("arenas." + arenaName + ".teams." + team + ".bed-status", "destroyed");
            plugin.saveConfig();
        }
    }
}