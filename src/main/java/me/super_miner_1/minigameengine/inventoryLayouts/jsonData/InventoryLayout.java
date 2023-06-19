package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class InventoryLayout {
    public String name;
    public int rows;
    public ArrayList<InventoryLayer> layers = new ArrayList<InventoryLayer>();

    public static InventoryLayout load(String filePath) {
        String path = MinigameEngine.engine.getDataFolder().getAbsolutePath() + "/" + filePath;

        try {
            InventoryLayout data = MinigameEngine.engine.getGson().fromJson(new FileReader(path), InventoryLayout.class);

            data.layers.sort(Comparator.comparingInt(layer -> layer.layer));

            return data;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Inventory getInventory(String filePath) {
        InventoryLayout inventoryLayoutData = load(filePath);
        return inventoryLayoutData.buildInventory();
    }

    public Inventory createCompatibleInventory() {
        return Bukkit.createInventory(null, 9 * rows, name);
    }

    public void apply(Inventory inventory) {
        inventory.clear();
        for (InventoryLayer layer : layers) {
            layer.apply(inventory);
        }
    }

    public Inventory buildInventory() {
        Inventory inventory = createCompatibleInventory();
        apply(inventory);
        return inventory;
    }
}
