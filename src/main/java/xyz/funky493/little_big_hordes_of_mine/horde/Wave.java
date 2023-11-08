package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

/**
 * A wave is a table of participants that will be chosen at random.
 * Each participant has a weight, which is the chance of it being chosen, the higher the weight, the higher the chance.
 */
public class Wave {
    private Identifier id;
//    @SerializedName("participants")
//    private ArrayList<Participant> participantsTable;
    @SerializedName("weight")
    private float weight;
    @SerializedName("selection_amount")
    private int selectionAmount;

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }

    public float getWeight() {
        return weight;
    }
    public int getSelectionAmount() {
        return selectionAmount;
    }
//    public ArrayList<Participant> getParticipantsTable() {
//        return participantsTable;
//    }
    Wave() {
    }

    Wave(float weight, int selectionAmount) {
        this.id = null;
        this.weight = weight;
        this.selectionAmount = selectionAmount;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "id=" + id +
//                ", participantsTable=" + participantsTable +
                ", weight=" + weight +
                ", selectionAmount=" + selectionAmount +
                '}';
    }

    public static final Codec<Wave> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("weight").forGetter(Wave::getWeight),
            Codec.INT.fieldOf("selection_amount").forGetter(Wave::getSelectionAmount)//,
//            Codec.list(Participant.CODEC).fieldOf("participants").forGetter(Wave::getParticipantsTable)
    ).apply(instance, Wave::new));
}

