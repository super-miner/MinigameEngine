package me.super_miner_1.minigameengine;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.InvocationTargetException;

public class GamePlayerFactory implements Listener {
    protected Class<?>[] playerConstructorArgs;

    public GamePlayerFactory() {
        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);

        playerConstructorArgs = new Class[1];
        playerConstructorArgs[0] = Player.class;
    }

    public void onJoin(PlayerJoinEvent event) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MinigameEngine.engine.addGamePlayer(GamePlayer.getOverrideClass().getDeclaredConstructor(Player.class).newInstance(event.getPlayer()));
    }

    public void onLeave(PlayerQuitEvent event) {
        MinigameEngine.engine.removeGamePlayer(event.getPlayer());
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        try {
            onJoin(event);
        }
        catch (NoSuchMethodException err) {
            err.printStackTrace();
        }
        catch (InvocationTargetException err) {
            err.printStackTrace();
        }
        catch (InstantiationException err) {
            err.printStackTrace();
        }
        catch (IllegalAccessException err) {
            err.printStackTrace();
        }
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        onLeave(event);
    }
}
