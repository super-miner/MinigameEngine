package me.super_miner_1.minigameengine.animations;

import me.super_miner_1.minigameengine.Id;
import me.super_miner_1.minigameengine.MinigameEngine;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityState extends AnimationState {
    protected Entity entity;
    protected Location location;

    public EntityState(Id id, Entity entity, Location location) {
        super(id);

        this.entity = entity;
        this.location = location;
    }

    @Override
    public void lerp(AnimationState other, float time) {
        if (!(other instanceof EntityState)) {
            MinigameEngine.engine.consoleWarn("Mismatch class in lerp between animation states!", MinigameEngine.WarnPriority.LOW);
        }

        EntityState otherEntity = (EntityState) other;

        Location otherLocation = otherEntity.getLocation().clone();
        Location currentLocation = location.clone().subtract(otherLocation).multiply(time).add(otherLocation);

        entity.teleport(currentLocation);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity value) {
        entity = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location value) {
        location = value;
    }
}
