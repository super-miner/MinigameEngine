package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.events.external.UIClickEvent;
import me.super_miner_1.minigameengine.events.internal.InternalUIClickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.jsonData.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStackUtility implements Listener {
    public ItemStackUtility() {
        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public static boolean isBlankItem(ItemStack item) {
        return item == null || item.getType() == Material.AIR || item.getAmount() == 0;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();

        if (!(clicker instanceof Player)) {
            return;
        }

        Player player = (Player) clicker;

        Inventory clickedInventory = event.getClickedInventory();

        GameItemStack clickedGameItem = GameItemStack.getGameItemStack(clickedInventory.getItem(event.getSlot()));

        if (clickedGameItem == null) {
            return;
        }

        ArrayList<Interaction> callbacks = clickedGameItem.getCallbacks();

        ClickType clickType = event.getClick();

        for (Interaction interaction : callbacks) {
            if (interaction.isTriggered(clickType)) {
                Bukkit.getPluginManager().callEvent(new UIClickEvent(player, clickedInventory, clickedGameItem, interaction.id, null));
                Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, clickedInventory, clickedGameItem, interaction.id, null));
            }
        }

        if (!clickedGameItem.getMovable()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Inventory clickedInventory = player.getInventory();

        GameItemStack clickedGameItem = GameItemStack.getGameItemStack(event.getItem());

        if (clickedGameItem == null) {
            return;
        }

        ArrayList<Interaction> callbacks = clickedGameItem.getCallbacks();

        Action action = event.getAction();

        for (Interaction interaction : callbacks) {
            if (interaction.isTriggered(action)) {
                Bukkit.getPluginManager().callEvent(new UIClickEvent(player, clickedInventory, clickedGameItem, interaction.id, action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK ? event.getBlockFace() : null));
                Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, clickedInventory, clickedGameItem, interaction.id, action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK ? event.getBlockFace() : null));
            }
        }

        if (!clickedGameItem.getMovable()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        Inventory clickedInventory = player.getInventory();

        GameItemStack clickedGameItem = GameItemStack.getGameItemStack(event.getItemDrop().getItemStack());

        if (clickedGameItem == null) {
            return;
        }

        ArrayList<Interaction> callbacks = clickedGameItem.getCallbacks();

        for (Interaction interaction : callbacks) {
            if (interaction.isTriggered(Interaction.BinaryInteraction.DROP)) {
                Bukkit.getPluginManager().callEvent(new UIClickEvent(player, clickedInventory, clickedGameItem, interaction.id, null));
                Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, clickedInventory, clickedGameItem, interaction.id, null));
            }
        }

        if (!clickedGameItem.getMovable()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        Inventory playerInventory = player.getInventory();

        GameItemStack toGameItem = GameItemStack.getGameItemStack(playerInventory.getItem(event.getNewSlot()));
        GameItemStack fromGameItem = GameItemStack.getGameItemStack(playerInventory.getItem(event.getPreviousSlot()));

        if (toGameItem != null) {
            ArrayList<Interaction> callbacks = toGameItem.getCallbacks();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(Interaction.BinaryInteraction.SWAP_TO)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, playerInventory, toGameItem, interaction.id, null));
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, playerInventory, toGameItem, interaction.id, null));
                }
            }
        }

        if (fromGameItem != null) {
            ArrayList<Interaction> callbacks = fromGameItem.getCallbacks();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(Interaction.BinaryInteraction.SWAP_FROM)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, playerInventory, fromGameItem, interaction.id, null));
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, playerInventory, fromGameItem, interaction.id, null));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        Inventory playerInventory = player.getInventory();

        GameItemStack mainHandGameItem = GameItemStack.getGameItemStack(event.getMainHandItem());
        GameItemStack offHandGameItem = GameItemStack.getGameItemStack(event.getOffHandItem());

        if (mainHandGameItem != null) {
            ArrayList<Interaction> callbacks = mainHandGameItem.getCallbacks();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(Interaction.BinaryInteraction.SWAP_MAIN_HAND)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, playerInventory, mainHandGameItem, interaction.id, null));
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, playerInventory, mainHandGameItem, interaction.id, null));
                }
            }
        }

        if (offHandGameItem != null) {
            ArrayList<Interaction> callbacks = offHandGameItem.getCallbacks();

            for (Interaction interaction : callbacks) {
                if (interaction.isTriggered(Interaction.BinaryInteraction.SWAP_OFF_HAND)) {
                    Bukkit.getPluginManager().callEvent(new UIClickEvent(player, playerInventory, offHandGameItem, interaction.id, null));
                    Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, playerInventory, offHandGameItem, interaction.id, null));
                }
            }
        }

        if (!mainHandGameItem.getMovable() || !offHandGameItem.getMovable()) {
            event.setCancelled(true);
        }
    }
}
