package xyz.funky493.little_big_hordes_of_mine.datapack.conditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;
import xyz.funky493.little_big_hordes_of_mine.datapack.Condition;

/**
 * A condition that utilizes built-in Fabric conditions.
 * @see ResourceConditions
 */
public class FabricCondition extends Condition {
    @Override
    public ConditionType getConditionType() {
        return ConditionType.FABRIC;
    }

    @Override
    public String getConditionId() {
        return "fabric";
    }

    public boolean isConditionMet(String conditionArgument) {
        try {
            JsonArray jsonArray = JsonParser.parseString(conditionArgument).getAsJsonArray();
            return ResourceConditions.conditionsMatch(jsonArray, true);
        } catch (Exception e) {
            LittleBigHordesOfMine.LOGGER.error("Failed to parse Fabric condition: " + conditionArgument + ": " + e);
            return false;
        }
    }
}
