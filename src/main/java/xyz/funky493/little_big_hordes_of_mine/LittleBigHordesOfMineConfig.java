package xyz.funky493.little_big_hordes_of_mine;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.HashMap;
import java.util.Map;

@Config(name = "little_big_hordes_of_mine")
public class LittleBigHordesOfMineConfig implements ConfigData {
    @Comment("View the file at \"config/little_big_hordes_of_mine.json5\" for the full config!")
    public boolean naturalHordesEnabled = true;
    Map<String, String> test = Map.of("test", "test");
}
