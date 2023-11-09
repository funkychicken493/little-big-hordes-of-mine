package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import net.minecraft.server.world.ServerWorld;
import xyz.funky493.little_big_hordes_of_mine.datapack.Condition;

public abstract class WorldCondition extends Condition {
    @Override
    public String getConditionType() {
        return "world";
    }
    @Override
    public abstract String getConditionId();
    public abstract boolean isConditionMet(ServerWorld world, String conditionArgument);
}
