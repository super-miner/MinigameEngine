package me.super_miner_1.minigameengine;

import me.super_miner_1.minigameengine.effects.EffectCompoundFunction;
import me.super_miner_1.minigameengine.effects.EffectCompoundPresets;
import me.super_miner_1.minigameengine.effects.GameEffect;
import me.super_miner_1.minigameengine.effects.GameEffectGroup;
import me.super_miner_1.minigameengine.events.internal.InternalPotionEffectExpireEvent;
import me.super_miner_1.minigameengine.events.internal.InternalServerTickEvent;
import me.super_miner_1.minigameengine.inventoryLayouts.GameInventory;
import me.super_miner_1.minigameengine.inventoryLayouts.GameItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePlayer implements Listener {
    private static Class<? extends GamePlayer> overrideClass = GamePlayer.class;
    private static ArrayList<? extends GamePlayer> gamePlayers = new ArrayList<GamePlayer>();

    protected Player player;
    protected ArrayList<GameEffectGroup> effectGroups = new ArrayList<GameEffectGroup>();
    protected Entity morph = null;
    protected HashMap<Integer, GameItemStack> morphInventoryTemp = null;

    public GamePlayer(Player player) {
        super();

        this.player = player;
        MinigameEngine.engine.getServer().getPluginManager().registerEvents(this, MinigameEngine.engine);
    }

    public static void setOverrideClass(Class<? extends GamePlayer> value) {
        overrideClass = value;
    }

    public static Class<? extends GamePlayer> getOverrideClass() {
        return overrideClass;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity morph(EntityType entityType) {
        if (entityType == null) {
            unmorph();
            return null;
        }

        morph = player.getWorld().spawnEntity(player.getLocation(), entityType);
        morph.setGravity(false);
        morph.setInvulnerable(true);
        morph.setSilent(true);
        morph.setPersistent(true);

        if (morph instanceof LivingEntity) {
            LivingEntity livingEntityMorph = (LivingEntity) morph;

            livingEntityMorph.setAI(false);
            livingEntityMorph.setCollidable(false);
            livingEntityMorph.setRemoveWhenFarAway(false);
        }

        addEffect(new Id("MORPH_INVISIBILITY"), PotionEffectType.INVISIBILITY, -1, 1.0, EffectCompoundPresets.MIN, -1, false, false, false); // TODO: This isn't working.

        if (morphInventoryTemp == null) {
            morphInventoryTemp = getInventoryContents();
            new GameInventory(player.getInventory()).clear();
        }

        return morph;
    }

    public void unmorph() {
        morph.remove();
        morph = null;

        removeEffect(new Id("MORPH_INVISIBILITY"), PotionEffectType.INVISIBILITY);

        if (morphInventoryTemp != null) {
            applyInventoryContents(morphInventoryTemp);
            morphInventoryTemp = null;
        }
    }

    public boolean isMorphed() {
        return morph != null;
    }

    public GameEffectGroup addEffectGroup(Id id, PotionEffectType type) {
        if (getEffectGroup(id) != null) {
            return null;
        }

        GameEffectGroup effectGroup = new GameEffectGroup(id, type);
        effectGroups.add(effectGroup);
        return effectGroup;
    }

    public GameEffectGroup addEffectGroup(PotionEffectType type) {
        return addEffectGroup(new Id(type.getName()), type);
    }

    public GameEffectGroup addEffectGroup(Id id) {
        return addEffectGroup(id, null);
    }

    public GameEffectGroup getEffectGroup(Id id) {
        for (GameEffectGroup effectGroup : effectGroups) {
            if (id.equals(effectGroup.getId())) {
                return effectGroup;
            }
        }
        return null;
    }

    public GameEffectGroup getEffectGroup(PotionEffectType type) {
        return getEffectGroup(new Id(type.getName()));
    }

    public void removeEffectGroup(Id id) {
        effectGroups.remove(getEffectGroup(id));
    }

    public void removeEffectGroup(PotionEffectType type) {
        removeEffectGroup(new Id(type.getName()));
    }

    public GameEffect addEffect(Id id, Id groupId, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority, boolean ambient, boolean particles, boolean icon) {
        GameEffectGroup group = getEffectGroup(groupId);

        if (group == null) {
            group = addEffectGroup(groupId, type);
        }

        if (group.getEffect(id) != null) {
            return null;
        }

        GameEffect effect = new GameEffect(this, id, group, (duration < 0 && group.getType() != null ? 999999 : duration), value, effectCompoundFunction, priority, null, ambient, particles, icon);

        group.addEffect(effect);
        return effect;
    }

    public GameEffect addEffect(Id id, Id groupId, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority, boolean ambient, boolean particles, boolean icon) {
        return addEffect(id, groupId, null, duration, value, effectCompoundFunction, priority, ambient, particles, icon);
    }

    public GameEffect addEffect(Id id, Id groupId, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority) {
        return addEffect(id, groupId, null, duration, value, effectCompoundFunction, priority, false, true, true);
    }

    public GameEffect addEffect(Id id, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority, boolean ambient, boolean particles, boolean icon) {
        return addEffect(id, new Id(type.getName()), type, duration, value, effectCompoundFunction, priority, ambient, particles, icon);
    }

    public GameEffect addEffect(Id id, PotionEffectType type, int duration, double value, EffectCompoundFunction effectCompoundFunction, int priority) {
        return addEffect(id, new Id(type.getName()), type, duration, value, effectCompoundFunction, priority, false, true, true);
    }

    public void removeEffect(Id id, Id groupId) {
        GameEffectGroup group = getEffectGroup(groupId);

        if (group == null) {
            return;
        }

        group.removeEffect(id);
    }

    public void removeEffect(Id id, PotionEffectType type) {
        removeEffect(id, new Id(type.getName()));
    }

    public HashMap<Integer, GameItemStack> getInventoryContents() {
        PlayerInventory inventory = player.getInventory();

        HashMap<Integer, GameItemStack> contents = new HashMap<Integer, GameItemStack>();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                contents.put(i, GameItemStack.getGameItemStack(item));
            }
        }

        ItemStack helmet = inventory.getItem(EquipmentSlot.HEAD);
        if (helmet != null) {
            contents.put(-1, GameItemStack.getGameItemStack(helmet));
        }

        ItemStack chestplate = inventory.getItem(EquipmentSlot.CHEST);
        if (chestplate != null) {
            contents.put(-2, GameItemStack.getGameItemStack(chestplate));
        }

        ItemStack leggings = inventory.getItem(EquipmentSlot.LEGS);
        if (leggings != null) {
            contents.put(-3, GameItemStack.getGameItemStack(leggings));
        }

        ItemStack boots = inventory.getItem(EquipmentSlot.FEET);
        if (boots != null) {
            contents.put(-4, GameItemStack.getGameItemStack(boots));
        }

        ItemStack offhand = inventory.getItem(EquipmentSlot.OFF_HAND);
        if (offhand != null) {
            contents.put(-5, GameItemStack.getGameItemStack(offhand));
        }

        return contents;
    }

    public void applyInventoryContents(HashMap<Integer, GameItemStack> contents) {
        PlayerInventory playerInventory = player.getInventory();
        GameInventory gameInventory = new GameInventory(playerInventory);

        for (int slot : contents.keySet()) {
            if (slot >= 0) {
                GameItemStack gameItem = contents.get(slot);
                gameInventory.setItem(slot, gameItem);
            }
        }

        gameInventory.setItem(39, contents.getOrDefault(-1, null));
        gameInventory.setItem(38, contents.getOrDefault(-2, null));
        gameInventory.setItem(37, contents.getOrDefault(-3, null));
        gameInventory.setItem(36, contents.getOrDefault(-4, null));
        gameInventory.setItem(40, contents.getOrDefault(-5, null));
    }

    @EventHandler
    public void onTick(InternalServerTickEvent event) {
        player.updateInventory(); // Needed to fix ghost item glitch when opening a menu from your inventory.

        for (GameEffectGroup effectGroup : effectGroups) {
            effectGroup.calculateEffects(this);
        }

        if (morph != null) {
            morph.teleport(player.getLocation());
        }
    }

    @EventHandler
    public void potionEffectExpireEvent(InternalPotionEffectExpireEvent event) {
        if (event.getPlayer() == player) {
            removeEffect(event.getEffect().getId(), event.getEffect().getGroup().getId());
        }
    }
}
