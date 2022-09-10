package me.super_miner_1.minigameengine.animations;

import java.util.ArrayList;

public class AnimationTrack {
    protected ArrayList<AnimationState> states = new ArrayList<AnimationState>();
    protected long time;

    public boolean addState(AnimationState state) {
        if (states.size() <= 0 || states.get(0).compatibleWith(state)) {
            states.add(state);
            return true;
        }
        else {
            return false;
        }
    }

    public void advanceTime(long newTime) {
        time = newTime;

        AnimationState currentState = getCurrentState(time);
        AnimationState nextState = getNextState(time);

        float animTime = ((float) time - currentState.getTime()) / (nextState.getTime() - currentState.getTime());

        currentState.lerp(nextState, animTime);
    }

    private AnimationState getCurrentState(long time) {
        if (states.size() <= 0) {
            return null;
        }

        AnimationState best = states.get(0);

        for (AnimationState state : states) {
            if (state.getTime() <= time && state.getTime() > best.getTime()) {
                best = state;
            }
        }

        return best;
    }

    private AnimationState getNextState(long time) {
        if (states.size() <= 0) {
            return null;
        }

        AnimationState best = states.get(0);

        for (AnimationState state : states) {
            if (state.getTime() > time && state.getTime() < best.getTime()) {
                best = state;
            }
        }

        return best;
    }
}
