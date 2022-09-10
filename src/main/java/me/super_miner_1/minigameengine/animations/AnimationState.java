package me.super_miner_1.minigameengine.animations;

import me.super_miner_1.minigameengine.Id;

public class AnimationState {
    protected Id id;
    protected long time;

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

    public long getTime() {
        return time;
    }
}
