package xyz.funky493.little_big_hordes_of_mine.horde.function;

import net.minecraft.util.Identifier;
import xyz.funky493.little_big_hordes_of_mine.horde.Participant;

public class SetEntityTypeFunction extends ParticipantFunction {
    @Override
    public String getFunctionId() {
        return "set_entity_type";
    }

    @Override
    public void applyTo(Participant participant, Object... args) {
        participant.setEntityType(Identifier.tryParse((String) args[0]));
    }
}
