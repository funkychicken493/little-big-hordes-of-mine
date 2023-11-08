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

// Contains all data loaded from any datapacks
public class LoadedData {
    public ArrayList<Wave> waves;
    public ArrayList<Horde> hordes;
    public ArrayList<Participant> participants;
    public LoadedData() {
        waves = new ArrayList<>();
        hordes = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public void loadWave(JsonElement json, Identifier id) {
        DataResult<Wave> result = Wave.CODEC.parse(JsonOps.INSTANCE, json);
        Wave wave = result.resultOrPartial(LOGGER::error).orElseThrow();
        wave.setId(id);
        waves.add(wave);
        LOGGER.info("Loaded wave " + wave.getId() + " as: " + wave);
    }
}
