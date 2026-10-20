package net.lixir.pre.common.util.easing;

import net.lixir.pre.common.util.FloatUnaryFunction;

import static java.lang.Math.clamp;

public final class Tween implements FloatUnaryFunction {
    private final float in;
    private final float out;
    private final EasingFunction easeIn;
    private final EasingFunction easeOut;

    public Tween(
            float in,
            float out
    ) {
        this(in, out, EasingTypes.LINEAR, EasingTypes.LINEAR);
    }

    public Tween(
            float in,
            float out,
            EasingFunction easeIn,
            EasingFunction easeOut
    ) {
        this.in = in;
        this.out = out;
        this.easeIn = easeIn;
        this.easeOut = easeOut;
    }

    @Override
    public float apply(float t) {
        t = clamp(t, 0f, 1f);

        float a = 1f;
        if (in > 0f) {
            a = easeIn.ease(clamp(t / in, 0f, 1f));
        }

        float b = 1f;
        if (out > 0f) {
            float start = 1f - out;
            b = 1f - easeOut.ease(clamp((t - start) / out, 0f, 1f));
        }

        return Math.min(a, b);
    }
}
