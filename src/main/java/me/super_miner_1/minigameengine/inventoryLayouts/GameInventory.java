package me.super_miner_1.minigameengine.inventoryLayouts;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameInventory {
    protected Inventory inventory;

    public GameInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setItem(int slot, GameItemStack gameItem) {
        ItemStack previousItem = inventory.getItem(slot);
        GameItemStack previousGameItem = GameItemStack.getGameItemStack(previousItem);

        if (previousGameItem != null) {
            previousGameItem.removeItemLocation(slot, inventory);
        }

        if (gameItem == null) {
            return;
        }

        gameItem.addItemLocation(slot, inventory);
    }

    public GameItemStack getItem(int slot) {
        return GameItemStack.getGameItemStack(inventory.getItem(slot));
    }

    public void clear() {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            GameItemStack gameItem = GameItemStack.getGameItemStack(item);

            if (gameItem == null) {
                continue;
            }

            setItem(i, null);
        }
    }
}
