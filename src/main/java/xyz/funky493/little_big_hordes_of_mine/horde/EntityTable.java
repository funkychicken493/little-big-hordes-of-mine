package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.datapack.condition.EntityCondition;
import xyz.funky493.little_big_hordes_of_mine.datapack.function.EntityFunction;

import java.util.ArrayList;

public class EntityTable {
    private Identifier resourceLocation;
    private ArrayList<EntityCondition> conditions;
    private ArrayList<EntityFunction> functions;
    private ArrayList<EntityPool> pools;


    public void invokeFunctions() {
        for (EntityPool pool : pools) {
            pool.invokeFunctions(functions);
        }
    }
}
