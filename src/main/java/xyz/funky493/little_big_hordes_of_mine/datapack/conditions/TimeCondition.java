package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import net.minecraft.server.world.ServerWorld;

public class TimeCondition extends WorldCondition {
    @Override
    public String getConditionId() {
        return "time";
    }

    @Override
    public boolean isConditionMet(ServerWorld world, String conditionArgument) {
        return switch (conditionArgument) {
            case "day":
                yield world.getTimeOfDay() < 12000;
            case "night":
                yield world.getTimeOfDay() >= 12000;
            case "midday":
                yield world.getTimeOfDay() >= 6000 && world.getTimeOfDay() < 18000;
            default:
                if(conditionArgument.matches("\\d+-\\d+")){
                    String[] timeRange = conditionArgument.split("-");
                    int startTime = Integer.parseInt(timeRange[0]);
                    int endTime = Integer.parseInt(timeRange[1]);
                    yield world.getTimeOfDay() >= startTime && world.getTimeOfDay() < endTime;
                } else {
                    yield false;
                }
        };
    }
}
