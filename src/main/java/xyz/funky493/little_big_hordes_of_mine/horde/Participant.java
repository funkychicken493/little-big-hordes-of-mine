package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Participant {
    private Identifier id;
    private int amount;
    @SerializedName("type")
    private Identifier entityType;
    private NbtCompound nbt;

    public Identifier getId() {
        return id;
    }
    public void setId(Identifier id) {
        this.id = id;
    }
    public int getAmount() {
        return amount;
    }
    public Identifier getEntityType() {
        return entityType;
    }
    public NbtCompound getNbt() {
        return nbt;
    }

    public static final Codec<Participant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("type").forGetter(Participant::getEntityType),
            Codec.INT.fieldOf("amount").forGetter(Participant::getAmount),
            NbtCompound.CODEC.optionalFieldOf("nbt", new NbtCompound()).forGetter(Participant::getNbt)
    ).apply(instance, Participant::new));

    public Participant(Identifier entityType, int amount, NbtCompound nbt) {
        this.id = null;
        this.entityType = entityType;
        this.amount = amount;
        this.nbt = nbt;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", entityType=" + entityType +
                ", amount=" + amount +
                ", nbt=" + nbt +
                '}';
    }
}
