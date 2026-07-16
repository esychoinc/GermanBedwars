package dev.esycho.bwg.commands;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    private final GermenBedwars plugin = GermenBedwars.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        // Console theke ei command kaj korbe na, shudhu player use korte parbe
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        // Permission Check (bwg.admin)
        if (!player.hasPermission("bwg.admin")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to setup arenas.");
            return true;
        }

        // /bw type korle help menu dekhabe
        if (args.length == 0) {
            sendHelpMenu(player);
            return true;
        }

        // ==========================================================
        // NOTUN COMMAND: /bw setuparena <worldname>
        // ==========================================================
        if (args[0].equalsIgnoreCase("setuparena") && args.length == 2) {
            String worldName = args[1];
            player.sendMessage(ChatColor.YELLOW + "Trying to load and teleport to world '" + worldName + "'...");

            // World ta jodi load kora na thake, tahole Spigot ke diye load korano
            World targetWorld = Bukkit.getWorld(worldName);
            if (targetWorld == null) {
                player.sendMessage(ChatColor.GRAY + "Loading world folder from server directory...");
                // Eta server folder theke world load korbe (Multiverse chara)
                targetWorld = Bukkit.createWorld(new WorldCreator(worldName)); 
            }

            // World load hoye gele teleport korbe
            if (targetWorld != null) {
                player.teleport(targetWorld.getSpawnLocation());
                player.sendMessage(ChatColor.GREEN + "Welcome to the world: " + worldName);
                
                // Automatically config e arena hishebe create kore nibe
                String arenaName = worldName.toLowerCase();
                plugin.getConfig().set("arenas." + arenaName + ".world", worldName);
                plugin.saveConfig();
                player.sendMessage(ChatColor.GREEN + "Arena '" + arenaName + "' created! Now set spawns, beds, and waiting lobby.");
            } else {
                player.sendMessage(ChatColor.RED + "Failed to load world! Make sure the world folder exists.");
            }
            return true;
        }

        // /bw lobby set
        if (args[0].equalsIgnoreCase("lobby") && args.length == 2 && args[1].equalsIgnoreCase("set")) {
            saveLocationToConfig(player.getLocation(), "server-settings.lobby-spawn");
            player.sendMessage(ChatColor.GREEN + "Main Lobby spawn point has been set!");
            return true;
        }

        // /bw arena
        if (args[0].equalsIgnoreCase("arena") && args.length >= 2) {
            
            // /bw arena create <name> (Alternative option)
            if (args[1].equalsIgnoreCase("create") && args.length == 3) {
                String arenaName = args[2].toLowerCase();
                plugin.getConfig().set("arenas." + arenaName + ".world", player.getWorld().getName());
                plugin.saveConfig();
                player.sendMessage(ChatColor.GREEN + "Arena '" + arenaName + "' created! Now set spawns and beds.");
                return true;
            }
            
            // /bw arena setspawn <arena> <team>
            if (args[1].equalsIgnoreCase("setspawn") && args.length == 4) {
                String arenaName = args[2].toLowerCase();
                String teamName = args[3].substring(0, 1).toUpperCase() + args[3].substring(1).toLowerCase(); 
                
                saveLocationToConfig(player.getLocation(), "arenas." + arenaName + ".teams." + teamName + ".spawn");
                player.sendMessage(ChatColor.GREEN + teamName + " Team spawn set for arena " + arenaName + "!");
                return true;
            }

            // /bw arena setbed <arena> <team>
            if (args[1].equalsIgnoreCase("setbed") && args.length == 4) {
                String arenaName = args[2].toLowerCase();
                String teamName = args[3].substring(0, 1).toUpperCase() + args[3].substring(1).toLowerCase();
                
                saveLocationToConfig(player.getLocation(), "arenas." + arenaName + ".teams." + teamName + ".bed");
                player.sendMessage(ChatColor.GREEN + teamName + " Team bed location set for arena " + arenaName + "!");
                return true;
            }

            // /bw arena setpos1 <arena>
            if (args[1].equalsIgnoreCase("setpos1") && args.length == 3) {
                String arenaName = args[2].toLowerCase();
                saveLocationToConfig(player.getLocation(), "arenas." + arenaName + ".waiting-lobby.pos1");
                player.sendMessage(ChatColor.GREEN + "Waiting lobby Pos1 set for arena " + arenaName + "!");
                return true;
            }

            // /bw arena setpos2 <arena>
            if (args[1].equalsIgnoreCase("setpos2") && args.length == 3) {
                String arenaName = args[2].toLowerCase();
                saveLocationToConfig(player.getLocation(), "arenas." + arenaName + ".waiting-lobby.pos2");
                player.sendMessage(ChatColor.GREEN + "Waiting lobby Pos2 set for arena " + arenaName + "!");
                return true;
            }
        }

        sendHelpMenu(player);
        return true;
    }

    private void sendHelpMenu(Player player) {
        player.sendMessage(ChatColor.DARK_GRAY + "---------------------------------");
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " GERMAN BEDWARS SETUP");
        player.sendMessage(ChatColor.GRAY + " /bw setuparena <world>" + ChatColor.WHITE + " - Load world & auto-create arena");
        player.sendMessage(ChatColor.GRAY + " /bw lobby set" + ChatColor.WHITE + " - Set main server lobby");
        player.sendMessage(ChatColor.GRAY + " /bw arena create <name>" + ChatColor.WHITE + " - Create arena manually");
        player.sendMessage(ChatColor.GRAY + " /bw arena setspawn <arena> <team>" + ChatColor.WHITE + " - Set team spawn");
        player.sendMessage(ChatColor.GRAY + " /bw arena setbed <arena> <team>" + ChatColor.WHITE + " - Set team bed");
        player.sendMessage(ChatColor.GRAY + " /bw arena setpos1 <arena>" + ChatColor.WHITE + " - Set waiting lobby pos1");
        player.sendMessage(ChatColor.GRAY + " /bw arena setpos2 <arena>" + ChatColor.WHITE + " - Set waiting lobby pos2");
        player.sendMessage(ChatColor.DARK_GRAY + "---------------------------------");
    }

    private void saveLocationToConfig(Location loc, String path) {
        plugin.getConfig().set(path + ".world", loc.getWorld().getName());
        plugin.getConfig().set(path + ".x", loc.getX());
        plugin.getConfig().set(path + ".y", loc.getY());
        plugin.getConfig().set(path + ".z", loc.getZ());
        plugin.getConfig().set(path + ".yaw", loc.getYaw());
        plugin.getConfig().set(path + ".pitch", loc.getPitch());
        plugin.saveConfig();
    }
}