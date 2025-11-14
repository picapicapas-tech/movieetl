package org.picapicapas.movieetl.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MovieKeyTest {

    private MovieKey movieKey1;
    private MovieKey movieKey2;
    private MovieKey movieKey3;

    @Before
    public void setUp() {
        movieKey1 = new MovieKey("Inception", 2010);
        movieKey2 = new MovieKey("Inception", 2010);
        movieKey3 = new MovieKey("The Matrix", 1999);
    }

    @Test
    public void testMovieKeyCreation() {
        assertNotNull(movieKey1);
        assertEquals("inception", movieKey1.getNormalizedTitle());
        assertEquals(Integer.valueOf(2010), movieKey1.getReleaseYear());
    }

    @Test
    public void testMovieKeyNormalization() {
        MovieKey key = new MovieKey("  INCEPTION  ", 2010);
        assertEquals("inception", key.getNormalizedTitle());
    }

    @Test
    public void testMovieKeyEquals() {
        assertTrue(movieKey1.equals(movieKey2));
        assertFalse(movieKey1.equals(movieKey3));
        assertFalse(movieKey1.equals(null));
        assertTrue(movieKey1.equals(movieKey1));
    }

    @Test
    public void testMovieKeyHashCode() {
        assertEquals(movieKey1.hashCode(), movieKey2.hashCode());
        assertNotEquals(movieKey1.hashCode(), movieKey3.hashCode());
    }

    @Test
    public void testMovieKeyToString() {
        String result = movieKey1.toString();
        assertTrue(result.contains("inception"));
        assertTrue(result.contains("2010"));
    }

    @Test
    public void testMovieKeyToStringWithoutYear() {
        MovieKey key = new MovieKey("Inception", null);
        assertEquals("inception", key.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMovieKeyWithNullTitle() {
        new MovieKey(null, 2010);
    }

    @Test
    public void testMovieKeyWithEmptyTitle() {
        // Empty strings are normalized to empty, which is allowed
        MovieKey key = new MovieKey("", 2010);
        assertEquals("", key.getNormalizedTitle());
    }

    @Test
    public void testMovieKeyWithNullYear() {
        MovieKey key = new MovieKey("Inception", null);
        assertNotNull(key);
        assertEquals(null, key.getReleaseYear());
    }
}
