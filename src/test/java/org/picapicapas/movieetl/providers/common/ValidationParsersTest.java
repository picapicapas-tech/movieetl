package org.picapicapas.movieetl.providers.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class ValidationParsersTest {

    @Test
    public void testParseDoubleValid() {
        assertEquals(85.5, ValidationParsers.parseDouble("85.5"), 0.01);
    }

    @Test
    public void testParseDoubleNull() {
        assertNull(ValidationParsers.parseDouble(null));
    }

    @Test
    public void testParseDoubleEmpty() {
        assertNull(ValidationParsers.parseDouble(""));
    }

    @Test
    public void testParseDoubleWhitespace() {
        assertNull(ValidationParsers.parseDouble("   "));
    }

    @Test
    public void testParseDoubleInvalid() {
        assertThrows(IllegalArgumentException.class, () -> ValidationParsers.parseDouble("not a number"));
    }

    @Test
    public void testParseDoubleWithWhitespace() {
        assertEquals(85.5, ValidationParsers.parseDouble("  85.5  "), 0.01);
    }

    @Test
    public void testParseLongValid() {
        assertEquals(100000L, ValidationParsers.parseLong("100000").longValue());
    }

    @Test
    public void testParseLongNull() {
        assertNull(ValidationParsers.parseLong(null));
    }

    @Test
    public void testParseLongEmpty() {
        assertNull(ValidationParsers.parseLong(""));
    }

    @Test
    public void testParseLongInvalid() {
        assertThrows(IllegalArgumentException.class, () -> ValidationParsers.parseLong("not a long"));
    }

    @Test
    public void testParseIntegerValid() {
        assertEquals(150, ValidationParsers.parseInt("150").intValue());
    }

    @Test
    public void testParseIntegerNull() {
        assertNull(ValidationParsers.parseInt(null));
    }

    @Test
    public void testParseIntegerEmpty() {
        assertNull(ValidationParsers.parseInt(""));
    }

    @Test
    public void testParseIntegerInvalid() {
        assertThrows(IllegalArgumentException.class, () -> ValidationParsers.parseInt("not an integer"));
    }
}
