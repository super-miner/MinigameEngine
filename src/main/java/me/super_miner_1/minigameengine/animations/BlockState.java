package me.super_miner_1.minigameengine.animations;

import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockState extends AnimationState {
    protected Block block;
    protected Material blockType;

    public BlockState(Id id, Block block, Material blockType) {
        super(id);

        this.block = block;
        this.blockType = blockType;
    }

    @Override
    public void lerp(AnimationState other, float time) {
        if (time >= 1) {
            block.setType(blockType);
        }
    }

    @Override
    public boolean compatibleWith(AnimationState other) {
        if (!(other instanceof BlockState)) {
            return false;
        }

        BlockState otherBlockState = (BlockState) other;

        return otherBlockState.getBlock().getLocation().equals(block.getLocation());
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block value) {
        block = value;
    }

    public Material getBlockType() {
        return blockType;
    }

    public void setBlockType(Material value) {
        blockType = value;
    }
}
