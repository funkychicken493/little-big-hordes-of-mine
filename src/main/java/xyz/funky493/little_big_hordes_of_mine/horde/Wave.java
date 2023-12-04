package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;
import xyz.funky493.little_big_hordes_of_mine.LoadedData;
import xyz.funky493.little_big_hordes_of_mine.horde.function.ParticipantFunction;

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

            outputPools.add(pool);
        }
        return new Wave(pools, outputPools);
    }

    private static ArrayList<Participant> parseParticipants(List<Map<String, Dynamic<?>>> participants) {
        ArrayList<Participant> output = new ArrayList<>();
        for(Map<String, Dynamic<?>> participant : participants) {
            switch(participant.get("type").asString("null")) {
                case "lbhom:empty":
                    break;
                case "lbhom:entity":
                    Map<String, Object> fixedParticipant = new HashMap<>();
                    for(Map.Entry<String, Dynamic<?>> entry : participant.entrySet()) {
                        fixedParticipant.put(entry.getKey(), entry.getValue().getValue());
                    }
                    output.add(Participant.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(GSON.toJson(fixedParticipant))).resultOrPartial(LOGGER::error).orElseThrow());
                    break;
                case "lbhom:group":
                    List<Map<String, Dynamic<?>>> children = participant.get("children").asList((dynamic) -> dynamic.asMap((dynamic1 -> dynamic1.asString().getOrThrow(false, LOGGER::error)), dynamic1 -> dynamic1));
                    List<ParticipantFunction> functions = participant.get("functions").asList((dynamic) -> LittleBigHordesOfMine.loadedData.getParticipantFunction(dynamic.asString().getOrThrow(false, LOGGER::error)));
                    for(Map<String, Dynamic<?>> child : children) {
                        // if the child is a group, then we need to parse it recursively
                        if(child.get("type").asString("null").equals("lbhom:group")) {
                            output.addAll(parseParticipants(child.get("children").asList((dynamic) -> dynamic.asMap((dynamic1 -> dynamic1.asString().getOrThrow(false, LOGGER::error)), dynamic1 -> dynamic1))));
                        } else {
                            Map<String, Object> fixedChild = new HashMap<>();
                            for(Map.Entry<String, Dynamic<?>> entry : child.entrySet()) {
                                fixedChild.put(entry.getKey(), entry.getValue().getValue());
                            }
                            // tack on the functions to the child
                            fixedChild.put("functions", functions);
                            output.add(Participant.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(GSON.toJson(fixedChild))).resultOrPartial(LOGGER::error).orElseThrow());
                        }
                    }
                case "lbhom:reference":
                    // reference to a participant in loadedData
                    output.add(LittleBigHordesOfMine.loadedData.getParticipant(Identifier.tryParse(participant.get("id").asString("null"))));
            }
        }
        return output;
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

