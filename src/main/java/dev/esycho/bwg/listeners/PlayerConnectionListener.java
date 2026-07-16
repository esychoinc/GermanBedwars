package dev.esycho.bwg.listeners;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    private final GermenBedwars plugin = GermenBedwars.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Welcome message
        player.sendMessage(ChatColor.GOLD + "Welcome to " + ChatColor.RED + "GermanBedwars" + ChatColor.GOLD + "!");
        
        // Teleport to lobby set in config
        teleportToLobby(player);
        
        // Inventory clear kora jeno game start er age kichu na thake
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20);
        player.setFoodLevel(20);

        // Scoreboard show korar logic (Porobortite FastBoard setup korle ekhane call hobe)
        // ScoreboardManager.setup(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // Player quit korle scoreboard clean up
        // ScoreboardManager.remove(event.getPlayer());
    }

    private void teleportToLobby(Player player) {
        String path = "server-settings.lobby-spawn";
        if (plugin.getConfig().contains(path)) {
            World world = Bukkit.getWorld(plugin.getConfig().getString(path + ".world"));
            double x = plugin.getConfig().getDouble(path + ".x");
            double y = plugin.getConfig().getDouble(path + ".y");
            double z = plugin.getConfig().getDouble(path + ".z");
            float yaw = (float) plugin.getConfig().getDouble(path + ".yaw");
            float pitch = (float) plugin.getConfig().getDouble(path + ".pitch");

            if (world != null) {
                player.teleport(new Location(world, x, y, z, yaw, pitch));
            }
        }
    }
}