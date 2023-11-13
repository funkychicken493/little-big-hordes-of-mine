package xyz.funky493.little_big_hordes_of_mine.datapack.function;

import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import xyz.funky493.little_big_hordes_of_mine.datapack.function.type.EntityFunctionType;
import xyz.funky493.little_big_hordes_of_mine.horde.EntityPoolEntry;

public interface EntityFunction extends LootFunction {
    EntityFunctionType getType();
    boolean canRun(EntityPoolEntry entry);
    void run(EntityPoolEntry entry);
}
