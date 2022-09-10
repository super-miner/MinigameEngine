package me.super_miner_1.minigameengine;

import me.super_miner_1.minigameengine.MinigameEngine;

public class Time {
    public static long getTime() {
        return MinigameEngine.engine.getTicksSinceStart();
    }

    public static long ticksToMilliseconds(long ticks) {
        return ticks * 50;
    }

    public static double ticksToSeconds(long ticks) {
        return ticks / 20.0;
    }

    public static long millisecondsToTicks(long milliseconds) {
        return (long) Math.ceil(milliseconds / 50.0);
    }

    public static long secondsToTicks(double seconds) {
        return (long) Math.ceil(seconds * 20.0);
    }
}
