package me.super_miner_1.minigameengine.events.external;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerStartEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
