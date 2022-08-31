package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class InventoryLayoutData {
    public String name;
    public int rows;
    public ArrayList<InventoryLayoutLayer> layers = new ArrayList<InventoryLayoutLayer>();

    public static InventoryLayoutData load(String filePath) {
        String path = MinigameEngine.engine.getDataFolder().getAbsolutePath() + "/" + filePath;

        try {
            InventoryLayoutData data = MinigameEngine.engine.getGson().fromJson(new FileReader(path), InventoryLayoutData.class);

            data.layers.sort(new Comparator<InventoryLayoutLayer>() {
                public int compare(InventoryLayoutLayer layer1, InventoryLayoutLayer layer2) {
                    return layer1.layer - layer2.layer;
                }
            });

            return data;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Inventory getInventory(String filePath) {
        InventoryLayoutData inventoryLayoutData = load(filePath);
        return inventoryLayoutData.buildInventory();
    }

    public Inventory createCompatibleInventory() {
        return Bukkit.createInventory(null, 9 * rows, name);
    }

    public void apply(Inventory inventory) {
        for (InventoryLayoutLayer layer : layers) {
            layer.apply(inventory);
        }
    }

    public Inventory buildInventory() {
        Inventory inventory = createCompatibleInventory();
        apply(inventory);
        return inventory;
    }
}
