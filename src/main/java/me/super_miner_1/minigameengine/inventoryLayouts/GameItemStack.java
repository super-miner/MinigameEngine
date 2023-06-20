package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.events.external.UIClickEvent;
import me.super_miner_1.minigameengine.events.internal.InternalUIClickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.jsonData.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;

public class GameItemStack implements Listener {
    private ItemStack item = null;
    private boolean movable = true;
    private ArrayList<Interaction> callbacks = new ArrayList<Interaction>();
    private Inventory inventory = null;
    private int slot = -1;

    public GameItemStack(ItemStack item) {
        this.item = item;
    }

    public GameItemStack(ItemStack item, boolean movable, ArrayList<Interaction> callbacks) {
        this.item = item;
        this.movable = movable;
        this.callbacks = callbacks;
    }

    public GameItemStack(ItemStack item, boolean movable, ArrayList<Interaction> callbacks, Inventory inventory, int slot) {
        this.item = item;
        this.movable = movable;
        this.callbacks = callbacks;
        this.inventory = inventory;
        this.slot = slot;
    }

    public void updateInventory() {
        inventory.setItem(slot, item);
    }

    public ItemStack getItemStack() {
        return item;
    }

    public void setItemStack(ItemStack itemStack) {
        this.item = itemStack;
    }

    public boolean isMovable() {
        return movable;
    }

    public ArrayList<Interaction> getCallbackIds() {
        return callbacks;
    }

    public int getAmount() {
        return item.getAmount();
    }

    public void setAmount(int amount) {
        item.setAmount(amount);
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public GameItemStack clone() {
        return new GameItemStack(item.clone(), movable, (ArrayList<Interaction>) callbacks.clone(), inventory, slot);
    }

    public void Destroy() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (item == null) {
            return;
        }

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

        if (clickedItem.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) { // TODO: Use NBT data for this
            ClickType clickType = event.getClick();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(clickType)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, this, interaction.id));
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, this, interaction.id));
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

        if (clickedItem.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
            Action action = event.getAction();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(action)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, this, interaction.id));
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, this, interaction.id));
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
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, this, interaction.id));
                }
            }

            if (!movable) {
                event.setCancelled(true);
            }
        }
    }
}
