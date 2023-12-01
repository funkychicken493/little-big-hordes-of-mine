package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;

import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Wave {
    private Identifier id;
    private final List<Dynamic<?>> pools;
    public List<Dynamic<?>> getPools() {
        return pools;
    }

    private Wave(List<Dynamic<?>> pools) {
        this.pools = pools;
    }

    public static Wave create(List<Dynamic<?>> pools) {
        ArrayList<Dynamic<?>> outputPools = new ArrayList<>();
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
                        // Utilize the Participant.CODEC to parse the participant
                        DataResult<Participant> result = Participant.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(new Gson().toJson(participant)));
                }
            }
        }

        return new Wave(pools);
    }

    public static final Codec<Wave> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.PASSTHROUGH.listOf().fieldOf("pools").forGetter(Wave::getPools)
    ).apply(instance, Wave::create));

    public void setId(Identifier id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "id=" + id +
                ", pools=" + pools +
                '}';
    }
}

