package xyz.funky493.little_big_hordes_of_mine.horde.function;

import xyz.funky493.little_big_hordes_of_mine.horde.Participant;

import java.text.DecimalFormat;

public abstract class ParticipantFunction {
    public abstract void applyTo(Participant participant, Object... args);

    public abstract String getFunctionId();
}
