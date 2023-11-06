package xyz.funky493.little_big_hordes_of_mine;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.funky493.little_big_hordes_of_mine.command.LittleBigHordesOfMineCommand;

public class LittleBigHordesOfMine implements ModInitializer {
    public static final String MODID = "little_big_hordes_of_mine";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static LittleBigHordesOfMineConfig config;
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        AutoConfig.register(LittleBigHordesOfMineConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(LittleBigHordesOfMineConfig.class).getConfig();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> LittleBigHordesOfMineCommand.register(dispatcher));

    }
}
