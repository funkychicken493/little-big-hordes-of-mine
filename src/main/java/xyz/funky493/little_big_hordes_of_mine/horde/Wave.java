package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Map;

/**
 * A wave is a table of participants that will be chosen at random.
 * Each participant has a weight, which is the chance of it being chosen, the higher the weight, the higher the chance.
 */
public class Wave {
    private Identifier id;
    @SerializedName("participants")
    private Map<String, Float> participantsTable;
    @SerializedName("selection_amount")
    private int selectionAmount;

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }
    public int getSelectionAmount() {
        return selectionAmount;
    }
    public Map<String, Float> getParticipantsTable() {
        return participantsTable;
    }

    Wave() {
    }

    Wave(int selectionAmount, Map<String, Float> participantsTable) {
        this.id = null;
        this.selectionAmount = selectionAmount;
        this.participantsTable = participantsTable;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "id=" + id +
                ", selectionAmount=" + selectionAmount +
                ", participantsTable=" + participantsTable +
                '}';
    }

    public static final Codec<Wave> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("selection_amount").forGetter(Wave::getSelectionAmount),
            Codec.unboundedMap(Codec.STRING, Codec.FLOAT).fieldOf("participants").forGetter(Wave::getParticipantsTable)
    ).apply(instance, Wave::new));
}

