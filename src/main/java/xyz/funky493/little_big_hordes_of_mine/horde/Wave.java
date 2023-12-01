package xyz.funky493.little_big_hordes_of_mine.horde;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
        }

        return new Wave(pools);
    }

    public static final Codec<Wave> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.PASSTHROUGH.listOf().fieldOf("pools").forGetter(Wave::getPools)
    ).apply(instance, Wave::create));
}

