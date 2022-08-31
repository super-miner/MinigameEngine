package me.super_miner_1.minigameengine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.super_miner_1.minigameengine.events.ServerStartEvent;
import me.super_miner_1.minigameengine.events.ServerTickEvent;
import me.super_miner_1.minigameengine.testScripts.MainTest;
import me.super_miner_1.minigameengine.time.Time;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class MinigameEngine extends JavaPlugin {
    public static MinigameEngine engine;

    public enum WarnPriority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Gson gson = null;
    private long startingMilliseconds = 0;
    private long startingTicks = 0;
    private Time lastUpdateTime = null;
    private Time currentUpdateTime = null;
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

        this.startingMilliseconds = System.currentTimeMillis();
        this.startingTicks = Bukkit.getWorlds().get(0).getFullTime();

        this.lastUpdateTime = Time.getTime();
        this.currentUpdateTime = Time.getTime();

        this.playerFactory = new GamePlayerFactory();

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
            public void run(){
                Bukkit.getPluginManager().callEvent(new ServerStartEvent());
            }
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                onTick();
            }
        }.runTaskTimer(this, 0L, 1L);

        //==========TEST=CODE==========//

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(new MainTest(), this);

        //=============================//

        consoleLog("Minigame Engine has loaded.");
    }

    public void onTick() {
        lastUpdateTime = currentUpdateTime;
        currentUpdateTime = Time.getTime();

        Bukkit.getPluginManager().callEvent(new ServerTickEvent(getDeltaTime()));
    }

    @Override
    public void onDisable() {
        consoleLog("Minigame Engine has unloaded.");
    }

    public Gson getGson() {
        return gson;
    }

    public long getMillisecondsSinceStart() {
        return System.currentTimeMillis() - startingMilliseconds;
    }

    public long getTicksSinceStart() {
        return Bukkit.getWorlds().get(0).getFullTime() - startingTicks;
    }

    public Time getDeltaTime() {
        return currentUpdateTime.subtract(lastUpdateTime);
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
}
