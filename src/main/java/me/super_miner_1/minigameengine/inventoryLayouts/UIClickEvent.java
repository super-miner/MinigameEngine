package me.super_miner_1.minigameengine.inventoryLayouts;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UIClickEvent extends Event implements Cancellable {
    private Player player;
    private GameItemStack item;
    private String callbackId;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public UIClickEvent(Player player, GameItemStack item, String callbackId) {
        this.player = player;
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

    public GameItemStack getItem() {
        return item;
    }

    public String getCallbackId() {
        return callbackId;
    }
}
