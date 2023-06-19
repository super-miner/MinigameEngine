package me.super_miner_1.minigameengine.events.internal;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InternalServerTickEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    long deltaTime;

    public InternalServerTickEvent(long deltaTime) {
        this.deltaTime = deltaTime;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public long getDeltaTime() {
        return deltaTime;
    }
}
