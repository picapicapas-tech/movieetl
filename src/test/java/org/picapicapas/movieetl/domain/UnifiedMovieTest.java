package org.picapicapas.movieetl.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UnifiedMovieTest {

    private UnifiedMovie unifiedMovie;
    private MovieKey movieKey;

    @Before
    public void setUp() {
        movieKey = new MovieKey("Inception", 2010);
        unifiedMovie = new UnifiedMovie(movieKey);
    }

    @Test
    public void testUnifiedMovieCreation() {
        assertNotNull(unifiedMovie);
        assertEquals(movieKey, unifiedMovie.getMovieKey());
    }

    @Test
    public void testAddDataSource() {
        unifiedMovie.addDataSource(DataSource.CRITIC_AGG);
        assertTrue(unifiedMovie.getDataCompleteness().contains(DataSource.CRITIC_AGG));
    }

    @Test
    public void testAddMultipleDataSources() {
        unifiedMovie.addDataSource(DataSource.CRITIC_AGG);
        unifiedMovie.addDataSource(DataSource.AUDIENCE_PULSE);
        unifiedMovie.addDataSource(DataSource.BOX_OFFICE_METRICS);
        
        assertEquals(3, unifiedMovie.getDataCompleteness().size());
        assertTrue(unifiedMovie.getDataCompleteness().contains(DataSource.CRITIC_AGG));
        assertTrue(unifiedMovie.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
        assertTrue(unifiedMovie.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testGetDataCompletenessImmutable() {
        unifiedMovie.addDataSource(DataSource.CRITIC_AGG);
        var completeness = unifiedMovie.getDataCompleteness();
        completeness.add(DataSource.AUDIENCE_PULSE);
        
        // Original should not be modified
        assertEquals(1, unifiedMovie.getDataCompleteness().size());
    }

    @Test
    public void testUnifiedMovieEquals() {
        MovieKey sameKey = new MovieKey("Inception", 2010);
        UnifiedMovie sameMovie = new UnifiedMovie(sameKey);
        
        assertTrue(unifiedMovie.equals(sameMovie));
        assertTrue(unifiedMovie.equals(unifiedMovie));
    }

    @Test
    public void testUnifiedMovieNotEquals() {
        MovieKey differentKey = new MovieKey("The Matrix", 1999);
        UnifiedMovie differentMovie = new UnifiedMovie(differentKey);
        
        assertFalse(unifiedMovie.equals(differentMovie));
    }

    @Test
    public void testUnifiedMovieHashCode() {
        MovieKey sameKey = new MovieKey("Inception", 2010);
        UnifiedMovie sameMovie = new UnifiedMovie(sameKey);
        
        assertEquals(unifiedMovie.hashCode(), sameMovie.hashCode());
    }

    @Test
    public void testUnifiedMovieToString() {
        unifiedMovie.addDataSource(DataSource.CRITIC_AGG);
        String result = unifiedMovie.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("UnifiedMovie"));
        assertTrue(result.contains("inception"));
        assertTrue(result.contains("CriticAgg"));
    }

    @Test(expected = NullPointerException.class)
    public void testUnifiedMovieWithNullMovieKey() {
        new UnifiedMovie(null);
    }
}
