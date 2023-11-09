package xyz.funky493.little_big_hordes_of_mine.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;
import xyz.funky493.little_big_hordes_of_mine.command.suggestion.ParticipantSuggestionProvider;
import xyz.funky493.little_big_hordes_of_mine.horde.Participant;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LittleBigHordesOfMineCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LittleBigHordesOfMineCommand.register(dispatcher);
        });
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        final LiteralCommandNode<ServerCommandSource> lbhom = dispatcher.register(
        literal("little_big_hordes_of_mine")
            .requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
            .then(literal("reload_config").executes((commandContext) -> {
                    LittleBigHordesOfMine.reloadConfig();
                    commandContext.getSource().sendFeedback(Text.literal("Reloaded config, to reload resource jsons, use /reload"), false);
                    return 1;}))
            .then(literal("dump")
                .then(argument("type", StringArgumentType.string()).suggests((context, builder) -> {
                    builder.suggest("hordes");
                    builder.suggest("waves");
                    builder.suggest("participants");
                    return builder.buildFuture();
                })
                .executes((commandContext) -> {
                    String type = StringArgumentType.getString(commandContext, "type");
                    switch(type) {
                        case "participants":
                            commandContext.getSource().sendFeedback(Text.literal(LittleBigHordesOfMine.loadedData.getParticipants().toString()), false);
                            break;
                        case "waves":
                            commandContext.getSource().sendFeedback(Text.literal(LittleBigHordesOfMine.loadedData.getWaves().toString()), false);
                            break;
                        default:
                            commandContext.getSource().sendFeedback(Text.literal("Unknown type: " + type), false);
                            return 0;
                    }
                    commandContext.getSource().sendFeedback(Text.literal("Dumped " + type), false);
                    return 1;})))
            .then(literal("summon_participant")
                .then(argument("pos", BlockPosArgumentType.blockPos())
                .then(argument("participant", IdentifierArgumentType.identifier()).suggests(new ParticipantSuggestionProvider())
                .executes((commandContext) -> {
                    Identifier identifier = IdentifierArgumentType.getIdentifier(commandContext, "participant");
                    Participant participant = LittleBigHordesOfMine.loadedData.getParticipant(identifier);
                    participant.summon(commandContext.getSource().getWorld(), BlockPosArgumentType.getBlockPos(commandContext, "pos"));
                    commandContext.getSource().sendFeedback(Text.literal("Summoned participant: " + identifier), false);
                    return 1;}))))
        );
        dispatcher.register(literal("lbhom").redirect(lbhom));
    }
}
