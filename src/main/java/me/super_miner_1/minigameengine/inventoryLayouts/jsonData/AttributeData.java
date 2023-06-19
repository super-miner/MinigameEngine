package me.super_miner_1.minigameengine.inventoryLayouts.jsonData;

import org.bukkit.attribute.Attribute;

public class AttributeData {
    public String attribute;
    public int baseLevel;

    public Attribute getAttribute() {
        return Attribute.valueOf(attribute);
    }
}
