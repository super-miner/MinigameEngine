package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import de.tr7zw.nbtapi.NBTItem;
import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.inventoryLayouts.ItemStackUtility;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ItemData {
    public String type;
    public int amount = 1;
    public String name = "";
    public ArrayList<String> lore = new ArrayList<String>();
    public ArrayList<EnchantmentData> enchantments = new ArrayList<EnchantmentData>();
    public ArrayList<AttributeData> attributes = new ArrayList<AttributeData>();
    public boolean unbreakable = false;
    public int color = 10511680;
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

    public static ItemStack getItem(String filePath) {
        ItemData itemData = load(filePath);

        if (itemData == null) {
            return null;
        }

        ItemStack item = itemData.createItem();

        if (item == null) {
            return null;
        }

        //return new GameItemStack(item, itemData.movable, itemData.callbacks);
        return item;
    }

    public ItemStack createItem() {
        Material material = Material.getMaterial(type);

        if (material == null) {
            MinigameEngine.consoleWarn("Material " + type + " not found, skipping this item.", MinigameEngine.WarnPriority.HIGH);
            return null;
        }

        ItemStack item = new ItemStack(material, amount);
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

        if (attributes.size() > 0) {
            for (AttributeData attributeData : attributes) {
                meta.addAttributeModifier(attributeData.getAttribute(), new AttributeModifier(attributeData.attribute, attributeData.baseLevel, AttributeModifier.Operation.ADD_NUMBER));
            }
        }

        meta.setUnbreakable(unbreakable);

        if (meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
            leatherArmorMeta.setColor(Color.fromRGB(color));
        }

        if (flags.size() > 0) {
            for (String flag : flags) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }

        item.setItemMeta(meta);

        item = ItemStackUtility.setMovable(item, movable);
        item = ItemStackUtility.setCallbacks(item, callbacks);

        return item;
    }
}
