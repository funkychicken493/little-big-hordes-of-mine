package xyz.funky493.little_big_hordes_of_mine.datapack;

import com.google.gson.*;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.horde.Horde;
import xyz.funky493.little_big_hordes_of_mine.horde.Participant;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

// Contains all data loaded from any datapacks
public class LoadedData {
    private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    public ArrayList<Wave> waves;
    public ArrayList<Horde> hordes;
    public ArrayList<Participant> participants;
    public LoadedData() {
        waves = new ArrayList<>();
        hordes = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public void loadParticipant(InputStream stream, Identifier location) {
        Participant participant = gson.fromJson(new InputStreamReader(stream), Participant.class);
        if(participant.getId() == null) {
            participant.setId(location);
        }
        participants.add(participant);
        LOGGER.info("Loaded participant " + participant.getId() + " from " + location);
        //tryClose(stream);
    }

    public void loadWave(String json, Identifier location) {
        Wave wave = gson.fromJson(json, Wave.class);
        wave.setId(location); // forcefully set the id to the path, even if it's already set
        waves.add(wave);
        LOGGER.info("Loaded wave " + wave + " from " + location);
    }

    public void loadHorde(InputStream stream) {

    }

    private void tryClose(InputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            LOGGER.error("Failed to close stream!");
            throw new RuntimeException(e);
        }
    }
}
