package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.gson.annotations.SerializedName;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;

import java.util.ArrayList;

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

    public Entity summonOnce(World world, BlockPos pos) {
        NbtCompound compound = nbt.copy();
        if(!Registry.ENTITY_TYPE.containsId(entityType)) {
            LittleBigHordesOfMine.LOGGER.error("Entity type " + entityType.toString() + " does not exist");
            return null;
        }
        compound.putString("id", entityType.toString());
        ServerWorld serverWorld = (ServerWorld) world;
        Entity entity = EntityType.loadEntityWithPassengers(compound, serverWorld, (e) -> {
            e.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), e.getYaw(), e.getPitch());
            return e;
        });
        if(entity == null) {
            LittleBigHordesOfMine.LOGGER.error("Failed to summon entity " + entityType + " from participant " + id + " at " + pos.toShortString());
            return null;
        }
        serverWorld.spawnEntityAndPassengers(entity);
        return entity;
    }

    public ArrayList<Entity> summon(World world, BlockPos pos) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Entity entity = summonOnce(world, pos);
            if (entity != null) {
                entities.add(entity);
            } else {
                LittleBigHordesOfMine.LOGGER.error("Failed to summon entity " + entityType + " from participant " + id + " at " + pos.toShortString());
            }
        }
        return entities;
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
