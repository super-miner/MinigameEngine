package me.super_miner_1.minigameengine.effects;

public class EffectCompoundPresets {
    public static EffectCompoundFunction MIN = (currentValue, effect) -> Math.max(currentValue, effect.getValue());
    public static EffectCompoundFunction MAX = (currentValue, effect) -> Math.min(currentValue, effect.getValue());
    public static EffectCompoundFunction ADD = (currentValue, effect) -> currentValue + effect.getValue();
    public static EffectCompoundFunction MULTIPLY = (currentValue, effect) -> currentValue * effect.getValue();
}
