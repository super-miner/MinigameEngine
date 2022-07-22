package me.super_miner_1.minigameengine.testScripts;

import me.super_miner_1.minigameengine.GameItemStack;
import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.events.ServerStartEvent;
import me.super_miner_1.minigameengine.events.ServerTickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.InventoryLayout;
import me.super_miner_1.minigameengine.inventoryLayouts.LayoutButton;
import me.super_miner_1.minigameengine.inventoryLayouts.LayoutItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MainTest implements Listener {
    public InventoryLayout testLayout;

    @EventHandler
    public void onServerStart(ServerStartEvent event) {
        MinigameEngine.consoleLog("Server Start Event");

        testLayout = new InventoryLayout(6)
                .fill(
                        new LayoutItem(
                                new GameItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
                                        .setDisplayName(ChatColor.WHITE + "")))
                .setItem(4, 2, new LayoutButton(
                        new GameItemStack(Material.GREEN_STAINED_GLASS_PANE, 1)
                            .setDisplayName("Click Me"),
                        LayoutButton.InputType.INVENTORY_CLICK, (clicker) -> {
                            clicker.sendMessage("You clicked me!");
                        }));
    }

    @EventHandler
    public void onServerTick(ServerTickEvent event) {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        testLayout.openInventory(event.getPlayer());
    }
}
