package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

/**
 * A wave is a table of participants that will be chosen at random.
 * Each participant has a weight, which is the chance of it being chosen, the higher the weight, the higher the chance.
 */
public class Wave {
    public Identifier id;
    public ArrayList<Participant> participantsTable;
    public float weight;
    public int selectionAmount;
    public int minDays;
    public int maxDays;

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }

    public ArrayList<Participant> getParticipantsTable() {
        return participantsTable;
    }
    Wave() {

    }
}

