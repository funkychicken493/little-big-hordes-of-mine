package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wave {
    private final ArrayList<String> participants;
    private final String id;
    public Wave(String id, ArrayList<String> participants) {
        this.id = id;
        this.participants = participants;
    }

    public static Wave loadWaveFromString(String waveString, boolean id) {
        // Example format: participants[1-10xminecraft:zombie,1-5xminecraft:zombie_villager]
        if(id) {
            return new Wave(waveString.replace("\n", "")
                .split("id\\[")[1]
                .replaceFirst("\\[.*]", ""),
                new ArrayList<>(List.of(
                waveString.replace("\n", "")
                        .split("participants\\[")[1]
                        .replaceFirst("\\[.*", "")
                        .split(", ")
        )));} else {
            return new Wave("",
                    new ArrayList<>(List.of(
                            waveString.replace("\n", "")
                                    .replaceAll(".*participants\\[", "")
                                    .replaceFirst("].*", "")
                                    .split(", ")
                    )));
        }

    }

    private ArrayList<Entity> participantStringToEntities(String participantString, World world) {
        // Example format: 1-10xminecraft:zombie
        // Example format: 5xminecraft:zombie_villager
        ArrayList<Entity> entities = new ArrayList<>();
        String[] participantParts = participantString.replace("\"", "").split("x ", 2);
        int amount;
        if(participantParts[0].contains("-")) {
            int min = Integer.parseInt(participantParts[0].split("-")[0]);
            int max = Integer.parseInt(participantParts[0].split("-")[1]);
            amount = new Random().nextInt(max - min) + min;
        } else amount = Integer.parseInt(participantParts[0]);
        Identifier entityType = new Identifier(participantParts[1].split(":")[0], participantParts[1].replaceFirst("\\{.*", "").split(":")[1]);
        for (int i = 0; i < amount; i++) {
            entities.add(Registry.ENTITY_TYPE.get(entityType).create(world));
        }
        return entities;
    }

    public ArrayList<Entity> manifest(World world) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (String participantString : participants) {
            entities.addAll(participantStringToEntities(participantString, world));
        }
        return entities;
    }
}
