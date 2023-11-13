package xyz.funky493.little_big_hordes_of_mine.datapack.function;

import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import xyz.funky493.little_big_hordes_of_mine.datapack.function.type.EntityFunctionType;

public interface EntityFunction extends LootFunction {
    EntityFunctionType getType();

}
