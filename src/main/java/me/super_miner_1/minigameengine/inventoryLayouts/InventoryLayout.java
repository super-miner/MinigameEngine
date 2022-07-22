package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Bukkit;
import org.bukkit.block.Container;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class InventoryLayout implements Listener {
    private HashMap<Integer, LayoutItem> items = new HashMap<Integer, LayoutItem>();
    private Inventory inventory;
    private boolean canMoveItems = false;

    public InventoryLayout(Inventory inventory) {
        this.inventory = inventory;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(Inventory inventory, boolean canMoveItems) {
        this.inventory = inventory;
        this.canMoveItems = canMoveItems;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(int rows) {
        this.inventory = Bukkit.createInventory(null, rows * 9);

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(int rows, boolean canMoveItems) {
        this.inventory = Bukkit.createInventory(null, rows * 9);
        this.canMoveItems = canMoveItems;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(int rows, String title) {
        this.inventory = Bukkit.createInventory(null, rows * 9, title);

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(int rows, String title, boolean canMoveItems) {
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        this.canMoveItems = canMoveItems;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(String title, InventoryType type) {
        this.inventory = Bukkit.createInventory(null, type, title);

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public InventoryLayout(String title, InventoryType type, boolean canMoveItems) {
        this.inventory = Bukkit.createInventory(null, type, title);
        this.canMoveItems = canMoveItems;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public void openInventory(HumanEntity entity) {
        entity.openInventory(inventory);
    }

    public InventoryLayout setItem(int column, int row, LayoutItem item) {
        if (inventory.getType() != InventoryType.BARREL
            && inventory.getType() != InventoryType.CHEST
            && inventory.getType() != InventoryType.CREATIVE
            && inventory.getType() != InventoryType.ENDER_CHEST
            && inventory.getType() != InventoryType.PLAYER
            && inventory.getType() != InventoryType.SHULKER_BOX) {
                MinigameEngine.consoleWarn("Use of setItem(column, row, item) with an unsupported inventory, use setItem(slot, item) instead.", MinigameEngine.WarnPriority.MEDIUM);
        }

        return setItem(row * 9 + column, item);
    }

    public InventoryLayout setItem(int slot, LayoutItem item) {
        if (item != null) {
            inventory.setItem(slot, item.getItem().getItemStack());
        }
        else {
            inventory.setItem(slot, null);
        }

        if (!items.containsKey(slot)) {
            items.put(slot, item);
        }
        items.replace(slot, item);

        return this;
    }

    public InventoryLayout fill(LayoutItem item) {
        for (int slot = 0; slot < inventory.getSize(); slot ++) {
            setItem(slot, item);
        }
        return this;
    }

    private void clearInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            setItem(i, null);
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (!canMoveItems) {
            event.setCancelled(true);
        }

        Inventory inventory = event.getInventory();
        int inventorySlot = event.getSlot();

        for (int slot : items.keySet()) {
            LayoutItem item = items.get(slot);
            if (slot == inventorySlot) {
                item.onInventoryClick(event);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (!canMoveItems) {
            event.setCancelled(true);
        }

        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        int inventorySlot = player.getInventory().getHeldItemSlot();

        for (int slot : items.keySet()) {
            LayoutItem item = items.get(slot);
            if (slot == inventorySlot) {
                item.onClick(event);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void itemDropEvent(PlayerDropItemEvent event) {
        if (!canMoveItems) {
            event.setCancelled(true);
        }

        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        int inventorySlot = player.getInventory().getHeldItemSlot();

        for (int slot : items.keySet()) {
            LayoutItem item = items.get(slot);
            if (slot == inventorySlot) {
                item.onDrop(event);
            }
        }
    }
}
