package me.super_miner_1.minigameengine;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GameEffectGroup {
    private ArrayList<GameEffect> effects = new ArrayList<GameEffect>();
    private Id id;
    private PotionEffectType type;
    private boolean ambient;
    private boolean particles;
    private boolean icon;

    public GameEffectGroup(Id id, PotionEffectType type, boolean ambient, boolean particles, boolean icon) {
        this.id = id;
        this.type = type;
        this.ambient = ambient;
        this.particles = particles;
        this.icon = icon;
    }

    public void applyEffects(GamePlayer player) {
        effects.sort((GameEffect effectA, GameEffect effectB) -> Integer.compare(effectB.getPriority(), effectA.getPriority()));

        double value = 0.0;
        for (GameEffect effect : effects) {
            value = effect.getCompoundFunction().run(value, effect);
        }

        if (type != null) {
            if (value > 0) {
                player.getPlayer().addPotionEffect(new PotionEffect(type, 1, (int) Math.round(value), ambient, particles, icon));
            }
            else {
                player.getPlayer().removePotionEffect(type);
            }
        }
    }

    public GameEffect addEffect(GameEffect effect) {
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

    public boolean isAmbient() {
        return ambient;
    }

    public boolean hasParticles() {
        return particles;
    }

    public boolean hasIcon() {
        return icon;
    }
}
