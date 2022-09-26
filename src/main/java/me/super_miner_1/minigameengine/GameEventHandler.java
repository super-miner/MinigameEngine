package me.super_miner_1.minigameengine;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class GameEventHandler implements Listener {
    public GameEventHandler() {
        Bukkit.getPluginManager().registerEvents(this, MinigameEngine.engine);
    }
}
