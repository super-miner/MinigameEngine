package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryLayer {
    public int layer;
    public String item;
    public String action = "SINGLE";

    // used for action "SINGLE"
    public int slot = -1;
    public int row;
    public int column;

    // used for action "RECT"
    public int row1;
    public int column1;
    public int row2;
    public int column2;

    public void apply(Inventory inventory) {
        ItemStack itemStack = ItemData.getItem(item);

        if (itemStack == null) {
            MinigameEngine.consoleWarn("Item at path " + item + " not found, skipping this item.", MinigameEngine.WarnPriority.HIGH);
            return;
        }

        ItemStack gameItemStackCopy;

        switch(action) {
            case "SINGLE":
                gameItemStackCopy = itemStack.clone();
                inventory.setItem(getSlot(), gameItemStackCopy);
                break;
            case "RECT":
                for (int y = row1 - 1; y < row2; y++) {
                    for (int x = column1 - 1; x < column2; x++) {
                        gameItemStackCopy = itemStack.clone();
                        inventory.setItem(y * 9 + x, gameItemStackCopy);
                    }
                }

                break;
        }
    }

    protected int getSlot() {
        return slot < 0 ? (row - 1) * 9 + column - 1 : slot;
    }
}
