package dev.esycho.bwg.managers;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArenaManager {

    private final GermenBedwars plugin = GermenBedwars.getInstance();
    
    // Arena te kon player ache sheta track korar jonno map
    private final Map<UUID, String> playerInArena = new HashMap<>();

    // Arena Start korar logic
    public void startGame(String arenaName) {
        removeWaitingLobby(arenaName);
        Bukkit.broadcastMessage("§e[GermanBedwars] §aMatch in " + arenaName + " has started!");
    }

    // Lobby vanish korar logic (Pos1 to Pos2)
    public void removeWaitingLobby(String arenaName) {
        String path = "arenas." + arenaName + ".waiting-lobby";
        
        if (!plugin.getConfig().contains(path + ".pos1") || !plugin.getConfig().contains(path + ".pos2")) {
            return;
        }

        World world = Bukkit.getWorld(plugin.getConfig().getString(path + ".pos1.world"));
        if (world == null) return;

        Location loc1 = getLocationFromConfig(path + ".pos1");
        Location loc2 = getLocationFromConfig(path + ".pos2");

        int minX = (int) Math.min(loc1.getX(), loc2.getX());
        int minY = (int) Math.min(loc1.getY(), loc2.getY());
        int minZ = (int) Math.min(loc1.getZ(), loc2.getZ());
        
        int maxX = (int) Math.max(loc1.getX(), loc2.getX());
        int maxY = (int) Math.max(loc1.getY(), loc2.getY());
        int maxZ = (int) Math.max(loc1.getZ(), loc2.getZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    // Config theke location pawar helper method
    private Location getLocationFromConfig(String path) {
        World world = Bukkit.getWorld(plugin.getConfig().getString(path + ".world"));
        double x = plugin.getConfig().getDouble(path + ".x");
        double y = plugin.getConfig().getDouble(path + ".y");
        double z = plugin.getConfig().getDouble(path + ".z");
        return new Location(world, x, y, z);
    }

    // Player arena te add kora
    public void addPlayer(Player player, String arenaName) {
        playerInArena.put(player.getUniqueId(), arenaName);
    }

    public String getPlayerArena(Player player) {
        return playerInArena.get(player.getUniqueId());
    }
}