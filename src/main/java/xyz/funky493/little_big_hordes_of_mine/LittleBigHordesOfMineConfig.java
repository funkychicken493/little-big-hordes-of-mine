package xyz.funky493.little_big_hordes_of_mine;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.visitor.NbtElementVisitor;

import java.util.HashMap;
import java.util.Map;

@Config(name = "little_big_hordes_of_mine")
public class LittleBigHordesOfMineConfig implements ConfigData {
    @Comment("View the file at \"config/little_big_hordes_of_mine.json5\" for the full config!")
    public boolean naturalHordesEnabled = true;
    private static NbtCompound generateNbtCompound(Map<String, ?> elementMap) {
        NbtCompound compound = new NbtCompound();
        for (Map.Entry<String, ?> entry : elementMap.entrySet()) {
            compound.put(entry.getKey(), (NbtElement) entry.getValue());
        }
        return compound;
    }
    public Map<String, Map<String, NbtCompound>> waves = new HashMap<>(Map.ofEntries(
            Map.entry("zombie_variety", Map.of(
                    "10x minecraft:zombie", new NbtCompound(),
                    "10x minecraft:zombie_villager", new NbtCompound()
            )),
            Map.entry("just_normal_zombie", Map.of(
                    "10-20x minecraft:zombie", generateNbtCompound(Map.of("NoGravity", NbtByte.of(false)))
            ))
    ));
}
