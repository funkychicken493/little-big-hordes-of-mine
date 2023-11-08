package xyz.funky493.little_big_hordes_of_mine.datapack;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;
import xyz.funky493.little_big_hordes_of_mine.horde.Horde;
import xyz.funky493.little_big_hordes_of_mine.horde.Participant;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;
import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Contains all data loaded from any datapacks
public class LoadedData {
    public Map<String, Wave> waves;
    public LoadedData() {
        waves = new HashMap<>();
    }

    public void loadWave(JsonElement json, Identifier id) {
        DataResult<Wave> result = Wave.CODEC.parse(JsonOps.INSTANCE, json);
        Wave wave = result.resultOrPartial(LOGGER::error).orElseThrow();
        wave.setId(id);
        waves.put(id.toString(), wave);
        LOGGER.info("Loaded wave " + wave.getId() + " as: " + wave);
    }
}
