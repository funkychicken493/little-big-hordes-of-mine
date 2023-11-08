package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Participant {
    private Identifier id;
    private float weight;
    private int amount;
    @SerializedName("type")
    private Identifier entityTypeString;
    private String nbt;
    @SerializedName("min_days")
    private int minDays;
    @SerializedName("max_days")
    private int maxDays;
    Participant() {

    }

    public EntityType<?> getEntityType() {
        return Registry.ENTITY_TYPE.get(entityTypeString);
    }

    public NbtCompound getNbt() {
        try {
            return StringNbtReader.parse(nbt);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

}
