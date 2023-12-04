package xyz.funky493.little_big_hordes_of_mine.horde.function;

import xyz.funky493.little_big_hordes_of_mine.horde.Participant;

public class SetAmountFunction extends ParticipantFunction {
    @Override
    public String getFunctionId() {
        return "set_amount";
    }

    @Override
    public void applyTo(Participant participant, Object... args) {
        participant.setAmount((int) args[0]);
    }
}
