package me.super_miner_1.minigameengine.animations;

import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.time.Time;

public class AnimationState {
    protected Id id;
    protected Time time;

    public AnimationState(Id id) {
        this.id = id;
    }

    public void lerp(AnimationState other, float time) {

    }

    public boolean compatibleWith(AnimationState other) {
        return true;
    }

    public Id getId() {
        return id;
    }

    public Time getTime() {
        return time;
    }
}
