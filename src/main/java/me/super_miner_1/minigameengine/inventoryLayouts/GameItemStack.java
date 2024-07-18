package me.super_miner_1.minigameengine.inventoryLayouts;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import me.super_miner_1.minigameengine.inventoryLayouts.jsonData.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameItemStack {
    protected static HashMap<UUID, GameItemStack> gameItems = new HashMap<UUID, GameItemStack>();
    protected ItemStack item;
    protected ArrayList<ItemStackLocation> itemLocations = new ArrayList<ItemStackLocation>();

    public GameItemStack(ItemStack item) {
        this.item = item;

        if (!hasGameUUID()) {
            setGameUUID(UUID.randomUUID());
        }
    }

    public static GameItemStack getGameItemStack(ItemStack item) {
        if (ItemStackUtility.isBlankItem(item)) {
            return null;
        }

        GameItemStack gameItem = new GameItemStack(item);
        UUID itemGameUUID = gameItem.getGameUUID();

        if (gameItems.containsKey(itemGameUUID)) {
            return gameItems.get(itemGameUUID);
        }
        else {
            gameItems.put(itemGameUUID, gameItem);
            return gameItem;
        }
    }

    public ItemStack getItemStack() {
        return item;
    }

    public void setItemStack(ItemStack item) {
        this.item = item;

        updateItemStacks();
    }

    public GameItemStack clone() {
        return new GameItemStack(item);
    }

    public boolean getMovable() {
        NBTItem itemNBT = new NBTItem(item);

        return itemNBT.getBoolean("Movable");
    }

    public void setMovable(boolean movable) {
        NBTItem itemNBT = new NBTItem(item);

        itemNBT.setBoolean("Movable", movable);

        item = itemNBT.getItem();

        updateItemStacks();
    }

    public ArrayList<Interaction> getCallbacks() {
        NBTItem itemNBT = new NBTItem(item);
        NBTCompoundList callbacksNBT = itemNBT.getCompoundList("Callbacks");

        ArrayList<Interaction> callbacks = new ArrayList<Interaction>();

        for (int i = 0; i < callbacksNBT.size(); i++) {
            NBTCompound callback = callbacksNBT.get(i);

            callbacks.add(new Interaction(callback.getString("Interaction"), callback.getString("Id")));
        }

        return callbacks;
    }

    public void setCallbacks(ArrayList<Interaction> callbacks) {
        NBTItem itemNBT = new NBTItem(item);
        NBTCompoundList callbacksNBT = itemNBT.getCompoundList("Callbacks");
        callbacksNBT.clear();

        for (Interaction callback : callbacks) {
            NBTCompound callbackNBT = new NBTContainer();

            callbackNBT.setString("Interaction", callback.interaction);
            callbackNBT.setString("Id", callback.id);

            callbacksNBT.addCompound(callbackNBT);
        }

        item = itemNBT.getItem();

        updateItemStacks();
    }

    public UUID getUUID() {
        NBTItem itemNBT = new NBTItem(item);

        return itemNBT.getUUID("Identifier");
    }

    public void setUUID(UUID uuid) {
        NBTItem itemNBT = new NBTItem(item);

        itemNBT.setUUID("Identifier", uuid);

        item = itemNBT.getItem();

        updateItemStacks();
    }

    public boolean hasGameUUID() {
        NBTItem itemNBT = new NBTItem(item);

        return itemNBT.hasTag("GameUUID");
    }

    public UUID getGameUUID() {
        NBTItem itemNBT = new NBTItem(item);

        return itemNBT.getUUID("GameUUID");
    }

    public void setGameUUID(UUID uuid) {
        NBTItem itemNBT = new NBTItem(item);

        itemNBT.setUUID("GameUUID", uuid);

        item = itemNBT.getItem();

        updateItemStacks();
    }

    public void updateItemStacks() {
        for (ItemStackLocation itemLocation : itemLocations) {
            itemLocation.set(item);
        }
    }

    public void addItemLocation(int slot, Inventory inventory) {
        itemLocations.add(new ItemStackLocation(slot, inventory));

        inventory.setItem(slot, item);
    }

    public void removeItemLocation(int slot, Inventory inventory) {
        ItemStackLocation oldItemLocation = new ItemStackLocation(slot, inventory);

        //Bukkit.broadcastMessage("Removing item from slot " + slot);

        for (int i = 0; i < itemLocations.size(); i++) {
            ItemStackLocation itemLocation = itemLocations.get(i);

            if (itemLocation.equals(oldItemLocation)) {
                itemLocations.remove(i);

                break;
            }
        }

        inventory.setItem(slot, null);
    }
}
