package xyz.funky493.little_big_hordes_of_mine.datapack;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.horde.Horde;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

// Contains all data loaded from any datapacks
public class LoadedData {
    public ArrayList<Wave> waves;
    public ArrayList<Horde> hordes;
    public LoadedData() {
        waves = new ArrayList<>();
        hordes = new ArrayList<>();
    }

    public void loadWave(InputStream stream, Identifier location) {
        Gson gson = new Gson().newBuilder().setLenient().create();
        Wave wave = null;
        wave = gson.fromJson(new InputStreamReader(stream), Wave.class);
        if(wave.getId() == null) {
            wave.setId(location);
        }
        waves.add(wave);
        LOGGER.info("Loaded wave " + wave.getId() + " with "  + " participants from " + location);
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadHorde(InputStream stream) {

    }
}
