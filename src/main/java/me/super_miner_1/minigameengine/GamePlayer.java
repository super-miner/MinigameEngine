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
    protected ArrayList<GameEffectGroup> effectGroups = new ArrayList<GameEffectGroup>();
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
        for (GameEffectGroup effectGroup : effectGroups) {
            effectGroup.applyEffects(this);
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

        if (getEffectGroup(PotionEffectType.INVISIBILITY) == null) addEffectGroup(PotionEffectType.INVISIBILITY, false, false, false);
        addEffect(new Id("MORPH_INVIS"), PotionEffectType.INVISIBILITY, 999999, 1.0, EffectCompoundPresets.MIN, -1);
    }

    public void unmorph() {
        morph.remove();
        morph = null;

        removeEffect(new Id("MORPH_INVIS"), PotionEffectType.INVISIBILITY);
    }

    public GameEffectGroup addEffectGroup(PotionEffectType type, boolean ambient, boolean particles, boolean icon) {
        Id id = new Id(type.getName());
        if (getEffectGroup(id) != null) {
            return null;
        }

        GameEffectGroup effectGroup = new GameEffectGroup(id, type, ambient, particles, icon);
        effectGroups.add(effectGroup);
        return effectGroup;
    }

    public GameEffectGroup addEffectGroup(PotionEffectType type) {
        return addEffectGroup(type, false, true, true);
    }

    public GameEffectGroup addEffectGroup(Id id) {
        if (getEffectGroup(id) != null) {
            return null;
        }

        GameEffectGroup effectGroup = new GameEffectGroup(id, null, false, true, true);
        effectGroups.add(effectGroup);
        return effectGroup;
    }

    public GameEffectGroup getEffectGroup(Id id) {
        for (GameEffectGroup effectGroup : effectGroups) {
            if (id.equals(effectGroup.getId())) {
                return effectGroup;
            }
        }
        return null;
    }

    public GameEffectGroup getEffectGroup(PotionEffectType type) {
        return getEffectGroup(new Id(type.getName()));
    }

    public void removeEffectGroup(Id id) {
        effectGroups.remove(getEffectGroup(id));
    }

    public void removeEffectGroup(PotionEffectType type) {
        removeEffectGroup(new Id(type.getName()));
    }

    public GameEffect addEffect(Id id, Id groupId, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority) {
        GameEffectGroup group = getEffectGroup(groupId);

        if (group.getEffect(id) != null) {
            return null;
        }

        GameEffect effect = new GameEffect(this, id, group, (duration < 0 && group.getType() != null ? 999999 : duration), value, effectCompoundFunction, priority, null, false, true, true);

        group.addEffect(effect);
        return effect;
    }

    public GameEffect addEffect(Id id, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority) {
        return addEffect(id, new Id(type.getName()), duration, value, effectCompoundFunction, priority);
    }

    public void removeEffect(Id id, Id groupId) {
        GameEffectGroup group = getEffectGroup(groupId);

        if (group == null) {
            MinigameEngine.consoleWarn("Used a non-existant group id when removing an effect.", MinigameEngine.WarnPriority.LOW);
            return;
        }

        group.removeEffect(id);
    }

    public void removeEffect(Id id, PotionEffectType type) {
        removeEffect(id, new Id(type.getName()));
    }

    /*public boolean hasPotionEffect(Id id) {
        for (GameEffect effect : effects) {
            if (effect.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<GameEffect> getPotionEffects(Id id) {
        ArrayList<GameEffect> output = new ArrayList<GameEffect>();
        for (GameEffect effect : effects) {
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
        for (GameEffect effect : effects) {
            if (effect.getId().equals(id)) {
                removePotionEffect(effect);
                if (!removeAllInstances) {
                    return;
                }
            }
        }
    }

    public void removePotionEffect(GameEffect effect) {
        effects.remove(effect);
    }

    protected GameEffect getHighestLevelEffect(PotionEffectType type) {
        GameEffect highest = null;
        for (GameEffect effect : effects) {
            if (effect.getType().equals(type) && (highest == null || effect.getAmplifier() > highest.getAmplifier())) {
                highest = effect;
            }
        }
        return highest;
    }

    protected ArrayList<PotionEffectType> getActiveEffectTypes() {
        ArrayList<PotionEffectType> output = new ArrayList<PotionEffectType>();
        for (GameEffect effect : effects) {
            if (!output.contains(effect.getType())) {
                output.add(effect.getType());
            }
        }
        return output;
    }*/

    @EventHandler
    public void potionEffectExpireEvent(PotionEffectExpireEvent event) {
        if (event.getPlayer() == player) {
            //removePotionEffect(event.getEffect());
        }
    }
}
