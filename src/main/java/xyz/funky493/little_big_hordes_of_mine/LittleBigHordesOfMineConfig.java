package xyz.funky493.little_big_hordes_of_mine;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Config(name = "little_big_hordes_of_mine")
public class LittleBigHordesOfMineConfig implements ConfigData {
    @Comment("View the file at \"config/little_big_hordes_of_mine.json5\" for the full config!")
    public boolean naturalHordesEnabled = true;
    Map<String, Map<String, NbtCompound>> waves = new HashMap<>(Map.ofEntries(
            Map.entry("zombie_variety", Map.of(
                    "10x minecraft:zombie", new NbtCompound(),
                    "10x minecraft:zombie_villager", new NbtCompound()
            )),
            Map.entry("just_normal_zombie", Map.of(
                    "10-20x minecraft:zombie", new NbtCompound()
            ))
    ));
}
