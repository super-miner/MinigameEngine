package me.super_miner_1.minigameengine;

import me.super_miner_1.minigameengine.events.PotionEffectExpireEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GamePotionEffect extends PotionEffect {
    protected Id id;
    protected Player player;
    protected long startTime;
    protected long endTime;
    protected long length;

    public GamePotionEffect(Id id, Player player, PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        super(type, duration, amplifier, ambient, particles, icon);

        this.id = id;
        this.player = player;
        this.startTime = Time.getTime();
        this.length = duration;
        this.endTime = startTime + length;

        GamePotionEffect thisReference = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new PotionEffectExpireEvent(player, thisReference));
                cancel();
            }
        }.runTaskTimer(MinigameEngine.engine, this.length, 0);
    }

    public GamePotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        super(type, duration, amplifier, ambient, particles);
    }

    public GamePotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        super(type, duration, amplifier, ambient);
    }

    public GamePotionEffect(PotionEffectType type, int duration, int amplifier) {
        super(type, duration, amplifier);
    }

    public Id getId() {
        return id;
    }

    @Override
    public int getDuration() {
        MinigameEngine.engine.consoleWarn("GamePotionEffect.getDuration() is not he right function to use with the GamePotionEffect class. Instead use GamePotionEffect.getLength().getTimeTicks()", MinigameEngine.WarnPriority.LOW);

        return super.getDuration();
    }

    public long getLength() {
        return length;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTimeLeft() {
        return endTime - Time.getTime();
    }
}
