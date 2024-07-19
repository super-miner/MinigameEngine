package me.super_miner_1.minigameengine.animations;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.events.external.ServerTickEvent;
import me.super_miner_1.minigameengine.events.internal.InternalServerStopEvent;
import me.super_miner_1.minigameengine.events.internal.InternalServerTickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class Animation implements Listener {
    public static ArrayList<Animation> animations = new ArrayList<Animation>();

    protected boolean playing = true;
    protected ArrayList<AnimationTrack> tracks = new ArrayList<AnimationTrack>();
    protected long time;

    public static void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public static void stopAllAnimations() {
        for (Animation animation : animations) {
            animation.forceTracksToEnd();
        }
    }

    public Animation(boolean playOnStart) {
        this.playing = playOnStart;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public Animation() {
        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public void play() {
        playing = true;
    }

    public void pause() {
        playing = false;
    }

    public void addTrack(AnimationTrack track) {
        tracks.add(track);
    }

    public void advanceTracks(long time) {
        for (AnimationTrack track : tracks) {
            track.advanceTime(time);
        }
    }

    public void forceTracksToEnd() {
        for (AnimationTrack track : tracks) {
            track.forceToEnd();
        }
    }

    @EventHandler
    public void onServerTick(InternalServerTickEvent event) {
        advanceTracks(event.getDeltaTime());
    }

    @EventHandler
    public void onServerStop(InternalServerStopEvent event) {
        forceTracksToEnd();
    }
}
