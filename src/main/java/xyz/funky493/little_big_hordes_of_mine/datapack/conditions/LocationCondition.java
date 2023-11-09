package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import xyz.funky493.little_big_hordes_of_mine.datapack.Condition;

public abstract class LocationCondition extends Condition {
    @Override
    public String getConditionType() {
        return "location";
    }

    public abstract String getConditionId();
    public abstract boolean isConditionMet(ServerWorld world, BlockPos pos, String conditionArgument);
}
