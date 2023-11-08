package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class Participant {
    private Identifier id;
    private float weight;
    private int amount;
    @SerializedName("type")
    private String entityTypeString;
    private String nbt;
    @SerializedName("min_days")
    private int minDays;
    @SerializedName("max_days")
    private int maxDays;
    Participant() {

    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

}
