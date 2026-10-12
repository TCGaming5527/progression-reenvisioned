package net.lixir.pre.util.easing;

/**
 * @author lixir
 */
public interface EasingFunction {
    /**
     * @param t normalized input in range [0, 1]
     * @return eased output, usually in range [0, 1]
     */
    float ease(float t);
}
