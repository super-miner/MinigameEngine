package me.super_miner_1.minigameengine.events.external;

import me.super_miner_1.minigameengine.effects.GameEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PotionEffectExpireEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    Player player = null;
    GameEffect effect = null;

    public PotionEffectExpireEvent(Player player, GameEffect effect) {
        this.player = player;
        this.effect = effect;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public GameEffect getEffect() {
        return effect;
    }
}
