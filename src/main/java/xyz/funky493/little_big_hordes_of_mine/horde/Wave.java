package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

/**
 * A wave is a table of participants that will be chosen at random.
 * Each participant has a weight, which is the chance of it being chosen, the higher the weight, the higher the chance.
 */
public class Wave {
    //private Identifier id;
    //@SerializedName("participants")
    //private ArrayList<Participant> participantsTable;
    @SerializedName("weight")
    private float weight;
    @SerializedName("selection_amount")
    private int selectionAmount;
    @SerializedName("min_days")
    private int minDays;
    @SerializedName("max_days")
    private int maxDays;

//    public void setId(Identifier id) {
//        this.id = id;
//    }
//
//    public Identifier getId() {
//        return id;
//    }
    Wave() {
        weight = 0;
        selectionAmount = 0;
        minDays = 0;
        maxDays = 0;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Wave{" +
//                "id=" + id +
                ", participantsTable="  +
                ", weight=" + weight +
                ", selectionAmount=" + selectionAmount +
                ", minDays=" + minDays +
                ", maxDays=" + maxDays +
                '}';
    }
}

