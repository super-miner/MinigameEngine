package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.inventoryLayouts.GameInventory;
import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

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
        GameInventory

        GameItemStack gameItemStack = ItemData.getItem(item);

        if (gameItemStack == null) {
            MinigameEngine.consoleWarn("Item at path " + item + " not found, skipping this item.", MinigameEngine.WarnPriority.HIGH);
            return;
        }

        GameItemStack gameItemStackCopy;

        switch(action) {
            case "SINGLE":
                gameItemStackCopy = gameItemStack.clone();
                gameItemStackCopy.setInventory(inventory);
                gameItemStackCopy.setSlot(getSlot());

                MinigameEngine.engine.getServer().getPluginManager().registerEvents(gameItemStackCopy, MinigameEngine.engine);

                inventory.setItem(getSlot(), gameItemStackCopy.getItemStack());
                break;
            case "RECT":
                for (int y = row1 - 1; y < row2; y++) {
                    for (int x = column1 - 1; x < column2; x++) {
                        gameItemStackCopy = gameItemStack.clone();
                        gameItemStackCopy.setInventory(inventory);
                        gameItemStackCopy.setSlot(y * 9 + x);
                        MinigameEngine.engine.getServer().getPluginManager().registerEvents(gameItemStackCopy, MinigameEngine.engine);

                        inventory.setItem(y * 9 + x, gameItemStackCopy.getItemStack());
                    }
                }

                break;
        }
    }

    protected int getSlot() {
        return slot < 0 ? (row - 1) * 9 + column - 1 : slot;
    }
}
