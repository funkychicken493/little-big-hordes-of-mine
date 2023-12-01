package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

public class Wave {
    private static Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().setLenient().create();
    private Identifier id;
    private List<Dynamic<?>> rawPools;
    private ArrayList<Object> parsedPools;
    public List<Dynamic<?>> getRawPools() {
        return rawPools;
    }

    public ArrayList<Object> getParsedPools() {
        return parsedPools;
    }

    private Wave(List<Dynamic<?>> rawPools, ArrayList<Object> parsedPools) {
        this.rawPools = rawPools;
        this.parsedPools = parsedPools;
    }

    public static Wave create(List<Dynamic<?>> pools) {
        ArrayList<Object> outputPools = new ArrayList<>();
        for(Dynamic<?> pool : pools) {
            List<Map<String, Dynamic<?>>> participants = pool.get("participants").asList((dynamic) -> dynamic.asMap((dynamic1 -> dynamic1.asString().getOrThrow(false, LOGGER::error)), dynamic1 -> dynamic1));
            ArrayList<Participant> parsedParticipants = new ArrayList<>();
            // This is where the MAGIC happens D:
            for(Map<String, Dynamic<?>> participant : participants) {
                // First we need to figure out what type of statement we have in this map
                // The simplest type is lbhom:empty which is literally just not parsed
                switch(participant.get("type").asString("null")) {
                    case "lbhom:empty":
                        break;
                    case "lbhom:entity":
                        Map<String, Object> fixedParticipant = new HashMap<>();
                        for(Map.Entry<String, Dynamic<?>> entry : participant.entrySet()) {
                            fixedParticipant.put(entry.getKey(), entry.getValue().getValue());
                        }
                        LOGGER.info(fixedParticipant.toString());
                }
            }
            outputPools.add(pool);
        }
        return new Wave(pools, outputPools);
    }

    public static final Codec<Wave> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.PASSTHROUGH.listOf().fieldOf("pools").forGetter(Wave::getRawPools)
    ).apply(instance, Wave::create));

    public void setId(Identifier id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "id=" + id +
                ", pools=" + rawPools +
                '}';
    }
}

