package org.krishnamatta.util;

public class Utils {
    /**
     *
     * @param in        - input character
     * @param minValue  - minimum expected value
     * @param maxValue  - maximum expected value
     * @return          - true if it in the range
     */
    public static boolean isCharInRange(char in,
                                        char minValue,
                                        char maxValue) {
        char min = minValue < maxValue ? minValue : maxValue;
        char max = minValue < maxValue ? maxValue : minValue;
        return (in >= min && in <= max);
    }
}
