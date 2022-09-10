package me.super_miner_1.minigameengine.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerTickEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    long deltaTime;

    public ServerTickEvent(long deltaTime) {
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
