package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.Time;
import org.bukkit.entity.Player;

public class ExperienceBarCooldown extends Cooldown {
    public ExperienceBarCooldown(int id, Player player, long length) {
        super(id, player, length);
    }
    public ExperienceBarCooldown(String id, Player player, long length) {
        super(id, player, length);
    }

    @Override
    public void run() {
        if (!active) {
            return;
        }

        if (getTimeLeft() > 0) {
            player.setLevel(Math.round((float) getTimeLeft() / 20));
            long timeUntil = length - getTimeLeft();
            player.setExp((float) timeUntil / length);
        }
        else {
            player.setLevel(0);
            player.setExp(0.999f);
        }
    }

    @Override
    public void cancel() {
        player.setLevel(0);
        player.setExp(0.999f);
    }
}
