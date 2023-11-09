package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import xyz.funky493.little_big_hordes_of_mine.datapack.Condition;

public class WeatherCondition extends WorldCondition {
    @Override
    public String getConditionId() {
        return "weather";
    }
    @Override
    public boolean isConditionMet(ServerWorld world, String conditionArgument) {
        return switch (conditionArgument) {
            case "clear" -> !world.isRaining() && !world.isThundering();
            case "rain" -> world.isRaining();
            case "thunder" -> world.isThundering();
            default -> false;
        };
    }


}
