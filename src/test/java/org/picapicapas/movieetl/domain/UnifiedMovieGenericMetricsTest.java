package org.picapicapas.movieetl.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class UnifiedMovieGenericMetricsTest {

    private UnifiedMovie unifiedMovie;
    private LocalDateTime testTimestamp;

    @Before
    public void setUp() {
        unifiedMovie = new UnifiedMovie(new MovieKey("Inception", 2010));
        testTimestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
    }

    @Test
    public void testAddMetricUsingGenericInterface() {
        CriticMetric criticMetric = new CriticMetric(85.5, 8.2, 150, "CriticAgg", testTimestamp);
        unifiedMovie.addMetric(criticMetric);

        List<CriticMetric> metrics = unifiedMovie.getCriticMetricsHistory();
        assertEquals(1, metrics.size());
        assertEquals(criticMetric, metrics.get(0));
    }

    @Test
    public void testGetMetricsHistoryGeneric() {
        CriticMetric metric1 = new CriticMetric(85.5, 8.2, 150, "CriticAgg", testTimestamp);
        CriticMetric metric2 = new CriticMetric(88.0, 8.5, 180, "CriticAgg", testTimestamp.plusHours(1));
        
        unifiedMovie.addMetric(metric1);
        unifiedMovie.addMetric(metric2);

        List<CriticMetric> metrics = unifiedMovie.getMetricsHistory(CriticMetric.class);
        assertEquals(2, metrics.size());
        assertEquals(metric1, metrics.get(0));
        assertEquals(metric2, metrics.get(1));
    }

    @Test
    public void testMultipleMetricTypesIndependence() {
        CriticMetric criticMetric = new CriticMetric(85.5, 8.2, 150, "CriticAgg", testTimestamp);
        AudienceMetric audienceMetric = new AudienceMetric(8.5, 1000000, "AudiencePulse", testTimestamp);

        unifiedMovie.addMetric(criticMetric);
        unifiedMovie.addMetric(audienceMetric);

        assertEquals(1, unifiedMovie.getCriticMetricsHistory().size());
        assertEquals(1, unifiedMovie.getAudienceMetricsHistory().size());
    }

    @Test
    public void testAddMetricWithNullThrowsException() {
        assertThrows(NullPointerException.class, () -> unifiedMovie.addMetric(null));
    }

    @Test
    public void testGetMetricsHistoryReturnsEmptyForUnregisteredType() {
        List<CriticMetric> metrics = unifiedMovie.getMetricsHistory(CriticMetric.class);
        assertTrue(metrics.isEmpty());
    }

    @Test
    public void testGetMetricsHistoryReturnsDefensiveCopy() {
        CriticMetric metric = new CriticMetric(85.5, 8.2, 150, "CriticAgg", testTimestamp);
        unifiedMovie.addMetric(metric);

        List<CriticMetric> metrics1 = unifiedMovie.getMetricsHistory(CriticMetric.class);
        List<CriticMetric> metrics2 = unifiedMovie.getMetricsHistory(CriticMetric.class);

        metrics1.add(null);
        assertEquals(1, metrics2.size());
    }

    @Test
    public void testAllThreeMetricTypesTogether() {
        CriticMetric criticMetric = new CriticMetric(85.5, 8.2, 150, "CriticAgg", testTimestamp);
        AudienceMetric audienceMetric = new AudienceMetric(8.5, 1000000, "AudiencePulse", testTimestamp);
        BoxOfficeDomesticMetric boxOfficeMetric = new BoxOfficeDomesticMetric(500000000L, "BoxOfficeMetrics", testTimestamp);

        unifiedMovie.addMetric(criticMetric);
        unifiedMovie.addMetric(audienceMetric);
        unifiedMovie.addMetric(boxOfficeMetric);

        assertEquals(1, unifiedMovie.getCriticMetricsHistory().size());
        assertEquals(1, unifiedMovie.getAudienceMetricsHistory().size());
        assertEquals(1, unifiedMovie.getDomesticBoxOfficeHistory().size());
    }
}
