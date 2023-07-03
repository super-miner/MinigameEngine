package me.super_miner_1.minigameengine.inventoryLayouts;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.NBTAPI;
import me.super_miner_1.minigameengine.MinigameEngine;
import me.super_miner_1.minigameengine.events.external.UIClickEvent;
import me.super_miner_1.minigameengine.events.internal.InternalUIClickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.jsonData.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStackUtility implements Listener {
    public ItemStackUtility() {
        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public static boolean getMovable(ItemStack item) {
        NBTItem itemNBT = new NBTItem(item);

        return itemNBT.getBoolean("Movable");
    }

    public static ItemStack setMovable(ItemStack item, boolean movable) {
        NBTItem itemNBT = new NBTItem(item);

        itemNBT.setBoolean("Movable", movable);

        return itemNBT.getItem();
    }

    public static ArrayList<Interaction> getCallbacks(ItemStack item) {
        NBTItem itemNBT = new NBTItem(item);
        NBTCompoundList callbacksNBT = itemNBT.getCompoundList("Callbacks");

        ArrayList<Interaction> callbacks = new ArrayList<Interaction>();

        for (int i = 0; i < callbacksNBT.size(); i++) {
            NBTCompound callback = callbacksNBT.get(i);

            callbacks.add(new Interaction(callback.getString("Interaction"), callback.getString("Id")));
        }

        return callbacks;
    }

    public static ItemStack setCallbacks(ItemStack item, ArrayList<Interaction> callbacks) {
        NBTItem itemNBT = new NBTItem(item);
        NBTCompoundList callbacksNBT = itemNBT.getCompoundList("Callbacks");
        callbacksNBT.clear();

        for (Interaction callback : callbacks) {
            NBTCompound callbackNBT = new NBTContainer();

            callbackNBT.setString("Interaction", callback.interaction);
            callbackNBT.setString("Id", callback.id);

            callbacksNBT.addCompound(callbackNBT);
        }

        return itemNBT.getItem();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();

        if (!(clicker instanceof Player)) {
            return;
        }

        Player player = (Player) clicker;

        Inventory clickedInventory = event.getClickedInventory();

        ItemStack clickedItem = clickedInventory.getItem(event.getSlot());

        if (clickedItem == null) {
            return;
        }

        ArrayList<Interaction> callbacks = getCallbacks(clickedItem);

        ClickType clickType = event.getClick();

        for (Interaction interaction : callbacks) {
            if (interaction.isTriggered(clickType)) {
                Bukkit.getPluginManager().callEvent(new UIClickEvent(player, clickedInventory, clickedItem, interaction.id));
                Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, clickedInventory, clickedItem, interaction.id));
            }
        }

        if (!getMovable(clickedItem)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Inventory clickedInventory = player.getInventory();

        ItemStack clickedItem = event.getItem();

        if (clickedItem == null) {
            return;
        }

        ArrayList<Interaction> callbacks = getCallbacks(clickedItem);

        Action action = event.getAction();

        for (Interaction interaction : callbacks) {
            if (interaction.isTriggered(action)) {
                Bukkit.getPluginManager().callEvent(new UIClickEvent(player, clickedInventory, clickedItem, interaction.id));
                Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, clickedInventory, clickedItem, interaction.id));
            }
        }

        if (!getMovable(clickedItem)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        Inventory clickedInventory = player.getInventory();

        ItemStack clickedItem = event.getItemDrop().getItemStack();

        ArrayList<Interaction> callbacks = getCallbacks(clickedItem);

        for (Interaction interaction : callbacks) {
            if (interaction.isTriggered(true)) {
                Bukkit.getPluginManager().callEvent(new UIClickEvent(player, clickedInventory, clickedItem, interaction.id));
                Bukkit.getPluginManager().callEvent(new InternalUIClickEvent(player, clickedInventory, clickedItem, interaction.id));
            }
        }

        if (!getMovable(clickedItem)) {
            event.setCancelled(true);
        }
    }
}
