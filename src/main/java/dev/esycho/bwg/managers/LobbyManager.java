package dev.esycho.bwg.managers;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LobbyManager implements Listener {

    private final GermenBedwars plugin = GermenBedwars.getInstance();

    public LobbyManager() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Lobby te player join korle Game Selector item deya
    public void setupLobbyItems(Player player) {
        player.getInventory().clear();
        
        ItemStack selector = new ItemStack(Material.COMPASS);
        ItemMeta meta = selector.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Game Selector");
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Right click to join a game!"));
        selector.setItemMeta(meta);
        
        player.getInventory().setItem(4, selector); // Middle slot e
    }

    // --- Events (Lobby protection) ---

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isLobby(event.getPlayer())) {
            event.setCancelled(true); // Lobby te keu block bhangte parbe na
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isLobby(event.getPlayer())) {
            event.setCancelled(true); // Lobby te keu block lagate parbe na
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isLobby(player)) {
                event.setCancelled(true); // Lobby te kono damage hobe na
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (isLobby(event.getPlayer())) {
            event.setCancelled(true); // Item drop kora bondho
        }
    }

    // Helper: Player lobby te ache kina check kora
    private boolean isLobby(Player player) {
        // Simple logic: world check kora (lobby world = world)
        return player.getWorld().getName().equals("world"); 
    }
}