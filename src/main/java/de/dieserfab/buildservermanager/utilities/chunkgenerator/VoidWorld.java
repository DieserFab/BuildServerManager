package de.dieserfab.buildservermanager.utilities.chunkgenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidWorld extends ChunkGenerator {

    /**
     * Used to create a blank world when using this as a generator
     */

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return createChunkData(world);
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        Location spawnLocation = new Location(world, 0.0D, 110.0D, 0.0D);
        Location blockLocation = spawnLocation.clone().subtract(0D, 100D, 0D);
        blockLocation.getBlock().setType(Material.GLASS);
        return spawnLocation;
    }

}