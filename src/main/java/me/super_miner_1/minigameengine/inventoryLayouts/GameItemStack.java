package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.testScripts.MainTest;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GameItemStack implements Listener {
    private ItemStack item;
    private boolean movable = true;
    private ArrayList<Interaction> callbacks = new ArrayList<Interaction>();

    public GameItemStack(ItemStack item) {
        this.item = item;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public GameItemStack(ItemStack item, boolean movable, ArrayList<Interaction> callbacks) {
        this.item = item;
        this.movable = movable;
        this.callbacks = callbacks;

        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public ItemStack getItemStack() {
        return item;
    }

    public boolean isMovable() {
        return movable;
    }

    public ArrayList<Interaction> getCallbackIds() {
        return callbacks;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();

        if (!(clicker instanceof Player)) {
            return;
        }

        Player player = (Player) clicker;

        ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());

        if (clickedItem == null) {
            return;
        }

        if (clickedItem.equals(item)) {
            ClickType clickType = event.getClick();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(clickType)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, this, interaction.id));
                }
            }
        }
    }
}
