package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;

public class Summoner {
    public static void summonHorde(Wave wave, World world, BlockPos center) {
        ArrayList<BlockPos> spawnLocations = findSpawnLocations(world, center, wave.manifest(world).size());
        Collections.shuffle(spawnLocations);
        for(Entity entity : wave.manifest(world)){
            BlockPos spawnLocation = spawnLocations.get(0);
            Collections.shuffle(spawnLocations);
            entity.refreshPositionAndAngles(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), 0, 0);
            world.spawnEntity(entity);
        }
    }

    // Gets a list of viable spawn locations for a horde.
    private static ArrayList<BlockPos> findSpawnLocations(World world, BlockPos center, int positions) {
        int yLevel = world.getTopY(Heightmap.Type.WORLD_SURFACE, center.getX(), center.getZ());
        ArrayList<BlockPos> spawnLocations = new ArrayList<>();
        //spawnLocations.add(center); <-- adding center causes hordes to spawn in blocks if that is the specified location
        for (int i = 0; i < positions; i++) {
            BlockPos spawnLocation = new BlockPos(center.getX() + (int) (Math.random() * 10) - 5, yLevel + 1, center.getZ() + (int) (Math.random() * 10) - 5);
            if (world.getBlockState(spawnLocation).isAir()) {
                spawnLocations.add(spawnLocation);
            }
        }
        return spawnLocations;
    }
}
