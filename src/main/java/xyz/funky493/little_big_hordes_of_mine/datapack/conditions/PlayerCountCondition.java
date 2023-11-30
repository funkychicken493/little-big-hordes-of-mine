package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import net.minecraft.server.world.ServerWorld;
import xyz.funky493.little_big_hordes_of_mine.util.InputUtil;

public class PlayerCountCondition extends WorldCondition {
    @Override
    public String getConditionId() {
        return "player_count";
    }

    @Override
    public boolean isConditionMet(ServerWorld world, String conditionArgument) {
        return InputUtil.checkComparatorString(conditionArgument, (float) world.getPlayers().size());
    }
}
