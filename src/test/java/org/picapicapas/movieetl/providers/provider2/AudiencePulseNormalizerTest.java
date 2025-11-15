package org.picapicapas.movieetl.providers.provider2;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AudiencePulseNormalizerTest {

    private AudiencePulseNormalizer normalizer;
    private LocalDateTime testTimestamp;

    @Before
    public void setUp() {
        testTimestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
        normalizer = new AudiencePulseNormalizer(testTimestamp);
    }

    @Test
    public void testNormalizeCreateNewMovie() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Inception", "2010", 9.1, 1500000, 292576195L);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertEquals("inception", result.getMovieKey().getNormalizedTitle());
        assertEquals(2010, (int) result.getMovieKey().getReleaseYear());
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testNormalizeUpdateExistingMovie() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Inception", "2010", 9.1, 1500000, 292576195L);

        UnifiedMovie movie = new UnifiedMovie(new MovieKey("Inception", 2010));
        UnifiedMovie result = normalizer.normalize(record, movie);

        assertNotNull(result);
        assertEquals(movie, result);
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testNormalizeWithAudienceMetrics() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Inception", "2010", 9.1, 1500000, 292576195L);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testNormalizeWithBoxOfficeMetrics() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "The Dark Knight", "2008", 9.4, 2200000, 533345358L);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testNormalizeWithNullAudienceScore() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Parasite", "2019", null, 800000, 53369749L);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testNormalizeWithNullBoxOffice() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Parasite", "2019", 9.0, 800000, null);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testNormalizeMissingTitle() {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                null, "2010", 9.1, 1500000, 292576195L);

        try {
            normalizer.normalize(record, null);
        } catch (AudiencePulseNormalizer.DataNormalizationException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testNormalizeZeroBoxOffice() throws AudiencePulseNormalizer.DataNormalizationException {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Inception", "2010", 9.1, 1500000, 0L);

        UnifiedMovie result = normalizer.normalize(record, null);

        assertNotNull(result);
        assertTrue(result.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }
}
