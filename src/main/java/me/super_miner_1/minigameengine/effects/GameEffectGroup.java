package me.super_miner_1.minigameengine.effects;

import me.super_miner_1.minigameengine.GamePlayer;
import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GameEffectGroup {
    private ArrayList<GameEffect> effects = new ArrayList<GameEffect>();
    private Id id;
    private PotionEffectType type;

    public GameEffectGroup(Id id, PotionEffectType type) {
        this.id = id;
        this.type = type;
    }

    public double calculateEffects(GamePlayer player) {
        effects.sort((GameEffect effectA, GameEffect effectB) -> Integer.compare(effectB.getPriority(), effectA.getPriority()));

        double value = 0.0;
        boolean ambient = false;
        boolean particles = false;
        boolean icon = false;

        for (GameEffect effect : effects) {
            value = effect.getCompoundFunction().run(value, effect);

            if (effect.isAmbient()) {
                ambient = true;
            }
            if (effect.hasParticles()) {
                particles = true;
            }
            if (effect.hasIcon()) {
                icon = true;
            }
        }

        if (type != null) {
            if (value > 0) {
                player.getPlayer().addPotionEffect(new PotionEffect(type, 999999, (int) Math.round(value), ambient, particles, icon));
            }
            else {
                player.getPlayer().removePotionEffect(type);
            }
        }

        return value;
    }

    public GameEffect addEffect(GameEffect effect) {
        Bukkit.broadcastMessage("Adding effect to group");

        effects.add(effect);
        return effect;
    }

    public GameEffect getEffect(Id id) {
        for (GameEffect effect : effects) {
            if (id.equals(effect.getId())) {
                return effect;
            }
        }
        return null;
    }

    public GameEffect getEffect(PotionEffectType type) {
        return getEffect(new Id(type.getName()));
    }

    public void removeEffect(Id id) {
        GameEffect effect = getEffect(id);

        if (effect == null) {
            return;
        }

        effects.remove(effect);
    }

    public Id getId() {
        return id;
    }

    public PotionEffectType getType() {
        return type;
    }
}
