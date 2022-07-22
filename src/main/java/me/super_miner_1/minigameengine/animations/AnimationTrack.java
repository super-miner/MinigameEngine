package me.super_miner_1.minigameengine.animations;

import me.super_miner_1.minigameengine.time.Time;

import java.util.ArrayList;

public class AnimationTrack {
    protected ArrayList<AnimationState> states = new ArrayList<AnimationState>();
    protected Time time;

    public boolean addState(AnimationState state) {
        if (states.size() <= 0 || states.get(0).compatibleWith(state)) {
            states.add(state);
            return true;
        }
        else {
            return false;
        }
    }

    public void advanceTime(Time newTime) {
        time = newTime;

        AnimationState currentState = getCurrentState(time);
        AnimationState nextState = getNextState(time);

        float animTime = ((float) time.getTimeMilliseconds() - currentState.getTime().getTimeMilliseconds()) / (nextState.getTime().getTimeMilliseconds() - currentState.getTime().getTimeMilliseconds());

        currentState.lerp(nextState, animTime);
    }

    private AnimationState getCurrentState(Time time) {
        if (states.size() <= 0) {
            return null;
        }

        AnimationState best = states.get(0);

        for (AnimationState state : states) {
            if (state.getTime().getTimeMilliseconds() <= time.getTimeMilliseconds() && state.getTime().getTimeMilliseconds() > best.getTime().getTimeMilliseconds()) {
                best = state;
            }
        }

        return best;
    }

    private AnimationState getNextState(Time time) {
        if (states.size() <= 0) {
            return null;
        }

        AnimationState best = states.get(0);

        for (AnimationState state : states) {
            if (state.getTime().getTimeMilliseconds() > time.getTimeMilliseconds() && state.getTime().getTimeMilliseconds() < best.getTime().getTimeMilliseconds()) {
                best = state;
            }
        }

        return best;
    }
}
