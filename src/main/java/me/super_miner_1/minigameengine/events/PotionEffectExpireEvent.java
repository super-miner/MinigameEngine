package me.super_miner_1.minigameengine.events;

import me.super_miner_1.minigameengine.GamePotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PotionEffectExpireEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    Player player = null;
    GamePotionEffect effect = null;

    public PotionEffectExpireEvent(Player player, GamePotionEffect effect) {
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

    public GamePotionEffect getEffect() {
        return effect;
    }
}
