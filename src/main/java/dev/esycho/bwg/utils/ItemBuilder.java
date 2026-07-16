package dev.esycho.bwg.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}