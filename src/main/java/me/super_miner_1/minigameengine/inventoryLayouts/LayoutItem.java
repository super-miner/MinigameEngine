package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.GameItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LayoutItem {
    private GameItemStack item;

    public LayoutItem(ItemStack item) {
        this.item = new GameItemStack(item);
    }
    public LayoutItem(GameItemStack item) {
        this.item = item;
    }

    public void onInventoryClick(InventoryClickEvent event) {

    }

    public void onClick(PlayerInteractEvent event) {

    }

    public void onDrop(PlayerDropItemEvent event) {

    }

    public GameItemStack getItem() {
        return item;
    }

    public void setItem(GameItemStack value) {
        item = value;
    }
}
