package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.Time;
import org.bukkit.entity.Player;

public class Cooldown {
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
    }

    public Cooldown(String id, Player player, long length) {
        this.id = new Id(id);
        this.player = player;
        this.startTime = Time.getTime();
        this.endTime = startTime + length;
        this.length = length;
    }

    public void run() {

    }

    public void cancel() {

    }

    public long getTimeLeft() {
        return endTime - Time.getTime();
    }

    public void setActive(boolean value) {
        active = value;
    }
}
