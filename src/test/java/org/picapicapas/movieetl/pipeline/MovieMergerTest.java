package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MovieMergerTest {

    private MovieMerger merger;
    private MovieKey movieKey1;
    private MovieKey movieKey2;

    @Before
    public void setUp() {
        merger = new MovieMerger();
        movieKey1 = new MovieKey("Inception", 2010);
        movieKey2 = new MovieKey("The Matrix", 1999);
    }

    @Test
    public void testMovieMergerCreation() {
        assertNotNull(merger);
        assertEquals(0, merger.getMovieCount());
    }

    @Test
    public void testMergeNewMovie() {
        UnifiedMovie movie = new UnifiedMovie(movieKey1);
        movie.addDataSource(DataSource.CRITIC_AGG);

        UnifiedMovie result = merger.merge(movie);
        assertNotNull(result);
        assertEquals(movie, result);
        assertEquals(1, merger.getMovieCount());
    }

    @Test
    public void testMergeExistingMovie() {
        UnifiedMovie movie1 = new UnifiedMovie(movieKey1);
        movie1.addDataSource(DataSource.CRITIC_AGG);
        
        merger.merge(movie1);

        UnifiedMovie movie2 = new UnifiedMovie(movieKey1);
        movie2.addDataSource(DataSource.AUDIENCE_PULSE);

        UnifiedMovie result = merger.merge(movie2);
        assertEquals(movie1, result);
        assertEquals(1, merger.getMovieCount());
    }

    @Test
    public void testRegisterMovie() {
        UnifiedMovie movie = new UnifiedMovie(movieKey1);
        merger.registerMovie(movie);

        assertEquals(1, merger.getMovieCount());
        assertEquals(movie, merger.getMovie(movieKey1));
    }

    @Test
    public void testGetMovie() {
        UnifiedMovie movie = new UnifiedMovie(movieKey1);
        merger.merge(movie);

        UnifiedMovie retrieved = merger.getMovie(movieKey1);
        assertEquals(movie, retrieved);
    }

    @Test
    public void testGetMovieNonExistent() {
        UnifiedMovie retrieved = merger.getMovie(movieKey1);
        assertNull(retrieved);
    }

    @Test
    public void testGetAllMovies() {
        UnifiedMovie movie1 = new UnifiedMovie(movieKey1);
        UnifiedMovie movie2 = new UnifiedMovie(movieKey2);

        merger.merge(movie1);
        merger.merge(movie2);

        Map<MovieKey, UnifiedMovie> allMovies = merger.getAllMovies();
        assertEquals(2, allMovies.size());
        assertNotNull(allMovies.get(movieKey1));
        assertNotNull(allMovies.get(movieKey2));
    }

    @Test
    public void testClearMovies() {
        UnifiedMovie movie = new UnifiedMovie(movieKey1);
        merger.merge(movie);
        assertEquals(1, merger.getMovieCount());

        merger.clear();
        assertEquals(0, merger.getMovieCount());
    }

    @Test(expected = NullPointerException.class)
    public void testMergeNullMovie() {
        merger.merge(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullMovie() {
        merger.registerMovie(null);
    }
}
