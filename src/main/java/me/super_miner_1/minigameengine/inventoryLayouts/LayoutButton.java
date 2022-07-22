package me.super_miner_1.minigameengine.inventoryLayouts;

import me.super_miner_1.minigameengine.GameItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LayoutButton extends LayoutItem implements Listener {
    public static class InputType {
        public static final int INVENTORY_CONTROL_DROP = 1;
        public static final int INVENTORY_NORMAL_DROP = 2;
        public static final int INVENTORY_DOUBLE_CLICK = 4;
        public static final int INVENTORY_NORMAL_LEFT_CLICK = 8;
        public static final int INVENTORY_NORMAL_MIDDLE_CLICK = 16;
        public static final int INVENTORY_NORMAL_RIGHT_CLICK = 32;
        public static final int INVENTORY_NUMBER_KEY = 64;
        public static final int INVENTORY_SHIFT_LEFT = 128;
        public static final int INVENTORY_SHIFT_RIGHT = 256;
        public static final int INVENTORY_SWAP_OFFHAND = 512;
        public static final int LEFT_CLICK_BLOCK = 1024;
        public static final int LEFT_CLICK_AIR = 2048;
        public static final int RIGHT_CLICK_BLOCK = 4096;
        public static final int RIGHT_CLICK_AIR = 8192;
        public static final int DROP = 16384;
        public static final int INVENTORY_DROP = INVENTORY_CONTROL_DROP | INVENTORY_NORMAL_DROP;
        public static final int INVENTORY_LEFT_CLICK = INVENTORY_NORMAL_LEFT_CLICK | INVENTORY_SHIFT_LEFT;
        public static final int INVENTORY_RIGHT_CLICK = INVENTORY_NORMAL_RIGHT_CLICK | INVENTORY_SHIFT_RIGHT;
        public static final int INVENTORY_CLICK = INVENTORY_LEFT_CLICK | INVENTORY_RIGHT_CLICK | INVENTORY_NORMAL_MIDDLE_CLICK;
        public static final int LEFT_CLICK = LEFT_CLICK_AIR | LEFT_CLICK_BLOCK;
        public static final int RIGHT_CLICK = RIGHT_CLICK_AIR | RIGHT_CLICK_BLOCK;
        public static final int CLICK = LEFT_CLICK | RIGHT_CLICK;
        public static final int ALL = INVENTORY_DOUBLE_CLICK | INVENTORY_NUMBER_KEY | INVENTORY_SWAP_OFFHAND | DROP | INVENTORY_DROP | INVENTORY_CLICK | CLICK;
    }

    private int inputType = 0;
    private ButtonCallback callback;

    public LayoutButton(GameItemStack item, int inputType, ButtonCallback callback) {
        super(item);

        this.inputType = inputType;
        this.callback = callback;
    }

    public LayoutButton(ItemStack item, int inputType, ButtonCallback callback) {
        super(item);

        this.inputType = inputType;
        this.callback = callback;
    }

    public void clicked(HumanEntity clicker) {
        callback.run(clicker);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        ClickType clickType = event.getClick();
        HumanEntity entity = event.getWhoClicked();

        switch (clickType) {
            case CONTROL_DROP:
                if ((inputType & InputType.INVENTORY_CONTROL_DROP) != 0) {
                    clicked(entity);
                }
                break;
            case DROP:
                if ((inputType & InputType.INVENTORY_NORMAL_DROP) != 0) {
                    clicked(entity);
                }
                break;
            case DOUBLE_CLICK:
                if ((inputType & InputType.INVENTORY_DOUBLE_CLICK) != 0) {
                    clicked(entity);
                }
                break;
            case LEFT:
                if ((inputType & InputType.INVENTORY_NORMAL_LEFT_CLICK) != 0) {
                    clicked(entity);
                }
                break;
            case MIDDLE:
                if ((inputType & InputType.INVENTORY_NORMAL_MIDDLE_CLICK) != 0) {
                    clicked(entity);
                }
                break;
            case RIGHT:
                if ((inputType & InputType.INVENTORY_RIGHT_CLICK) != 0) {
                    clicked(entity);
                }
                break;
            case NUMBER_KEY:
                if ((inputType & InputType.INVENTORY_NUMBER_KEY) != 0) {
                    clicked(entity);
                }
                break;
            case SHIFT_LEFT:
                if ((inputType & InputType.INVENTORY_SHIFT_LEFT) != 0) {
                    clicked(entity);
                }
                break;
            case SHIFT_RIGHT:
                if ((inputType & InputType.INVENTORY_SHIFT_RIGHT) != 0) {
                    clicked(entity);
                }
                break;
            case SWAP_OFFHAND:
                if ((inputType & InputType.INVENTORY_SWAP_OFFHAND) != 0) {
                    clicked(entity);
                }
                break;
        }
    }

    @Override
    public void onClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        HumanEntity entity = event.getPlayer();

        switch (action) {
            case LEFT_CLICK_BLOCK:
                if ((inputType & InputType.LEFT_CLICK_BLOCK) != 0) {
                    clicked(entity);
                }
                break;
            case LEFT_CLICK_AIR:
                if ((inputType & InputType.LEFT_CLICK_AIR) != 0) {
                    clicked(entity);
                }
                break;
            case RIGHT_CLICK_BLOCK:
                if ((inputType & InputType.RIGHT_CLICK_BLOCK) != 0) {
                    clicked(entity);
                }
                break;
            case RIGHT_CLICK_AIR:
                if ((inputType & InputType.RIGHT_CLICK_AIR) != 0) {
                    clicked(entity);
                }
                break;
        }
    }

    @Override
    public void onDrop(PlayerDropItemEvent event) {
        HumanEntity entity = event.getPlayer();

        if ((inputType & InputType.DROP) != 0) {
            clicked(entity);
        }
    }
}
