package xyz.funky493.little_big_hordes_of_mine.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class ApplicablePotionEffect {
    private final StatusEffectInstance effect;

    public ApplicablePotionEffect(String effectName, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, boolean permanent) {
        this.effect = new StatusEffectInstance(Objects.requireNonNull(Registry.STATUS_EFFECT.get(Identifier.tryParse(effectName))), duration, amplifier, ambient, showParticles, showIcon);
        this.effect.setPermanent(permanent);
    }

    public StatusEffectInstance getEffect() {
        return effect;
    }

    public static final Codec<ApplicablePotionEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter((ApplicablePotionEffect e) -> e.effect.getEffectType().getName().toString()),
            Codec.INT.optionalFieldOf("duration", 1000000000).forGetter((ApplicablePotionEffect e) -> e.effect.getDuration()),
            Codec.INT.optionalFieldOf("amplifier", 0).forGetter((ApplicablePotionEffect e) -> e.effect.getAmplifier()),
            Codec.BOOL.optionalFieldOf("ambient", false).forGetter((ApplicablePotionEffect e) -> e.effect.isAmbient()),
            Codec.BOOL.optionalFieldOf("show_particles", true).forGetter((ApplicablePotionEffect e) -> e.effect.shouldShowParticles()),
            Codec.BOOL.optionalFieldOf("show_icon", true).forGetter((ApplicablePotionEffect e) -> e.effect.shouldShowIcon()),
            Codec.BOOL.optionalFieldOf("permanent", false).forGetter((ApplicablePotionEffect e) -> e.effect.isPermanent())
    ).apply(instance, ApplicablePotionEffect::new));

    @Override
    public String toString() {
        return "ApplicablePotionEffect{" +
                "type=" + effect.getEffectType().getName() +
                ", duration=" + effect.getDuration() +
                ", amplifier=" + effect.getAmplifier() +
                ", ambient=" + effect.isAmbient() +
                ", showParticles=" + effect.shouldShowParticles() +
                ", showIcon=" + effect.shouldShowIcon() +
                ", permanent=" + effect.isPermanent() +
                '}';
    }
}
