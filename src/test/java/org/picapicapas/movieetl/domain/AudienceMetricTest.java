package org.picapicapas.movieetl.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudienceMetricTest {

    private static final LocalDateTime TEST_TIMESTAMP = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
    private static final String TEST_SOURCE = "AudiencePulse";

    @Test
    public void testAudienceMetricCreation() {
        AudienceMetric metric = new AudienceMetric(8.5, 1000000, TEST_SOURCE, TEST_TIMESTAMP);

        assertNotNull(metric);
        assertEquals(8.5, metric.getAverageScore(), 0.01);
        assertEquals(1000000, (int) metric.getTotalRatings());
    }

    @Test
    public void testAudienceMetricSource() {
        AudienceMetric metric = new AudienceMetric(9.0, 2000000, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(TEST_SOURCE, metric.getSource());
    }

    @Test
    public void testAudienceMetricTimestamp() {
        AudienceMetric metric = new AudienceMetric(8.7, 1500000, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(TEST_TIMESTAMP, metric.getTimestamp());
    }

    @Test
    public void testAudienceMetricWithNullAverageScore() {
        AudienceMetric metric = new AudienceMetric(null, 500000, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(null, metric.getAverageScore());
        assertEquals(500000, (int) metric.getTotalRatings());
    }

    @Test
    public void testAudienceMetricWithNullTotalRatings() {
        AudienceMetric metric = new AudienceMetric(7.5, null, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(7.5, metric.getAverageScore(), 0.01);
        assertEquals(null, metric.getTotalRatings());
    }

    @Test
    public void testAudienceMetricToString() {
        AudienceMetric metric = new AudienceMetric(8.5, 1000000, TEST_SOURCE, TEST_TIMESTAMP);
        String str = metric.toString();

        assertNotNull(str);
        assertEquals("AudienceMetric{" +
                "averageScore=8.5" +
                ", totalRatings=1000000" +
                ", source='" + TEST_SOURCE + '\'' +
                ", timestamp=" + TEST_TIMESTAMP +
                '}', str);
    }
}
