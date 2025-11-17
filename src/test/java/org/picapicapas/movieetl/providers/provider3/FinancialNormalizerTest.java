package org.picapicapas.movieetl.providers.provider3;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.FinancialMetric;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FinancialNormalizerTest {

    private FinancialNormalizer normalizer;
    private LocalDateTime timestamp;

    @Before
    public void setUp() {
        timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        normalizer = new FinancialNormalizer(timestamp);
    }

    @Test
    public void testNormalizeCreateNewMovie() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Inception", "2010", "160000000", "100000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
        assertEquals(Integer.valueOf(2010), result.getMovieKey().getReleaseYear());
        assertTrue(result.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testNormalizeUpdateExistingMovie() throws DataNormalizer.DataNormalizationException {
        UnifiedMovie existingMovie = new UnifiedMovie(new MovieKey("Inception", 2010));
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Inception", "2010", "160000000", "100000000");

        UnifiedMovie result = normalizer.normalize(record, existingMovie);

        assertNotNull(result);
        assertEquals(existingMovie, result);
        assertTrue(result.getDataCompleteness().contains(DataSource.BOX_OFFICE_METRICS));
    }

    @Test
    public void testNormalizeWithFinancialMetric() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Avatar", "2009", "280000000", "150000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getFinancialMetricsHistory().size());
        FinancialMetric metric = result.getFinancialMetricsHistory().get(0);
        assertEquals(Long.valueOf(280000000L), metric.getProductionBudgetUsd());
        assertEquals(Long.valueOf(150000000L), metric.getMarketingSpendUsd());
    }

    @Test
    public void testNormalizeWithNullProductionBudgetButMarketingSpendAddsMetric() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Film", "2015", null, "50000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getFinancialMetricsHistory().size());
        FinancialMetric metric = result.getFinancialMetricsHistory().get(0);
        assertNull(metric.getProductionBudgetUsd());
        assertEquals(Long.valueOf(50000000L), metric.getMarketingSpendUsd());
    }

    @Test
    public void testNormalizeWithValidBothBudgetsAddsMetric() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Expensive Film", "2022", "500000000", "300000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getFinancialMetricsHistory().size());
        FinancialMetric metric = result.getFinancialMetricsHistory().get(0);
        assertEquals(Long.valueOf(500000000L), metric.getProductionBudgetUsd());
        assertEquals(Long.valueOf(300000000L), metric.getMarketingSpendUsd());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test
    public void testNormalizeWithNullMarketingSpend() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Film", "2015", "100000000", null);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals(1, result.getFinancialMetricsHistory().size());
        FinancialMetric metric = result.getFinancialMetricsHistory().get(0);
        assertEquals(Long.valueOf(100000000L), metric.getProductionBudgetUsd());
    }

    @Test
    public void testNormalizeMissingTitleThrowsException() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                null, "2010", "160000000", "100000000");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Film name is required"));
        }
    }

    @Test
    public void testNormalizeEmptyTitleThrowsException() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "", "2010", "160000000", "100000000");

        try {
            normalizer.normalize(record, null);
            assertTrue("Expected DataNormalizationException", false);
        } catch (DataNormalizer.DataNormalizationException e) {
            assertTrue(e.getMessage().contains("Film name is required"));
        }
    }

    @Test
    public void testNormalizeInvalidYearCreatesMovieWithNullYear() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Inception", "not-a-year", "160000000", "100000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertNull(result.getMovieKey().getReleaseYear());
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
    }

    @Test
    public void testNormalizeNullYearCreatesMovieWithoutYear() throws DataNormalizer.DataNormalizationException {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Film", null, "160000000", "100000000");

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("film", result.getMovieKey().getNormalizedTitle());
    }
}
