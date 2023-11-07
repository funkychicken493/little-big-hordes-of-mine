package xyz.funky493.little_big_hordes_of_mine;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.HashMap;
import java.util.Map;

@Config(name = "little_big_hordes_of_mine")
public class LittleBigHordesOfMineConfig implements ConfigData {
    @Comment("Whether natural hordes are enabled")
    public boolean naturalHordesEnabled = true;
    public Map<String, Map<String, String>> waves = new HashMap<>(Map.ofEntries(
            Map.entry("zombie_variety", Map.of(
                    "10x minecraft:zombie", "",
                    "10x minecraft:zombie_villager", ""
            )),
            Map.entry("glowing_flying_zombie", Map.of(
                    "10-20x minecraft:zombie", "{'NoGravity': 1b, 'Glowing': 1b}"
            )),
            Map.entry("super_zombie_assault", Map.of(
                    "base waves", "zombie_variety, glowing_flying_zombie"
            ))
    ));
}
