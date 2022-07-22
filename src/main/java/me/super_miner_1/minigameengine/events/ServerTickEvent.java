package me.super_miner_1.minigameengine.events;

import me.super_miner_1.minigameengine.GamePotionEffect;
import me.super_miner_1.minigameengine.time.Time;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerTickEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    Time deltaTime;

    public ServerTickEvent(Time deltaTime) {
        this.deltaTime = deltaTime;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Time getDeltaTime() {
        return deltaTime;
    }
}
