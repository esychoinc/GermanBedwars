package dev.esycho.bwg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onShopClick(InventoryClickEvent event) {
        // Shop er inventory title check kora (Jodi GUI er nam "Bedwars Shop" hoy)
        if (!event.getInventory().getName().equals(ChatColor.RED + "Bedwars Shop")) {
            return;
        }

        // Event cancel kora jeno player item gulo inventory theke ber korte na pare
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Item kinle ki hobe (Logic)
        if (clickedItem.getType() == Material.STICK) {
            // Eikhane apnar economy check korar logic hobe
            player.getInventory().addItem(new ItemStack(Material.STICK));
            player.sendMessage(ChatColor.GREEN + "You bought a Knockback Stick!");
            player.closeInventory();
        } 
        else if (clickedItem.getType() == Material.WOOL) {
            player.getInventory().addItem(new ItemStack(Material.WOOL, 16));
            player.sendMessage(ChatColor.GREEN + "You bought 16 wool!");
            player.closeInventory();
        }
    }
}