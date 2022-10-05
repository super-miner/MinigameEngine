package me.super_miner_1.minigameengine;

import me.super_miner_1.minigameengine.effects.EffectCompoundFunction;
import me.super_miner_1.minigameengine.effects.EffectCompoundPresets;
import me.super_miner_1.minigameengine.effects.GameEffect;
import me.super_miner_1.minigameengine.effects.GameEffectGroup;
import me.super_miner_1.minigameengine.events.PotionEffectExpireEvent;
import me.super_miner_1.minigameengine.events.ServerTickEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GamePlayer extends GameEventHandler implements Listener {
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

    @EventHandler
    public void onTick(ServerTickEvent event) {
        MinigameEngine.consoleLog("DEBUG");
        player.updateInventory(); // Needed to fix ghost item glitch when opening a menu from your inventory.

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

    public Entity morph(EntityType entityType) {
        if (entityType == null) {
            morph = null;
            return null;
        }

        morph = player.getWorld().spawnEntity(player.getLocation(), entityType);
        morph.setGravity(false);
        morph.setInvulnerable(true);
        morph.setSilent(true);

        addEffect(new Id("MORPH_INVIS"), PotionEffectType.INVISIBILITY, -1, 1.0, EffectCompoundPresets.MIN, -1);

        return morph;
    }

    public void unmorph() {
        morph.remove();
        morph = null;

        removeEffect(new Id("MORPH_INVIS"), PotionEffectType.INVISIBILITY);
    }

    public GameEffectGroup addEffectGroup(Id id, PotionEffectType type) {
        if (getEffectGroup(id) != null) {
            return null;
        }

        GameEffectGroup effectGroup = new GameEffectGroup(id, type);
        effectGroups.add(effectGroup);
        return effectGroup;
    }

    public GameEffectGroup addEffectGroup(PotionEffectType type) {
        return addEffectGroup(new Id(type.getName()), type);
    }

    public GameEffectGroup addEffectGroup(Id id) {
        return addEffectGroup(id, null);
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

    public GameEffect addEffect(Id id, Id groupId, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority, boolean ambient, boolean particles, boolean icon) {
        GameEffectGroup group = getEffectGroup(groupId);

        if (group == null) {
            group = addEffectGroup(groupId, type);
        }

        if (group.getEffect(id) != null) {
            return null;
        }

        GameEffect effect = new GameEffect(this, id, group, (duration < 0 && group.getType() != null ? 999999 : duration), value, effectCompoundFunction, priority, null, ambient, particles, icon);

        group.addEffect(effect);
        return effect;
    }

    public GameEffect addEffect(Id id, Id groupId, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority, boolean ambient, boolean particles, boolean icon) {
        return addEffect(id, groupId, null, duration, value, effectCompoundFunction, priority, ambient, particles, icon);
    }

    public GameEffect addEffect(Id id, Id groupId, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority) {
        return addEffect(id, groupId, null, duration, value, effectCompoundFunction, priority, false, true, true);
    }

    public GameEffect addEffect(Id id, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority, boolean ambient, boolean particles, boolean icon) {
        return addEffect(id, new Id(type.getName()), type, duration, value, effectCompoundFunction, priority, ambient, particles, icon);
    }

    public GameEffect addEffect(Id id, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority) {
        return addEffect(id, new Id(type.getName()), type, duration, value, effectCompoundFunction, priority, false, true, true);
    }

    public void removeEffect(Id id, Id groupId) {
        GameEffectGroup group = getEffectGroup(groupId);

        if (group == null) {
            MinigameEngine.consoleWarn("Used a non-existent group id when removing an effect.", MinigameEngine.WarnPriority.LOW);
            return;
        }

        group.removeEffect(id);
    }

    public void removeEffect(Id id, PotionEffectType type) {
        removeEffect(id, new Id(type.getName()));
    }

    @EventHandler
    public void potionEffectExpireEvent(PotionEffectExpireEvent event) {
        if (event.getPlayer() == player) {
            removeEffect(event.getEffect().getId(), event.getEffect().getGroup().getId());
        }
    }
}
