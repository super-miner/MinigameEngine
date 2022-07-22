package me.super_miner_1.minigameengine;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

public class GameItemStack {
    private static HashMap<Id, GameItemStack> gameItemStacks = new HashMap<Id, GameItemStack>();

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public GameItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
    }

    public GameItemStack(Material type, int amount) {
        itemStack = new ItemStack(type, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public GameItemStack(Material type) {
        itemStack = new ItemStack(type);
        itemMeta = itemStack.getItemMeta();
    }

    public GameItemStack addEnchantment(Enchantment ench, int level) {
        itemStack.addEnchantment(ench, level);

        return this;
    }

    public GameItemStack addEnchantments(Map<Enchantment,Integer> enchantments) {
        itemStack.addEnchantments(enchantments);

        return this;
    }

    public GameItemStack clone() {
        return new GameItemStack(itemStack);
    }

    public boolean containsEnchantment(Enchantment ench) {
        return itemStack.containsEnchantment(ench);
    }

    public boolean equals(GameItemStack gameItemStack) {
        return itemStack.equals(gameItemStack.itemStack);
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public MaterialData getData() {
        return itemStack.getData();
    }

    public int getEnchantmentLevel(Enchantment ench) {
        return itemStack.getEnchantmentLevel(ench);
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return itemStack.getEnchantments();
    }

    public int getMaxStackSize() {
        return itemStack.getMaxStackSize();
    }

    public Material getType() {
        return itemStack.getType();
    }

    public boolean hasItemMeta() {
        return itemStack.hasItemMeta();
    }

    public boolean isSimilar(GameItemStack gameItemStack) {
        return itemStack.isSimilar(gameItemStack.itemStack);
    }

    public GameItemStack removeEnchantment(Enchantment ench) {
        itemStack.removeEnchantment(ench);

        return this;
    }

    public GameItemStack setAmount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    public GameItemStack setData(MaterialData materialData) {
        itemStack.setData(materialData);

        return this;
    }

    public GameItemStack setType(Material type) {
        itemStack.setType(type);

        return this;
    }

    public GameItemStack setId(Id id) {
        gameItemStacks.put(id, this);

        return this;
    }

    public GameItemStack addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        itemMeta.addAttributeModifier(attribute, modifier);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        itemMeta.addEnchant(ench, level, ignoreLevelRestriction);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack addItemFlags(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return itemMeta.getAttributeModifiers();
    }

    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute) {
        return itemMeta.getAttributeModifiers(attribute);
    }

    public Multimap<Attribute,AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return itemMeta.getAttributeModifiers(slot);
    }

    public int getCustomModelData() {
        return itemMeta.getCustomModelData();
    }

    public String getDisplayName() {
        return itemMeta.getDisplayName();
    }

    public int getEnchantLevel(Enchantment ench) {
        return itemMeta.getEnchantLevel(ench);
    }

    public Map<Enchantment,Integer> getEnchants() {
        return itemMeta.getEnchants();
    }

    public Set<ItemFlag> getItemFlags() {
        return itemMeta.getItemFlags();
    }

    public String getLocalizedName() {
        return itemMeta.getLocalizedName();
    }

    public List<String> getLore() {
        return itemMeta.getLore();
    }

    public boolean hasAttributeModifiers() {
        return itemMeta.hasAttributeModifiers();
    }

    public boolean hasConflictingEnchant(Enchantment ench) {
        return itemMeta.hasConflictingEnchant(ench);
    }

    public boolean hasCustomModelData() {
        return itemMeta.hasCustomModelData();
    }

    public boolean hasDisplayName() {
        return itemMeta.hasDisplayName();
    }

    public boolean hasEnchant(Enchantment ench) {
        return itemMeta.hasEnchant(ench);
    }

    public boolean hasEnchants() {
        return itemMeta.hasEnchants();
    }

    public boolean hasItemFlag(ItemFlag flag) {
        return itemMeta.hasItemFlag(flag);
    }

    public boolean hasLocalizedName() {
        return itemMeta.hasLocalizedName();
    }

    public boolean hasLore() {
        return itemMeta.hasLore();
    }

    public boolean isUnbreakable() {
        return itemMeta.isUnbreakable();
    }

    public GameItemStack removeAttributeModifier(Attribute attribute) {
        itemMeta.removeAttributeModifier(attribute);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        itemMeta.removeAttributeModifier(attribute, modifier);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack removeAttributeModifier(EquipmentSlot slot) {
        itemMeta.removeAttributeModifier(slot);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack removeEnchant(Enchantment ench) {
        itemMeta.removeEnchant(ench);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack removeItemFlags(ItemFlag... itemFlags) {
        itemMeta.removeItemFlags(itemFlags);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
        itemMeta.setAttributeModifiers(attributeModifiers);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack setCustomModelData(Integer data) {
        itemMeta.setCustomModelData(data);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack setDisplayName(String name) {
        itemMeta.setDisplayName(name);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack setLocalizedName(String name) {
        itemMeta.setLocalizedName(name);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack setLore(List<String> lore) {
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public GameItemStack setUnbreakable(boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);

        itemStack.setItemMeta(itemMeta);


        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
