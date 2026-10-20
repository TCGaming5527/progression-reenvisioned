package net.lixir.pre.common.util.easing;

import static java.lang.Math.clamp;
import static java.lang.Math.min;

public final class TransitionMath {
    private TransitionMath() {

    }

    public static int fadeAlpha(final int colour, float t) {
        t = clamp(t, 0f, 1f);

        int a = (int) (((colour >> 24) & 0xFF) * t);
        int rgb = colour & 0x00FFFFFF;
        return (a << 24) | rgb;
    }


    /**
    Use {@link Tween} instead.
    */
    @Deprecated
    public static float transitionInOut(
            float percent,
            final float transitionInPercent,
            final float transitionOutPercent
    ) {
        percent = clamp(percent, 0f, 1f);
        float fadeIn = clamp(percent / transitionInPercent, 0f, 1f);
        float fadeOutStart = 1 - transitionOutPercent;
        float fadeOutProgress = clamp(
                (percent - fadeOutStart) / transitionOutPercent,
                0f, 1f);
        float fadeOut = 1f - fadeOutProgress;
        return min(fadeIn, fadeOut);
    }

    public static int lerpARGB(int start, int end, float t) {
        t = clamp(t, 0f, 1f);

        int aStart = (start >> 24) & 0xFF;
        int rStart = (start >> 16) & 0xFF;
        int gStart = (start >> 8) & 0xFF;
        int bStart = start & 0xFF;

        int aEnd = (end >> 24) & 0xFF;
        int rEnd = (end >> 16) & 0xFF;
        int gEnd = (end >> 8) & 0xFF;
        int bEnd = end & 0xFF;

        int a = (int) (aStart + (aEnd - aStart) * t);
        int r = (int) (rStart + (rEnd - rStart) * t);
        int g = (int) (gStart + (gEnd - gStart) * t);
        int b = (int) (bStart + (bEnd - bStart) * t);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
