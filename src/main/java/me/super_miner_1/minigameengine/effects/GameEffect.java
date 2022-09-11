package me.super_miner_1.minigameengine.effects;

import me.super_miner_1.minigameengine.GamePlayer;
import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.Time;
import me.super_miner_1.minigameengine.events.PotionEffectExpireEvent;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEffect {
    private GamePlayer player;
    private Id id;
    private GameEffectGroup group;
    private long startTime;
    private long duration;
    private double value;
    private EffectCompoundFunction compoundFunction;
    private int priority;
    private boolean ambient = false;
    private boolean particles = true;
    private boolean icon = true;

    public GameEffect(GamePlayer player, Id id, GameEffectGroup group, int duration, double value, EffectCompoundFunction compoundFunction, int priority, PotionEffectType type, boolean ambient, boolean particles, boolean icon) {
        this.player = player;
        this.id = id;
        this.group = group;
        this.startTime = Time.getTime();
        this.duration = duration;
        this.value = value;
        this.compoundFunction = compoundFunction;
        this.priority = priority;
        this.ambient = ambient;
        this.particles = particles;
        this.icon = icon;

        startCallbackTimer();
    }

    private void startCallbackTimer() {
        if (duration < 0) { // Set duration to -1 for infinite duration.
            return;
        }

        GameEffect thisReference = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new PotionEffectExpireEvent(player.getPlayer(), thisReference));
                cancel();
            }
        }.runTaskTimer(MinigameEngine.engine, this.duration, 0);
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public Id getId() {
        return id;
    }

    public GameEffectGroup getGroup() {
        return group;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    public long getEndTime() {
        if (duration < 0) {
            return -1;
        }
        else {
            return startTime + duration;
        }
    }

    public long getTimeLeft() {
        if (duration < 0) {
            return -1;
        }
        else {
            return getEndTime() - Time.getTime();
        }
    }

    public double getValue() {
        return value;
    }

    public EffectCompoundFunction getCompoundFunction() {
        return compoundFunction;
    }

    public void setCompoundFunction(EffectCompoundFunction compoundFunction) {
        this.compoundFunction = compoundFunction;
    }

    public int getPriority() {
        return priority;
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
