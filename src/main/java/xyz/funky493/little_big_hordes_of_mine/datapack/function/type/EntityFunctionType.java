package xyz.funky493.little_big_hordes_of_mine.datapack.function.type;

import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.JsonSerializer;

public class EntityFunctionType extends LootFunctionType {
    public EntityFunctionType(JsonSerializer<? extends LootFunction> jsonSerializer) {
        super(jsonSerializer);
    }
}
