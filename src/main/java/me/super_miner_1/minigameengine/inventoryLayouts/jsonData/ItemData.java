package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class ItemData {
    public String type;
    public int amount = 1;
    public String name = "";
    public ArrayList<String> lore = new ArrayList<String>();
    public ArrayList<EnchantmentData> enchantments = new ArrayList<EnchantmentData>();
    public boolean unbreakable = false;
    public ArrayList<String> flags = new ArrayList<String>();
    public boolean movable = true;
    public ArrayList<Interaction> callbacks = new ArrayList<Interaction>();

    public static ItemData load(String filePath) {
        String path = MinigameEngine.engine.getDataFolder().getAbsolutePath() + "/" + filePath;

        try {
            return MinigameEngine.engine.getGson().fromJson(new FileReader(path), ItemData.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static GameItemStack getItem(String filePath) {
        ItemData itemData = load(filePath);

        if (itemData == null) {
            return null;
        }

        ItemStack item = itemData.createItem();

        if (item == null) {
            return null;
        }

        return new GameItemStack(item, itemData.movable, itemData.callbacks);
    }

    public ItemStack createItem() {
        ItemStack item = new ItemStack(Objects.requireNonNull(Material.getMaterial(type)), amount);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) { // This is very unlikely but needed to get rid of a warning from IntelliJ
            return item;
        }

        if (!name.equals("")) {
            meta.setDisplayName(name);
        }

        if (lore.size() > 0) {
            meta.setLore(lore);
        }

        if (enchantments.size() > 0) {
            for (EnchantmentData enchantmentData : enchantments) {
                meta.addEnchant(enchantmentData.getEnchantment(), enchantmentData.level, true);
            }
        }

        meta.setUnbreakable(unbreakable);

        if (flags.size() > 0) {
            for (String flag : flags) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }

        item.setItemMeta(meta);

        return item;
    }
}