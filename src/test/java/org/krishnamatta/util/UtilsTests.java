package org.krishnamatta.util;

import org.junit.Test;

import static org.krishnamatta.util.Utils.isCharInRange;
import static org.junit.Assert.*;

public class UtilsTests {

    @Test
    public void testIsCharInRange() {
        char min = '5';
        char max = '9';
        for(char c = '5'; c <= '9'; c ++) {
            assertTrue(isCharInRange(c, min, max));
        }

        assertTrue(isCharInRange('1', '1', '1'));
        assertTrue(isCharInRange('2', '6', '1'));
        assertFalse(isCharInRange('0', min, max));
        assertFalse(isCharInRange('.', min, max));
        assertFalse(isCharInRange('-', min, max));
        assertFalse(isCharInRange('\0', min, max));
    }
}