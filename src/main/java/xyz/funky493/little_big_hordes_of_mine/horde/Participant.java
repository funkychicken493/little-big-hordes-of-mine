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
//    private String nbt;
//    @SerializedName("min_days")
//    private int minDays;
//    @SerializedName("max_days")
//    private int maxDays;
    Participant() {

    }

    public EntityType<?> getActualEntityType() {
        return Registry.ENTITY_TYPE.get(entityType);
    }

//    public NbtCompound getNbt() {
//        try {
//            return StringNbtReader.parse(nbt);
//        } catch (CommandSyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

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

    public static final Codec<Participant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("type").forGetter(Participant::getEntityType),
            Codec.INT.fieldOf("amount").forGetter(Participant::getAmount)
    ).apply(instance, Participant::new));

    public Participant(Identifier entityType, int amount) {
        this.id = null;
        this.entityType = entityType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", entityType=" + entityType +
                ", amount=" + amount +
                '}';
    }
}
