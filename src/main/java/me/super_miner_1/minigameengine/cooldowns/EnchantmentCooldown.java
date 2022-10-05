package me.super_miner_1.minigameengine.cooldowns;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentCooldown extends Cooldown {
    protected ItemStack itemStack;
    protected String originalName;

    public EnchantmentCooldown(int id, Player player, ItemStack itemStack, long length) {
        super(id, player, length);

        this.itemStack = itemStack;

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();
    }

    public EnchantmentCooldown(String id, Player player, ItemStack itemStack, long length) {
        super(id, player, length);

        this.itemStack = itemStack;

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();
    }

    @Override
    public void run() {
        if (!active) {
            return;
        }

        if (getTimeLeft() > 0) {
            if (!itemStack.containsEnchantment(Enchantment.LUCK)) {
                itemStack.addEnchantment(Enchantment.LUCK, 1);
            }

            ItemMeta meta = itemStack.getItemMeta();

            if (meta == null) {
                return;
            }

            if (getTimeLeft() > 20) {
                meta.setDisplayName(originalName + " (" + Math.floor(getTimeLeft() / 20.0) + ")");
            }
            else {
                meta.setDisplayName(originalName);
            }
        }
        else {
            itemStack.removeEnchantment(Enchantment.LUCK);
        }
    }

    @Override
    public void cancel() {
        itemStack.removeEnchantment(Enchantment.LUCK);
    }
}
