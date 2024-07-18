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

    public void Set(ItemStack item) {
        inventory.setItem(slot, item);
    }

    public ItemStack Get() {
        return inventory.getItem(slot);
    }
}
