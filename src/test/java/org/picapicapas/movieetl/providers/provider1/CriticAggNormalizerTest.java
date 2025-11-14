package org.picapicapas.movieetl.providers.provider1;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CriticAggNormalizerTest {

    private CriticAggNormalizer normalizer;
    private LocalDateTime timestamp;

    @Before
    public void setUp() {
        timestamp = LocalDateTime.of(2024, 1, 1, 12, 0);
        normalizer = new CriticAggNormalizer(timestamp);
    }

    @Test
    public void testNormalizeWithNewMovie() throws DataNormalizer.DataNormalizationException {
        CriticAggSourceRecord record = new CriticAggSourceRecord(
                "Inception",
                "2010",
                "85.5",
                "8.2",
                "150"
        );

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
        assertEquals(Integer.valueOf(2010), result.getMovieKey().getReleaseYear());
        assertTrue(result.getDataCompleteness().contains(DataSource.CRITIC_AGG));
    }

    @Test
    public void testNormalizeWithExistingMovie() throws DataNormalizer.DataNormalizationException {
        MovieKey key = new MovieKey("Inception", 2010);
        UnifiedMovie existingMovie = new UnifiedMovie(key);

        CriticAggSourceRecord record = new CriticAggSourceRecord(
                "Inception",
                "2010",
                "85.5",
                "8.2",
                "150"
        );

        UnifiedMovie result = normalizer.normalize(record, existingMovie);

        assertNotNull(result);
        assertEquals(existingMovie, result);
        assertTrue(result.getDataCompleteness().contains(DataSource.CRITIC_AGG));
    }

    @Test
    public void testNormalizeWithNullMetricValues() throws DataNormalizer.DataNormalizationException {
        CriticAggSourceRecord record = new CriticAggSourceRecord(
                "Inception",
                "2010",
                null,
                null,
                null
        );

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
    }

    @Test(expected = DataNormalizer.DataNormalizationException.class)
    public void testNormalizeWithMissingTitle() throws DataNormalizer.DataNormalizationException {
        CriticAggSourceRecord record = new CriticAggSourceRecord(
                null,
                "2010",
                "85.5",
                "8.2",
                "150"
        );

        normalizer.normalize(record, null);
    }

    @Test(expected = DataNormalizer.DataNormalizationException.class)
    public void testNormalizeWithEmptyTitle() throws DataNormalizer.DataNormalizationException {
        CriticAggSourceRecord record = new CriticAggSourceRecord(
                "   ",
                "2010",
                "85.5",
                "8.2",
                "150"
        );

        normalizer.normalize(record, null);
    }

    @Test(expected = DataNormalizer.DataNormalizationException.class)
    public void testNormalizeWithInvalidYear() throws DataNormalizer.DataNormalizationException {
        CriticAggSourceRecord record = new CriticAggSourceRecord(
                "Inception",
                "not-a-year",
                "85.5",
                "8.2",
                "150"
        );

        normalizer.normalize(record, null);
    }

    @Test
    public void testNormalizeWithNullYear() throws DataNormalizer.DataNormalizationException {
        CriticAggSourceRecord record = new CriticAggSourceRecord(
                "Inception",
                null,
                "85.5",
                "8.2",
                "150"
        );

        UnifiedMovie result = normalizer.normalize(record, null);
        assertNotNull(result);
        assertEquals(null, result.getMovieKey().getReleaseYear());
    }
}
