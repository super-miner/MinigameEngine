package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Bukkit;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;

import java.util.Dictionary;
import java.util.HashMap;

public class Interaction {
    public static HashMap<String, Integer> maskMap = new HashMap<String, Integer>() {{
        // INVENTORY
        put("INVENTORY_LEFT_CLICK", 1);
        put("INVENTORY_RIGHT_CLICK", 2);
        put("INVENTORY_MIDDLE_CLICK", 4);
        put("INVENTORY_DOUBLE_CLICK", 8);
        put("INVENTORY_SHIFT_LEFT_CLICK", 16);
        put("INVENTORY_SHIFT_RIGHT_CLICK", 32);
        put("INVENTORY_DROP_SINGLE", 64);
        put("INVENTORY_DROP_STACK", 128);
        put("INVENTORY_NUMBER_KEY", 256);
        put("INVENTORY_SWAP_OFFHAND", 512);

        // OTHER
        put("LEFT_CLICK_BLOCK", 1024);
        put("RIGHT_CLICK_BLOCK", 2048);
        put("LEFT_CLICK_AIR", 4096);
        put("RIGHT_CLICK_AIR", 8192);
        put("DROP", 16384);

        // COMBINATIONS
        put("INVENTORY_SHIFT_CLICK", 16+32);
        put("INVENTORY_LEFT_CLICK_ALL", 1+16);
        put("INVENTORY_RIGHT_CLICK_ALL", 2+32);
        put("INVENTORY_CLICK", 1+2+4+8+16+32);
        put("INVENTORY_DROP", 64+128);
        put("INVENTORY_INTERACT", 1+2+4+8+16+32+64+128+256+512);
        put("LEFT_CLICK_ITEM", 1024+4096);
        put("RIGHT_CLICK_ITEM", 2048+8192);
        put("LEFT_CLICK_ALL", 1+16+1024+4096);
        put("RIGHT_CLICK_ALL", 2+32+2048+8192);
        put("CLICK", 1+2+4+8+16+32+1024+2048+4096+8192);
        put("INTERACT", 1+2+4+8+16+32+64+128+256+512+1024+2048+4096+8192+16384);
    }};

    public String interaction;
    public String id;

    public Interaction(String interaction, String id) {
        this.interaction = interaction;
        this.id = id;
    }

    public boolean isTriggered(ClickType clickType) {
        int bitMask = maskMap.get(interaction);

        return (clickType == ClickType.LEFT         && (bitMask & maskMap.get("INVENTORY_LEFT_CLICK"))          != 0)
            || (clickType == ClickType.RIGHT        && (bitMask & maskMap.get("INVENTORY_RIGHT_CLICK"))         != 0)
            || (clickType == ClickType.MIDDLE       && (bitMask & maskMap.get("INVENTORY_MIDDLE_CLICK"))        != 0)
            || (clickType == ClickType.DOUBLE_CLICK && (bitMask & maskMap.get("INVENTORY_DOUBLE_CLICK"))        != 0)
            || (clickType == ClickType.SHIFT_LEFT   && (bitMask & maskMap.get("INVENTORY_SHIFT_LEFT_CLICK"))    != 0)
            || (clickType == ClickType.SHIFT_RIGHT  && (bitMask & maskMap.get("INVENTORY_SHIFT_RIGHT_CLICK"))   != 0)
            || (clickType == ClickType.DROP         && (bitMask & maskMap.get("INVENTORY_DROP_SINGLE"))         != 0)
            || (clickType == ClickType.CONTROL_DROP && (bitMask & maskMap.get("INVENTORY_DROP_STACK"))          != 0)
            || (clickType == ClickType.NUMBER_KEY   && (bitMask & maskMap.get("INVENTORY_NUMBER_KEY"))          != 0)
            || (clickType == ClickType.SWAP_OFFHAND && (bitMask & maskMap.get("INVENTORY_SWAP_OFFHAND"))        != 0);
    }

    public boolean isTriggered(Action action) {
        int bitMask = maskMap.get(interaction);

        return (action == Action.LEFT_CLICK_BLOCK   && (bitMask & maskMap.get("LEFT_CLICK_BLOCK"))              != 0)
            || (action == Action.RIGHT_CLICK_BLOCK  && (bitMask & maskMap.get("RIGHT_CLICK_BLOCK"))             != 0)
            || (action == Action.LEFT_CLICK_AIR     && (bitMask & maskMap.get("LEFT_CLICK_AIR"))                != 0)
            || (action == Action.RIGHT_CLICK_AIR    && (bitMask & maskMap.get("RIGHT_CLICK_AIR"))               != 0);
    }

    public boolean isTriggered(boolean droppedItem) {
        int bitMask = maskMap.get(interaction);

        return (droppedItem                         && (bitMask & maskMap.get("DROP"))                          != 0);
    }
}
