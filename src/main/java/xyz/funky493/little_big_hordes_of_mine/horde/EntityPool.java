package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.common.base.Predicates;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.datapack.condition.EntityCondition;
import xyz.funky493.little_big_hordes_of_mine.datapack.function.EntityFunction;

import java.util.ArrayList;
import java.util.function.Predicate;

public class EntityPool {
    private Identifier resourceLocation;
    private ArrayList<EntityCondition> conditions;
    private ArrayList<EntityFunction> functions;
    private LootNumberProvider rolls;
    private LootNumberProvider bonusRolls;
    private ArrayList<EntityPoolEntry> entries;

    public int totalWeight() {
        int result = 0;
        for(EntityPoolEntry entry : entries) {
            result += entry.getWeight();
        }
        return result;
    }

    /**
     * Invokes the functions of this pool on all applicable entries.
     */
    public void invokeFunctions(ArrayList<EntityFunction> invokableFunctions) {
        for (EntityPoolEntry entry : entries) {
            for (EntityFunction function : invokableFunctions) {
                if (function.canRun(entry)) {
                    function.run(entry);
                }
            }
        }
    }

    /**
     * Gets random entries from this pool based on the rolls, bonus rolls, and weight.
     */
    public ArrayList<EntityPoolEntry> getEntries(ServerWorld world) {
        LootContext context = new LootContext.Builder(world)
                .random(world.random)
                .build(LootContextTypes.EMPTY);
        ArrayList<EntityPoolEntry> result = new ArrayList<>();
        for (int i = 0; i < rolls.nextInt(context); i++) {
            int weight = world.random.nextInt(totalWeight());
            for (EntityPoolEntry entry : entries) {
                weight -= entry.getWeight();
                if (weight <= 0) {
                    result.add(entry);
                    break;
                }
            }
        }
        return result;
    }
}
