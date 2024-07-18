package me.super_miner_1.minigameengine.inventoryLayouts;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemStackLocation {
    public Inventory inventory;
    public int slot;

    public ItemStackLocation(int slot, Inventory inventory) {
        this.slot = slot;
        this.inventory = inventory;
    }

    public void set(ItemStack item) {
        inventory.setItem(slot, item);
    }

    public ItemStack get() {
        return inventory.getItem(slot);
    }

    public boolean equals(ItemStackLocation other) {
        return slot == other.slot && inventory == other.inventory;
    }
}
