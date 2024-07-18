package me.super_miner_1.minigameengine.events.internal;

import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class InternalUIClickEvent extends Event implements Cancellable {
    private Player player;
    private Inventory inventory;
    private GameItemStack gameItem;
    private String callbackId;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public InternalUIClickEvent(Player player, Inventory inventory, GameItemStack gameItem, String callbackId) {
        this.player = player;
        this.inventory = inventory;
        this.gameItem = gameItem;
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

    public GameItemStack getItem() {
        return gameItem;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
