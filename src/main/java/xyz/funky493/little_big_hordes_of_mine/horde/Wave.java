package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.visitor.StringNbtWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;

import java.util.*;

public class Wave {
    // Map of participant entity types and their quantity to NBT data
    public Map<String, NbtCompound> participants;
    private final String id;
    public Wave(String id) {
        this.id = id;
    }
    public Wave(String id, Map<String, String> stringParticipants) {
        this.id = id;
        this.participants = new java.util.HashMap<>();
        // If the wave contains the "base waves" key, copy the waves specified and add them to the participants
        if(stringParticipants.containsKey("base waves")) {
            for(String baseWave : stringParticipants.get("base waves").split(", ")) {
                Wave baseWaveObj = Wave.loadWaveFromConfig(baseWave);
                if(baseWaveObj == null) {
                    LittleBigHordesOfMine.LOGGER.error("Error loading base wave " + baseWave + " for wave " + id);
                    continue;
                }
                this.participants.putAll(baseWaveObj.participants);
            }

        }
        // We need to convert the string of the nbt into NbtCompound objects
        Set<Map.Entry<String, String>> entrySet = stringParticipants.entrySet();
        entrySet.remove(entrySet.stream().filter(entry -> entry.getKey().equals("base waves")).findFirst().orElse(null));
        for (Map.Entry<String, String> entry : entrySet) {
            NbtCompound nbt = new NbtCompound();
            // If the nbt is empty, just set it to an empty NbtCompound
            if(entry.getValue().isEmpty()) {
                participants.put(entry.getKey(), nbt);
                continue;
            }
            // Otherwise, parse the nbt string into an NbtCompound
            try {
                nbt = StringNbtReader.parse(entry.getValue());
            } catch (Exception e) {
                LittleBigHordesOfMine.LOGGER.error("Error parsing nbt for entity " + entry + " for wave " + id + ": " + e);
            }
            participants.put(entry.getKey(), nbt);
        }
    }

    public static Wave loadWaveFromConfig(@NotNull String id) {
        Map<String, String> configParticipants;
        try {
            configParticipants = LittleBigHordesOfMine.config.waves.get(id);
        } catch (Exception e) {
            LittleBigHordesOfMine.LOGGER.error("Error loading wave " + id + " (does it exist?): " + e);
            return null;
        }
        return new Wave(id, configParticipants);
    }

    private ArrayList<Entity> participantToEntities(String participantTypeAmount, NbtCompound nbt, World world) {
        // Example format: 1-10x minecraft:zombie
        // Example format: 5x minecraft:zombie_villager
        ArrayList<Entity> entities = new ArrayList<>();
        String[] participantParts = participantTypeAmount.split("x ", 2);
        int amount;
        // If the amount is a range, generate a random number between the min and max separated by a hyphen
        if(participantParts[0].contains("-")) {
            int min = Integer.parseInt(participantParts[0].split("-")[0]);
            int max = Integer.parseInt(participantParts[0].split("-")[1]);
            amount = new Random().nextInt(max - min) + min;
        } else { // Otherwise, just parse the amount as an integer
            amount = Integer.parseInt(participantParts[0]);
        }
        // Pull the entity type from the participant string
        Identifier entityType = new Identifier(participantParts[1].split(":")[0], participantParts[1].split(":")[1]);
        for (int i = 0; i < amount; i++) {
            Entity entity = Registry.ENTITY_TYPE.get(entityType).create(world);
            if(entity == null) {
                LittleBigHordesOfMine.LOGGER.error("Error creating entity " + entityType.toString() + " (does it exist?)");
                continue;
            }
            entity.readNbt(nbt);
            entities.add(entity);
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
