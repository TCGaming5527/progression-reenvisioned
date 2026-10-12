package net.lixir.pre.util.easing;

import net.lixir.pre.util.FloatUnaryFunction;

/**
 * @author lixir
 */
public enum EasingTypes implements EasingFunction {
    LINEAR(t -> t),

    // SINE
    EASE_IN_SINE(t -> 1f - (float) Math.cos(t * Math.PI / 2f)),
    EASE_OUT_SINE(t -> (float) Math.sin(t * Math.PI / 2f)),
    EASE_IN_OUT_SINE(t -> -(float) (Math.cos(Math.PI * t) - 1f) / 2f),

    // QUAD
    EASE_IN_QUAD(t -> t * t),
    EASE_OUT_QUAD(t -> {
        float u = 1f - t;
        return 1f - u * u;
    }),
    EASE_IN_OUT_QUAD(t ->
            t < 0.5f
                    ? 2f * t * t
                    : 1f - (float) Math.pow(-2f * t + 2f, 2) / 2f
    ),

    // CUBIC
    EASE_IN_CUBIC(t -> t * t * t),
    EASE_OUT_CUBIC(t -> {
        float u = 1f - t;
        return 1f - u * u * u;
    }),
    EASE_IN_OUT_CUBIC(t ->
            t < 0.5f
                    ? 4f * t * t * t
                    : 1f - (float) Math.pow(-2f * t + 2f, 3) / 2f
    );

    private final FloatUnaryFunction function;

    EasingTypes(FloatUnaryFunction function) {
        this.function = function;
    }

    @Override
    public float ease(float t) {
        return function.apply(t);
    }
}
