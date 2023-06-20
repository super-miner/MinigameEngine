package me.super_miner_1.minigameengine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.super_miner_1.minigameengine.events.external.ServerStartEvent;
import me.super_miner_1.minigameengine.events.external.ServerTickEvent;
import me.super_miner_1.minigameengine.events.internal.InternalServerStartEvent;
import me.super_miner_1.minigameengine.events.internal.InternalServerTickEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public final class MinigameEngine extends JavaPlugin {
    public static MinigameEngine engine;

    public enum WarnPriority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Gson gson = null;
    private Random random = null;
    private long startingMilliseconds = 0;
    private long ticksSinceStart = 0;
    private long lastUpdateTime = 0;
    private long currentUpdateTime = 0;
    private HashMap<Player, GamePlayer> players = new HashMap<Player, GamePlayer>();
    private GamePlayerFactory playerFactory = null;

    public static void consoleLog(String message) {
        Bukkit.getConsoleSender().sendMessage("[Minigame Engine]: " + message);
    }

    public static void consoleWarn(String message, WarnPriority priority) {
        Bukkit.getConsoleSender().sendMessage("[Minigame Engine]: " + ChatColor.YELLOW + message);
    }

    @Override
    public void onEnable() {
        engine = this;

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        this.gson = builder.create();

        this.random = new Random();

        this.startingMilliseconds = System.currentTimeMillis();

        this.lastUpdateTime = Time.getTime();
        this.currentUpdateTime = Time.getTime();

        this.playerFactory = new GamePlayerFactory();

        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            Bukkit.getPluginManager().callEvent(new ServerStartEvent());
            Bukkit.getPluginManager().callEvent(new InternalServerStartEvent());
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                onTick();
            }
        }.runTaskTimer(this, 0L, 1L);

        consoleLog("Minigame Engine has loaded.");
    }

    public void onTick() {
        lastUpdateTime = currentUpdateTime;
        currentUpdateTime = Time.getTime();

        Bukkit.getPluginManager().callEvent(new ServerTickEvent(getDeltaTime()));
        Bukkit.getPluginManager().callEvent(new InternalServerTickEvent(getDeltaTime()));

        ticksSinceStart ++;
    }

    @Override
    public void onDisable() {
        consoleLog("Minigame Engine has unloaded.");
    }

    public Gson getGson() {
        return gson;
    }

    public Random getRandom() {
        return random;
    }

    public void SetRandom(Random random) {
        this.random = random;
    }

    public long getMillisecondsSinceStart() {
        return System.currentTimeMillis() - startingMilliseconds;
    }

    public long getTicksSinceStart() {
        return ticksSinceStart;
    }

    public long getDeltaTime() {
        return currentUpdateTime - lastUpdateTime;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        players.put(gamePlayer.getPlayer(), gamePlayer);
    }

    public GamePlayer getGamePlayer(Player player) {
        return players.get(player);
    }

    public HashMap<Player, GamePlayer> getGamePlayers() {
        return players;
    }

    public void removeGamePlayer(Player player) {
        players.remove(player);
    }

    public void setGamePlayerFactory(GamePlayerFactory value) {
        playerFactory = value;
    }

    public GamePlayerFactory getGamePlayerFactory() {
        return playerFactory;
    }

    public World getMainWorld() {
        return Bukkit.getWorlds().get(0);
    }
}
