package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class LightLevelCondition extends LocationCondition {
    @Override
    public String getConditionId() {
        return "light";
    }

    @Override
    public boolean isConditionMet(ServerWorld world, BlockPos pos, String conditionArgument) {
        return world.getLightLevel(pos) >= Integer.parseInt(conditionArgument);
    }
}
