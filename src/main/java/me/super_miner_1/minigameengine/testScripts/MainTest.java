package me.super_miner_1.minigameengine.testScripts;

import me.super_miner_1.minigameengine.events.ServerStartEvent;
import me.super_miner_1.minigameengine.events.ServerTickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.InventoryLayout;
import me.super_miner_1.minigameengine.inventoryLayouts.UIClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class MainTest implements Listener {
    public Inventory testInventory;

    @EventHandler
    public void onServerStart(ServerStartEvent event) {
        /*testLayout = new InventoryLayout(6, "Test GUI")
                .fill(
                        new LayoutItem(
                                new GameItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
                                        .setDisplayName(ChatColor.WHITE + "")))
                .setItem(4, 2, new LayoutButton(
                        new GameItemStack(Material.GREEN_STAINED_GLASS_PANE, 1)
                            .setDisplayName("Click Me"),
                        LayoutButton.InputType.INVENTORY_CLICK, (clicker) -> {
                            clicker.sendMessage("You clicked me!");
                        }));*/

        testInventory = InventoryLayout.getInventory("TestMenu.json");
    }

    @EventHandler
    public void onServerTick(ServerTickEvent event) {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().openInventory(testInventory);
    }

    @EventHandler
    public void onUIClick(UIClickEvent event) {
        Bukkit.broadcastMessage(event.getCallbackId());
    }
}
