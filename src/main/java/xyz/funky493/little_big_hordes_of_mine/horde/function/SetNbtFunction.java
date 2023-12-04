package xyz.funky493.little_big_hordes_of_mine.horde.function;

import xyz.funky493.little_big_hordes_of_mine.horde.Participant;

public class SetNbtFunction extends ParticipantFunction {
    @Override
    public String getFunctionId() {
        return "set_nbt";
    }

    @Override
    public void applyTo(Participant participant, Object... args) {
        participant.setNbt((String) args[0]);
    }
}
