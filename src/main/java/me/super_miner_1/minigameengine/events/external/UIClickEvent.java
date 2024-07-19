package me.super_miner_1.minigameengine.events.external;

import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;

public class UIClickEvent extends Event implements Cancellable {
    private Player player;
    private Inventory inventory;
    private GameItemStack gameItem;
    private String callbackId;
    @Nullable private BlockFace blockFace;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public UIClickEvent(Player player, Inventory inventory, GameItemStack gameItem, String callbackId, @Nullable BlockFace blockFace) {
        this.player = player;
        this.inventory = inventory;
        this.gameItem = gameItem;
        this.callbackId = callbackId;
        this.blockFace = blockFace;
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

    public @Nullable BlockFace getBlockFace() {
        return blockFace;
    }
}
