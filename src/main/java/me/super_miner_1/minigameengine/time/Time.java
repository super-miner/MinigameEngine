package me.super_miner_1.minigameengine.time;

import me.super_miner_1.minigameengine.MinigameEngine;

public class Time {
    public static Time getTime() {
        return new Time();
    }

    private Ticks ticks;
    private Milliseconds milliseconds;

    public Time() {
        this.ticks = new Ticks(MinigameEngine.engine.getTicksSinceStart());
        this.milliseconds = new Milliseconds(MinigameEngine.engine.getMillisecondsSinceStart());
    }

    public Time(Ticks ticks) {
        this.ticks = ticks;
        this.milliseconds = new Milliseconds(ticks.ticks * 50);
    }

    public Time(Milliseconds milliseconds) {
        this.ticks = new Ticks((long) Math.ceil((float) milliseconds.milliseconds / 50));
        this.milliseconds = milliseconds;
    }

    public Time(Ticks ticks, Milliseconds milliseconds) {
        this.ticks = ticks;
        this.milliseconds = milliseconds;
    }

    public Time add(Time other) {
        return new Time(ticks.add(other.ticks));
    }

    public Time subtract(Time other) {
        return new Time(ticks.subtract(other.ticks));
    }

    public Time multiply(Time other) {
        return new Time(ticks.multiply(other.ticks));
    }

    public Time divide(Time other) {
        return new Time(ticks.divide(other.ticks));
    }

    public Ticks getTicks() {
        return ticks;
    }

    public long getTimeTicks() {
        return ticks.ticks;
    }

    public Milliseconds getMilliseconds() {
        return milliseconds;
    }

    public long getTimeMilliseconds() {
        return milliseconds.milliseconds;
    }
}
