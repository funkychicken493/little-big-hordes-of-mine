package xyz.funky493.little_big_hordes_of_mine.horde;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;
import xyz.funky493.little_big_hordes_of_mine.datapack.Condition;
import xyz.funky493.little_big_hordes_of_mine.datapack.conditions.FabricCondition;
import xyz.funky493.little_big_hordes_of_mine.datapack.conditions.WorldCondition;
import xyz.funky493.little_big_hordes_of_mine.util.ApplicablePotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Participant {
    private Identifier id;
    private int amount;
    private Identifier entityType;
    private NbtCompound nbt;
    private List<ApplicablePotionEffect> effects;
    private Map<String, Dynamic<?>> conditions;

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
    public List<ApplicablePotionEffect> getEffects() {
        return effects;
    }
    public static final Codec<Participant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("type").forGetter(Participant::getEntityType),
            Codec.INT.fieldOf("amount").forGetter(Participant::getAmount),
            NbtCompound.CODEC.optionalFieldOf("nbt", new NbtCompound()).forGetter(Participant::getNbt),
            Codec.unboundedMap(Codec.STRING, Codec.PASSTHROUGH).optionalFieldOf("conditions", Map.of()).forGetter(Participant::getConditions),
            ApplicablePotionEffect.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(Participant::getEffects)
    ).apply(instance, Participant::new));

    private Map<String, Dynamic<?>> getConditions() {
        return conditions;
    }

    public Participant(Identifier entityType, int amount, NbtCompound nbt, Map<String, Dynamic<?>> conditions, List<ApplicablePotionEffect> effects) {
        this.id = null;
        this.entityType = entityType;
        this.amount = amount;
        this.nbt = nbt;
        this.conditions = conditions;
        this.effects = effects;
    }

    public Entity summonOnce(World world, BlockPos pos) {
        LittleBigHordesOfMine.LOGGER.info(testConditions((ServerWorld) world) + " " + conditions.toString());
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
        for(ApplicablePotionEffect effect : effects) {
            ((LivingEntity) entity).addStatusEffect(effect.getEffect());
        }
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
                ", conditions=" + conditions +
                ", effects=" + effects +
                '}';
    }

    public boolean testConditions(ServerWorld world) {
        for(Map.Entry<String, Dynamic<?>> entry : conditions.entrySet()) {
            Condition condition = LittleBigHordesOfMine.loadedData.getCondition(entry.getKey());
            if(condition == null) {
                LittleBigHordesOfMine.LOGGER.error("Condition " + entry.getKey() + " does not exist");
                return false;
            }
            LittleBigHordesOfMine.LOGGER.info("Testing condition " + entry.getKey() + " with value " + entry.getValue().getValue().toString().replace("\"", ""));
            switch(condition.getConditionType()) {
                case WORLD:
                    if(!((WorldCondition) condition).isConditionMet(world, entry.getValue().getValue().toString().replace("\"", ""))) {
                        return false;
                    }
                    break;
                case FABRIC:
                    String value = entry.getValue().getValue().toString();
                    if(!((FabricCondition) condition).isConditionMet(value)) {
                        return false;
                    }
                    break;
                default:
                    LittleBigHordesOfMine.LOGGER.error("Unknown condition type " + condition.getConditionType());
                    return false;
            }
        }
        return true;
    }
}
