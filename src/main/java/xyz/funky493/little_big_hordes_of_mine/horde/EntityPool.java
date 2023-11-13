package xyz.funky493.little_big_hordes_of_mine.horde;

import com.google.common.base.Predicates;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
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
}
