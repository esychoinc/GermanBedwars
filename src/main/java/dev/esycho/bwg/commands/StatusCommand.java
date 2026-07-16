package dev.esycho.bwg.commands;

import dev.esycho.bwg.GermenBedwars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private final GermenBedwars plugin = GermenBedwars.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can check stats!");
            return true;
        }

        Player player = (Player) sender;
        
        // Eikhane apnar stats logic thakbe
        // Stat gulo config.yml ba database theke nite hoy
        String playerName = player.getName();
        int kills = plugin.getConfig().getInt("players." + player.getUniqueId() + ".kills", 0);
        int wins = plugin.getConfig().getInt("players." + player.getUniqueId() + ".wins", 0);
        int losses = plugin.getConfig().getInt("players." + player.getUniqueId() + ".losses", 0);
        
        // K/D Ratio calculation
        double kd = (losses == 0) ? kills : (double) kills / losses;

        player.sendMessage(ChatColor.DARK_GRAY + "§8&m---------------------------------");
        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " YOUR STATISTICS");
        player.sendMessage(ChatColor.GRAY + " Player: " + ChatColor.WHITE + playerName);
        player.sendMessage(" ");
        player.sendMessage(ChatColor.GREEN + " Kills: " + ChatColor.WHITE + kills);
        player.sendMessage(ChatColor.BLUE + " Wins: " + ChatColor.WHITE + wins);
        player.sendMessage(ChatColor.RED + " Losses: " + ChatColor.WHITE + losses);
        player.sendMessage(ChatColor.GOLD + " K/D Ratio: " + ChatColor.WHITE + String.format("%.2f", kd));
        player.sendMessage(ChatColor.DARK_GRAY + "§8&m---------------------------------");

        return true;
    }
}