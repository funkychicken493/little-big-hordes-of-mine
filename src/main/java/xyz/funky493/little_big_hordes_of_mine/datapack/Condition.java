package xyz.funky493.little_big_hordes_of_mine.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

public abstract class Condition {
    public abstract ConditionType getConditionType();
    public abstract String getConditionId();
    public enum ConditionType {
        WORLD,
        FABRIC
    }
}
