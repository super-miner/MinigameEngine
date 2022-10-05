package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.events.ServerStartEvent;
import me.super_miner_1.minigameengine.events.UIClickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.jsonData.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GameItemStack implements Listener {
    private ItemStack item;
    private boolean movable = true;
    private ArrayList<Interaction> callbacks = new ArrayList<Interaction>();

    public GameItemStack(ItemStack item) {
        this.item = item;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public GameItemStack(ItemStack item, boolean movable, ArrayList<Interaction> callbacks) {
        this.item = item;
        this.movable = movable;
        this.callbacks = callbacks;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public ItemStack getItemStack() {
        return item;
    }

    public boolean isMovable() {
        return movable;
    }

    public ArrayList<Interaction> getCallbackIds() {
        return callbacks;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();

        if (!(clicker instanceof Player)) {
            return;
        }

        Player player = (Player) clicker;

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) {
            return;
        }

        ItemStack clickedItem = clickedInventory.getItem(event.getSlot());

        if (clickedItem == null) {
            return;
        }

        if (clickedItem.equals(item)) {
            ClickType clickType = event.getClick();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(clickType)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, this, interaction.id));
                }
            }

            if (!movable) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack clickedItem = event.getItem();

        if (clickedItem == null) {
            return;
        }

        if (clickedItem.equals(item)) {
            Action action = event.getAction();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(action)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, this, interaction.id));
                }
            }

            if (!movable) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        ItemStack clickedItem = event.getItemDrop().getItemStack();

        if (clickedItem.equals(item)) {
            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(true)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, this, interaction.id));
                }
            }

            if (!movable) {
                event.setCancelled(true);
            }
        }
    }
}
