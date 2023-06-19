package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public class EnchantmentData {
    public String enchantment;
    public int level;

    public Enchantment getEnchantment() {
        return Enchantment.getByKey(NamespacedKey.fromString(enchantment.toLowerCase()));
    }
}
