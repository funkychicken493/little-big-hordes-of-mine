package xyz.funky493.little_big_hordes_of_mine.horde.function;

import xyz.funky493.little_big_hordes_of_mine.horde.Participant;
import xyz.funky493.little_big_hordes_of_mine.util.ApplicablePotionEffect;

import java.util.List;

public class SetEffectsFunction extends ParticipantFunction {
    @Override
    public String getFunctionId() {
        return "set_effects";
    }

    @Override
    public void applyTo(Participant participant, Object... args) {
        participant.setEffects((List<ApplicablePotionEffect>) args[0]);
    }
}
