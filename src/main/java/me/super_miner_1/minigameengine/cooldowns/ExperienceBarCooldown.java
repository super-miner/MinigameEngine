package me.super_miner_1.minigameengine.cooldowns;

import me.super_miner_1.minigameengine.time.Time;
import org.bukkit.entity.Player;

public class ExperienceBarCooldown extends Cooldown {
    public ExperienceBarCooldown(int id, Player player, Time length) {
        super(id, player, length);
    }
    public ExperienceBarCooldown(String id, Player player, Time length) {
        super(id, player, length);
    }

    @Override
    public void run() {
        if (!active) {
            return;
        }

        if (getTimeLeft().getTimeTicks() > 0) {
            player.setLevel(Math.round((float) getTimeLeft().getTimeMilliseconds() / 1000));
            Time timeUntil = length.subtract(getTimeLeft());
            player.setExp((float) timeUntil.getTimeMilliseconds() / length.getTimeMilliseconds());
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
