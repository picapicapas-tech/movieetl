package org.picapicapas.movieetl.providers.provider3;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.BoxOfficeInternationalMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BoxOfficeInternationalNormalizerTest {

    private BoxOfficeInternationalNormalizer normalizer;
    private LocalDateTime timestamp;

    @Before
    public void setUp() {
        timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        normalizer = new BoxOfficeInternationalNormalizer(timestamp);
    }

    @Test
    public void testNormalizeCreateNewMovie() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Inception", "2010", "535383670");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
        assertEquals(Integer.valueOf(2010), result.getMovieKey().getReleaseYear());
        assertTrue(result.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testNormalizeUpdateExistingMovie() throws DataNormalizer.DataNormalizationException {
        UnifiedMovie existingMovie = new UnifiedMovie(new MovieKey("Inception", 2010));
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Inception", "2010", "535383670");

        UnifiedMovie result = normalizer.normalize(record, existingMovie);

        assertNotNull(result);
        assertEquals(existingMovie, result);
        assertTrue(result.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testNormalizeWithInternationalBoxOfficeMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "The Dark Knight Rises", "2012", "1080747701");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getInternationalBoxOfficeHistory().size());
        BoxOfficeInternationalMetric metric = result.getInternationalBoxOfficeHistory().get(0);
        assertEquals(Long.valueOf(1080747701L), metric.getValue());
    }

    @Test
    public void testNormalizeWithNullBoxOfficeDoesNotAddMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Film", "2015", null);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(0, result.getInternationalBoxOfficeHistory().size());
    }

    @Test
    public void testNormalizeWithZeroBoxOfficeDoesNotAddMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Film", "2015", "0");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(0, result.getInternationalBoxOfficeHistory().size());
    }

    @Test
    public void testNormalizeWithValidBoxOfficeAddsMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Avatar 2", "2022", "1900000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getInternationalBoxOfficeHistory().size());
        BoxOfficeInternationalMetric metric = result.getInternationalBoxOfficeHistory().get(0);
        assertEquals(Long.valueOf(1900000000L), metric.getValue());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test
    public void testNormalizeMissingTitleThrowsException() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                null, "2010", "535383670");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Film name is required"));
        }
    }

    @Test
    public void testNormalizeEmptyTitleThrowsException() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "", "2010", "535383670");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Film name is required"));
        }
    }

    @Test
    public void testNormalizeInvalidYearCreatesMovieWithNullYear() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Inception", "not-a-year", "535383670");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertNull(result.getMovieKey().getReleaseYear());
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
    }

    @Test
    public void testNormalizeNullYearCreatesMovieWithoutYear() throws DataNormalizer.DataNormalizationException {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Film", null, "535383670");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("film", result.getMovieKey().getNormalizedTitle());
    }
}
