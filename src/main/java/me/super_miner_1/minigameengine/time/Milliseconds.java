package me.super_miner_1.minigameengine.time;

public class Milliseconds {
    public long milliseconds;

    public Milliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Milliseconds add(Milliseconds other) {
        return new Milliseconds(milliseconds + other.milliseconds);
    }

    public Milliseconds subtract(Milliseconds other) {
        return new Milliseconds(milliseconds - other.milliseconds);
    }

    public Milliseconds multiply(Milliseconds other) {
        return new Milliseconds(milliseconds * other.milliseconds);
    }

    public Milliseconds divide(Milliseconds other) {
        return new Milliseconds(milliseconds / other.milliseconds);
    }
}
