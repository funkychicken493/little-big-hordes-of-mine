package xyz.funky493.little_big_hordes_of_mine.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.LittleBigHordesOfMine;

import java.util.concurrent.CompletableFuture;

public class ParticipantSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        for(Identifier key : LittleBigHordesOfMine.loadedData.getParticipants().keySet()) {
            if(key.toString().startsWith(builder.getRemaining().toLowerCase())) {
                builder.suggest(key.toString());
            }
        }
        return builder.buildFuture();
    }
}
