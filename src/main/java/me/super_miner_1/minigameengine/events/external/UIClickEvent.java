package me.super_miner_1.minigameengine.events.external;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UIClickEvent extends Event implements Cancellable {
    private Player player;
    private Inventory inventory;
    private ItemStack item;
    private String callbackId;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public UIClickEvent(Player player, Inventory inventory, ItemStack item, String callbackId) {
        this.player = player;
        this.inventory = inventory;
        this.item = item;
        this.callbackId = callbackId;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
