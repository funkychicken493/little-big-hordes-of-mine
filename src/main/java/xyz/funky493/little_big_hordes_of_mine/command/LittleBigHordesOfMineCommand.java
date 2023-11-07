package xyz.funky493.little_big_hordes_of_mine.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;
import xyz.funky493.little_big_hordes_of_mine.horde.Summoner;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;

import java.util.Arrays;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine.LOGGER;

public class LittleBigHordesOfMineCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("lbhom")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("testWave")
                .then(argument("pos", BlockPosArgumentType.blockPos())
                .then(argument("wave", StringArgumentType.string())
                .executes(context -> {
                    BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
                    String waveString = StringArgumentType.getString(context, "wave");
                    try {
                        if(waveString == null) {
                            LOGGER.error("Wave string is null!");
                            context.getSource().sendFeedback(Text.literal("Wave string is null!"), true);
                            return -1;
                        }
                        Summoner.summonHorde(Objects.requireNonNull(Wave.loadWaveFromConfig(waveString)), context.getSource().getWorld(), pos);
                    } catch (Exception e) {
                        String error = "Error summoning wave " + waveString + " at " + pos.toString() + ": " + e.getMessage() + " " + Arrays.toString(e.getStackTrace());
                        LOGGER.error(error);
                        context.getSource().sendFeedback(Text.literal(error), true);
                        return -1;
                    }
                    context.getSource().sendFeedback(Text.literal("Summoned wave " + waveString + " at " + pos.toString()), true);
                    LOGGER.debug("Summoned wave " + waveString + " at " + pos + "(presumably successfully??)");
                    return 1;
                })))));
        dispatcher.register(literal("lbhom")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("dumpWaves")
                .executes(context -> {
                    context.getSource().sendFeedback(Text.literal("Dumping waves to log..."), false);

                    try {
                        for(String waveId : LittleBigHordesOfMine.config.waves.keySet()) {
                            Wave wave = Wave.loadWaveFromConfig(waveId);
                            if(wave == null) {
                                LOGGER.error("Wave " + waveId + " is null!");
                                continue;
                            }
                            LOGGER.info("Wave " + waveId + ":");
                            for(String participant : wave.participants.keySet()) {
                                LOGGER.info("    " + participant + ": " + wave.participants.get(participant).toString());
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error dumping waves: " + e);
                        context.getSource().sendFeedback(Text.literal("Error dumping waves: " + e), true);
                        return -1;
                    }
                    return 1;
                })));
        dispatcher.register(literal("lbhom")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("reloadConfig")
                .executes(context -> {
                    LittleBigHordesOfMine.reloadConfig();
                    return 1;
                })));
    }

}

