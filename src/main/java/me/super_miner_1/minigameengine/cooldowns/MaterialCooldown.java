package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialCooldown extends Cooldown {
    protected GameItemStack gameItem;
    protected Material material;
    protected String originalName;
    protected Material originalMaterial;

    public MaterialCooldown(int id, Player player, GameItemStack gameItem, Material material, long length) {
        super(id, player, length);

        this.gameItem = gameItem;
        this.material = material;

        if (gameItem.getItemStack() == null) {
            return;
        }

        ItemStack item = gameItem.getItemStack();

        originalMaterial = item.getType();

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        originalName = meta.getDisplayName();
    }

    public MaterialCooldown(String id, Player player, GameItemStack gameItem, Material material, long length) {
        super(id, player, length);

        this.gameItem = gameItem;
        this.material = material;

        if (gameItem.getItemStack() == null) {
            return;
        }

        ItemStack item = gameItem.getItemStack();

        originalMaterial = item.getType();

        ItemMeta meta = item.getItemMeta();

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
            if (item.getType() != material) {
                item.setType(material);
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
            item.setType(originalMaterial);
            gameItem.setItemStack(item);
        }
    }

    @Override
    public void cancel() {
        ItemStack item = gameItem.getItemStack();
        item.setType(originalMaterial);
        gameItem.setItemStack(item);

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
