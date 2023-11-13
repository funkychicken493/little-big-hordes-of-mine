package xyz.funky493.little_big_hordes_of_mine.horde;

import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.datapack.condition.EntityCondition;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class EntityPoolEntry {
    private Identifier resourceLocation;
    private ArrayList<EntityCondition> conditions;
    private int weight;

    public int getWeight() {
        return weight;
    }
}
