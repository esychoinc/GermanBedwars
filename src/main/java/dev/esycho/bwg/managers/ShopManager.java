package dev.esycho.bwg.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopManager {

    public static void openShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 9, ChatColor.RED + "Bedwars Shop");

        // 1. Knockback Stick (Cost: 5 Clay)
        shop.setItem(0, createItem(Material.STICK, ChatColor.GOLD + "KB Stick", 5));
        
        // 2. Chainmail Armor (Cost: 10 Clay)
        shop.setItem(1, createItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.WHITE + "Chainmail Chest", 10));
        
        // 3. Iron Armor (Cost: 20 Clay)
        shop.setItem(2, createItem(Material.IRON_CHESTPLATE, ChatColor.GRAY + "Iron Chest", 20));

        player.openInventory(shop);
    }

    private static ItemStack createItem(Material mat, String name, int cost) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(ChatColor.GRAY + "Cost: " + cost + " Clay"));
        item.setItemMeta(meta);
        return item;
    }
}