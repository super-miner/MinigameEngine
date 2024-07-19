package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialBarCooldown extends Cooldown {
    protected GameItemStack gameItem;
    protected String originalName;

    public MaterialBarCooldown(int id, Player player, GameItemStack gameItem, long length) {
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

        if (gameItem.getItemStack().getType() == Material.AIR) {
            return;
        }

        player.getPlayer().setCooldown(gameItem.getItemStack().getType(), (int) length);
    }

    public MaterialBarCooldown(String id, Player player, GameItemStack gameItem, long length) {
        super(id, player, length);

        this.gameItem = gameItem;

        ItemStack item = gameItem.getItemStack();

        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();

        if (item.getType() == Material.AIR) {
            return;
        }

        player.getPlayer().setCooldown(item.getType(), (int) length);
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
    }

    @Override
    public void cancel() {
        ItemStack item = gameItem.getItemStack();

        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setDisplayName(originalName);

        item.setItemMeta(meta);
        gameItem.setItemStack(item);

        super.cancel();
    }
}
