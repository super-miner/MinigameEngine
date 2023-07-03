package me.super_miner_1.minigameengine.cooldowns;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialBarCooldown extends Cooldown {
    protected ItemStack itemStack;
    protected String originalName;

    public MaterialBarCooldown(int id, Player player, ItemStack itemStack, long length) {
        super(id, player, length);

        this.itemStack = itemStack;

        if (itemStack == null) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();

        if (itemStack.getType() == Material.AIR) {
            return;
        }

        player.getPlayer().setCooldown(itemStack.getType(), (int) length);
    }

    public MaterialBarCooldown(String id, Player player, ItemStack itemStack, long length) {
        super(id, player, length);

        this.itemStack = itemStack;

        if (itemStack == null) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();

        if (itemStack.getType() == Material.AIR) {
            return;
        }

        player.getPlayer().setCooldown(itemStack.getType(), (int) length);
    }

    @Override
    public void onTick() {
        if (!active) {
            return;
        }

        if (itemStack == null) {
            return;
        }

        if (getTimeLeft() > 0) {
            ItemMeta meta = itemStack.getItemMeta();

            if (meta == null) {
                return;
            }

            if (getTimeLeft() > 20) {
                meta.setDisplayName(originalName + " (" + ((int) Math.floor(getTimeLeft() / 20.0) + 1) + ")");
            }
            else {
                meta.setDisplayName(originalName);
            }

            itemStack.setItemMeta(meta);
        }
    }

    @Override
    public void cancel() {
        itemStack.removeEnchantment(Enchantment.LUCK);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setDisplayName(originalName);

        itemStack.setItemMeta(meta);
    }
}
