package xyz.funky493.little_big_hordes_of_mine;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.funky493.little_big_hordes_of_mine.datapack.Condition;
import xyz.funky493.little_big_hordes_of_mine.datapack.conditions.FabricCondition;
import xyz.funky493.little_big_hordes_of_mine.datapack.conditions.TimeCondition;
import xyz.funky493.little_big_hordes_of_mine.datapack.conditions.WeatherCondition;
import xyz.funky493.little_big_hordes_of_mine.horde.Participant;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

// Contains all data loaded from any datapacks
public class LoadedData {
    private final Map<Identifier, Wave> waves;
    private final Map<Identifier, Participant> participants;

    private final ArrayList<Condition> conditions;
    public LoadedData() {
        waves = new HashMap<>();
        participants = new HashMap<>();
        conditions = new ArrayList<>();
        registerConditions();
    }

    public void loadWave(JsonElement json, Identifier id) {
        DataResult<Wave> result = Wave.CODEC.parse(JsonOps.INSTANCE, json);
        Wave wave = result.resultOrPartial(LOGGER::error).orElseThrow();
        wave.setId(id);
        waves.put(id, wave);
        LOGGER.info("Loaded wave " + wave.getId() + " as: " + wave);
    }

    public void loadParticipant(JsonElement json, Identifier id) {
        DataResult<Participant> result = Participant.CODEC.parse(JsonOps.INSTANCE, json);
        Participant participant = result.resultOrPartial(LOGGER::error).orElseThrow();
        participant.setId(id);
        participants.put(id, participant);
        LOGGER.info("Loaded participant " + id + " as: " + participant);
    }

    public void registerCondition(Condition condition) {
        conditions.add(condition);
    }
    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public boolean isConditionPresent(String id) {
        for(Condition condition : conditions) {
            if(condition.getConditionId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void registerConditions() {
        registerCondition(new FabricCondition());
        registerCondition(new TimeCondition());
        registerCondition(new WeatherCondition());
    }

    public Map<Identifier, Wave> getWaves() {
        return waves;
    }
    public Map<Identifier, Participant> getParticipants() {
        return participants;
    }

    public Wave getWave(Identifier id) {
        return waves.get(id);
    }
    public Participant getParticipant(Identifier id) {
        return participants.get(id);
    }

    public boolean isWaveLoaded(Identifier id) {
        return waves.containsKey(id);
    }
    public boolean isParticipantLoaded(Identifier id) {
        return participants.containsKey(id);
    }

    public Condition getCondition(String key) {
        for(Condition condition : conditions) {
            if(condition.getConditionId().equals(key)) {
                return condition;
            }
        }
        return null;
    }
}
