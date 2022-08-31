package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryLayoutLayer {
    public int layer;
    public String item;
    public String action = "SINGLE";

    // used for action "SINGLE"
    public int row;
    public int column;

    // used for action "RECT"
    public int row1;
    public int column1;
    public int row2;
    public int column2;

    public void apply(Inventory inventory) {
        ItemStack itemStack = ItemData.getItem(item);

        switch(action) {
            case "SINGLE":
                inventory.setItem((row - 1) * 9 + column - 1, itemStack);
                break;
            case "RECT":
                for (int y = row1 - 1; y < row2; y++) {
                    for (int x = column1 - 1; x < column2; x++) {
                        inventory.setItem(y * 9 + x, itemStack);
                    }
                }

                break;
        }
    }
}
