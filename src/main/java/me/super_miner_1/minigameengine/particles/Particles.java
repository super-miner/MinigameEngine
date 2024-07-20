package me.super_miner_1.minigameengine.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class Particles {
    public static void line(Location start, Location end, Particle particle, double particlesPerBlock) {
        line(start, end.clone().toVector().subtract(start.toVector()), particle, particlesPerBlock);
    }

    public static void line(Location start, Vector difference, Particle particle, double particlesPerBlock) {
        Location currentLocation = start.clone();
        Vector direction = difference.normalize();

        for (int i = 0; i <= difference.length() * particlesPerBlock; i++) {
            currentLocation.getWorld().spawnParticle(particle, currentLocation, 1);

            currentLocation.add(difference.multiply(1 / particlesPerBlock));
        }
    }
}
