package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.GameEventHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GameInventory extends GameEventHandler implements Listener {
    private Inventory inventory;

    public GameInventory(Inventory inventory) {
        super();

        this.inventory = inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inventory)) {
            return;
        }

        // TODO
    }
}
