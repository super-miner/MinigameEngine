package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentCooldown extends Cooldown {
    protected GameItemStack gameItem;
    protected String originalName;

    public EnchantmentCooldown(int id, Player player, GameItemStack gameItem, long length) {
        super(id, player, length);

        this.gameItem = gameItem;

        if (gameItem.getItemStack() == null) {
            return;
        }

        ItemMeta meta = gameItem.getItemStack().getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();
    }

    public EnchantmentCooldown(String id, Player player, GameItemStack gameItem, long length) {
        super(id, player, length);

        this.gameItem = gameItem;

        if (gameItem.getItemStack() == null) {
            return;
        }

        ItemMeta meta = gameItem.getItemStack().getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();
    }

    @Override
    public void onTick() {
        if (!active) {
            return;
        }

        ItemStack item = gameItem.getItemStack();

        if (item == null) {
            return;
        }

        if (getTimeLeft() > 0) {
            if (!item.containsEnchantment(Enchantment.LUCK)) {
                item.addEnchantment(Enchantment.LUCK, 1);
                gameItem.setItemStack(item);
            }

            ItemMeta meta = item.getItemMeta();

            if (meta == null) {
                return;
            }

            if (getTimeLeft() > 1) {
                meta.setDisplayName(originalName + " (" + ((int) Math.floor(getTimeLeft() / 20.0) + 1) + ")");
            }
            else {
                meta.setDisplayName(originalName);
            }

            item.setItemMeta(meta);
            gameItem.setItemStack(item);
        }
        else {
            item.removeEnchantment(Enchantment.LUCK);
            gameItem.setItemStack(item);
        }
    }

    @Override
    public void cancel() {
        ItemStack item = gameItem.getItemStack();
        item.removeEnchantment(Enchantment.LUCK);
        gameItem.setItemStack(item);

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setDisplayName(originalName);

        item.setItemMeta(meta);
        gameItem.setItemStack(item);
    }
}
