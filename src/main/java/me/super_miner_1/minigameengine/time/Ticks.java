package me.super_miner_1.minigameengine.time;

public class Ticks {
    public long ticks;

    public Ticks(long ticks) {
        this.ticks = ticks;
    }

    public Ticks add(Ticks other) {
        return new Ticks(ticks + other.ticks);
    }

    public Ticks subtract(Ticks other) {
        return new Ticks(ticks - other.ticks);
    }

    public Ticks multiply(Ticks other) {
        return new Ticks(ticks * other.ticks);
    }

    public Ticks divide(Ticks other) {
        return new Ticks(ticks / other.ticks);
    }
}
