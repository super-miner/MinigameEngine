package me.super_miner_1.minigameengine;

import me.super_miner_1.minigameengine.events.PotionEffectExpireEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GamePlayer implements Listener {
    private static Class<? extends GamePlayer> overrideClass = GamePlayer.class;

    protected Player player;
    protected ArrayList<GamePotionEffect> effects = new ArrayList<GamePotionEffect>();
    protected Entity morph;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public static void setOverrideClass(Class<? extends GamePlayer> value) {
        overrideClass = value;
    }

    public static Class<? extends GamePlayer> getOverrideClass() {
        return overrideClass;
    }

    public void onTick() {
        ArrayList<PotionEffectType> effectTypes = getActiveEffectTypes();
        for (PotionEffectType effectType : effectTypes) {
            GamePotionEffect highestLevelEffect = getHighestLevelEffect(effectType);
            GamePotionEffect gamePotionEffect = (GamePotionEffect) player.getPotionEffect(effectType);

            if (highestLevelEffect != null && highestLevelEffect.getAmplifier() != gamePotionEffect.getAmplifier()) {
                player.removePotionEffect(effectType);
                player.addPotionEffect(highestLevelEffect);
            }
        }

        if (morph != null) {
            morph.teleport(player.getLocation());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void morph(EntityType entityType) {
        if (entityType == null) {
            morph = null;
            return;
        }

        morph = player.getWorld().spawnEntity(player.getLocation(), entityType);
        morph.setGravity(false);
        morph.setInvulnerable(true);
        morph.setSilent(true);

        addPotionEffect(new Id("MORPH_INVIS"), PotionEffectType.INVISIBILITY, 999999, 1, false, false, false);
    }

    public void unmorph() {
        morph.remove();
        morph = null;

        removePotionEffect(new Id("MORPH_INVIS"));
    }

    public void addPotionEffect(Id id, PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        GamePotionEffect effect = new GamePotionEffect(id, player, type, duration, amplifier, ambient, particles, icon);

        effects.add(effect);
        player.addPotionEffect(effect);
    }

    public void addPotionEffect(Id id, PotionEffectType type, int duration, int amplifier) {
        addPotionEffect(id, type, duration, amplifier, true, true, true);
    }

    public boolean hasPotionEffect(Id id) {
        for (GamePotionEffect effect : effects) {
            if (effect.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<GamePotionEffect> getPotionEffects(Id id) {
        ArrayList<GamePotionEffect> output = new ArrayList<GamePotionEffect>();
        for (GamePotionEffect effect : effects) {
            if (effect.getId().equals(id)) {
                output.add(effect);
            }
        }
        return output;
    }

    public void removePotionEffect(Id id) {
        removePotionEffect(id, true);
    }

    public void removePotionEffect(Id id, boolean removeAllInstances) {
        for (GamePotionEffect effect : effects) {
            if (effect.getId().equals(id)) {
                removePotionEffect(effect);
                if (!removeAllInstances) {
                    return;
                }
            }
        }
    }

    public void removePotionEffect(GamePotionEffect effect) {
        effects.remove(effect);
    }

    protected GamePotionEffect getHighestLevelEffect(PotionEffectType type) {
        GamePotionEffect highest = null;
        for (GamePotionEffect effect : effects) {
            if (effect.getType().equals(type) && (highest == null || effect.getAmplifier() > highest.getAmplifier())) {
                highest = effect;
            }
        }
        return highest;
    }

    protected ArrayList<PotionEffectType> getActiveEffectTypes() {
        ArrayList<PotionEffectType> output = new ArrayList<PotionEffectType>();
        for (GamePotionEffect effect : effects) {
            if (!output.contains(effect.getType())) {
                output.add(effect.getType());
            }
        }
        return output;
    }

    @EventHandler
    public void potionEffectExpireEvent(PotionEffectExpireEvent event) {
        if (event.getPlayer() == player) {
            removePotionEffect(event.getEffect());
        }
    }
}
