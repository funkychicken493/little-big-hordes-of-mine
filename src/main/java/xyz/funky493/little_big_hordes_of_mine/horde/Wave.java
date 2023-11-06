package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Wave {
    // Map of participant entity types and their quantity to NBT data
    private Map<String, NbtCompound> participants;
    private final String id;
    public Wave(String id) {
        this.id = id;
    }
    public Wave(String id, Map<String, NbtCompound> participants) {
        this.id = id;
        this.participants = participants;
    }

    public static Wave loadWaveFromConfig(@NotNull String id) {
        try {
            Map<String, NbtCompound> configParticipants = LittleBigHordesOfMine.config.waves.get(id);
        } catch (Exception e) {
            LittleBigHordesOfMine.LOGGER.error("Error loading wave " + id + " (does it exist?): " + e);
            return null;
        }
        return new Wave(id, LittleBigHordesOfMine.config.waves.get(id));
    }

    private ArrayList<Entity> participantToEntities(String participantTypeAmount, NbtCompound nbt, World world) {
        // Example format: 1-10x minecraft:zombie
        // Example format: 5x minecraft:zombie_villager
        ArrayList<Entity> entities = new ArrayList<>();
        String[] participantParts = participantTypeAmount.split("x ", 2);
        int amount;
        if(participantParts[0].contains("-")) {
            int min = Integer.parseInt(participantParts[0].split("-")[0]);
            int max = Integer.parseInt(participantParts[0].split("-")[1]);
            amount = new Random().nextInt(max - min) + min;
        } else amount = Integer.parseInt(participantParts[0]);
        Identifier entityType = new Identifier(participantParts[1].split(":")[0], participantParts[1].split(":")[1]);
        for (int i = 0; i < amount; i++) {
            entities.add(Registry.ENTITY_TYPE.get(entityType).create(world));
            entities.get(i).writeNbt(nbt);
        }
        return entities;
    }

    public ArrayList<Entity> manifest(World world) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (Map.Entry<String, NbtCompound> entry : participants.entrySet()) {
            entities.addAll(participantToEntities(entry.getKey(), entry.getValue(), world));
        }
        return entities;
    }
}
