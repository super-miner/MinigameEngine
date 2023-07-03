package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.Time;
import me.super_miner_1.minigameengine.events.internal.InternalServerTickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Cooldown implements Listener {
    protected Id id;
    protected Player player;
    protected long startTime;
    protected long endTime;
    protected long length;
    protected boolean active = true;

    public Cooldown(int id, Player player, long length) {
        this.id = new Id(id);
        this.player = player;
        this.startTime = Time.getTime();
        this.endTime = startTime + length;
        this.length = length;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public Cooldown(String id, Player player, long length) {
        this.id = new Id(id);
        this.player = player;
        this.startTime = Time.getTime();
        this.endTime = startTime + length;
        this.length = length;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public void start() {

    }

    public void onTick() {

    }

    public void cancel() {

    }

    public long getTimeLeft() {
        return endTime - Time.getTime();
    }

    public void setActive(boolean value) {
        active = value;
    }

    @EventHandler
    public void onServerTick(InternalServerTickEvent event) {
        onTick();
    }
}
