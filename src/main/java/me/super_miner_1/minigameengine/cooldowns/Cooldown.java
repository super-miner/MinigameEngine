package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.time.Time;
import org.bukkit.entity.Player;

public class Cooldown {
    protected Id id;
    protected Player player;
    protected Time startTime;
    protected Time endTime;
    protected Time length;
    protected boolean active = true;

    public Cooldown(int id, Player player, Time length) {
        this.id = new Id(id);
        this.player = player;
        this.startTime = Time.getTime();
        this.endTime = startTime.add(length);
        this.length = length;
    }

    public Cooldown(String id, Player player, Time length) {
        this.id = new Id(id);
        this.player = player;
        this.startTime = Time.getTime();
        this.endTime = startTime.add(length);
        this.length = length;
    }

    public void run() {

    }

    public void cancel() {

    }

    public Time getTimeLeft() {
        return endTime.subtract(Time.getTime());
    }

    public void setActive(boolean value) {
        active = value;
    }
}
