package org.picapicapas.movieetl.providers.provider3;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.BoxOfficeDomesticMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BoxOfficeDomesticNormalizerTest {

    private BoxOfficeDomesticNormalizer normalizer;
    private LocalDateTime timestamp;

    @Before
    public void setUp() {
        timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        normalizer = new BoxOfficeDomesticNormalizer(timestamp);
    }

    @Test
    public void testNormalizeCreateNewMovie() throws DataNormalizer.DataNormalizationException {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Inception", "2010", "292576195");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
        assertEquals(Integer.valueOf(2010), result.getMovieKey().getReleaseYear());
        assertTrue(result.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testNormalizeUpdateExistingMovie() throws DataNormalizer.DataNormalizationException {
        UnifiedMovie existingMovie = new UnifiedMovie(new MovieKey("Inception", 2010));
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Inception", "2010", "292576195");

        UnifiedMovie result = normalizer.normalize(record, existingMovie);

        assertNotNull(result);
        assertEquals(existingMovie, result);
        assertTrue(result.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testNormalizeWithDomesticBoxOfficeMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "The Dark Knight", "2008", "533345358");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getDomesticBoxOfficeHistory().size());
        BoxOfficeDomesticMetric metric = result.getDomesticBoxOfficeHistory().get(0);
        assertEquals(Long.valueOf(533345358L), metric.getValue());
    }

    @Test
    public void testNormalizeWithNullBoxOfficeDoesNotAddMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Film", "2015", null);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(0, result.getDomesticBoxOfficeHistory().size());
    }

    @Test
    public void testNormalizeWithZeroBoxOfficeDoesNotAddMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Film", "2015", "0");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(0, result.getDomesticBoxOfficeHistory().size());
    }

    @Test
    public void testNormalizeWithValidBoxOfficeAddsMetric() throws DataNormalizer.DataNormalizationException {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Avatar", "2009", "2923706994");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getDomesticBoxOfficeHistory().size());
        BoxOfficeDomesticMetric metric = result.getDomesticBoxOfficeHistory().get(0);
        assertEquals(Long.valueOf(2923706994L), metric.getValue());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test
    public void testNormalizeMissingTitleThrowsException() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                null, "2010", "292576195");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Film name is required"));
        }
    }

    @Test
    public void testNormalizeEmptyTitleThrowsException() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "", "2010", "292576195");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Film name is required"));
        }
    }

    @Test
    public void testNormalizeInvalidYearThrowsException() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Inception", "not-a-year", "292576195");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Release year must be a valid integer"));
        }
    }

    @Test
    public void testNormalizeNullYearCreatesMovieWithoutYear() throws DataNormalizer.DataNormalizationException {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Film", null, "292576195");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("film", result.getMovieKey().getNormalizedTitle());
    }
}
