package xyz.funky493.little_big_hordes_of_mine.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import xyz.funky493.little_big_hordes_of_mine.horde.Summoner;
import xyz.funky493.little_big_hordes_of_mine.horde.Wave;

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
                        Summoner.summonHorde(Wave.loadWaveFromString(waveString, false), context.getSource().getWorld(), pos);
                    } catch (Exception e) {
                        String error = "Error summoning wave " + waveString + " at " + pos.toString() + ": " + e;
                        LOGGER.error(error);
                        context.getSource().sendFeedback(Text.literal(error), true);
                        return -1;
                    }
                    // /lbhom testWave ~ ~ ~ "participants[1-10x minecraft:zombie, 1-5x minecraft:zombie_villager]"
                    context.getSource().sendFeedback(Text.literal("Summoned wave " + waveString + " at " + pos.toString()), true);
                    LOGGER.debug("Summoned wave " + waveString + " at " + pos + "(presumably successfully??)");
                    return 1;
                })))));
    }

}

