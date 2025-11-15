package org.picapicapas.movieetl.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BoxOfficeMetricTest {

    private static final LocalDateTime TEST_TIMESTAMP = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
    private static final String TEST_SOURCE = "BoxOfficeMetrics";

    @Test
    public void testBoxOfficeMetricCreation() {
        BoxOfficeMetric metric = new BoxOfficeMetric(500000000L, TEST_SOURCE, TEST_TIMESTAMP);

        assertNotNull(metric);
        assertEquals(500000000L, (long) metric.getValue());
    }

    @Test
    public void testBoxOfficeMetricSource() {
        BoxOfficeMetric metric = new BoxOfficeMetric(250000000L, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(TEST_SOURCE, metric.getSource());
    }

    @Test
    public void testBoxOfficeMetricTimestamp() {
        BoxOfficeMetric metric = new BoxOfficeMetric(750000000L, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(TEST_TIMESTAMP, metric.getTimestamp());
    }

    @Test
    public void testBoxOfficeMetricWithZeroValue() {
        BoxOfficeMetric metric = new BoxOfficeMetric(0L, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(0L, (long) metric.getValue());
    }

    @Test
    public void testBoxOfficeMetricWithLargeValue() {
        long largeValue = 1234567890L;
        BoxOfficeMetric metric = new BoxOfficeMetric(largeValue, TEST_SOURCE, TEST_TIMESTAMP);

        assertEquals(largeValue, (long) metric.getValue());
    }

    @Test
    public void testBoxOfficeMetricToString() {
        BoxOfficeMetric metric = new BoxOfficeMetric(500000000L, TEST_SOURCE, TEST_TIMESTAMP);
        String str = metric.toString();

        assertNotNull(str);
        assertEquals("BoxOfficeMetric{" +
                "value=500000000" +
                ", source='" + TEST_SOURCE + '\'' +
                ", timestamp=" + TEST_TIMESTAMP +
                '}', str);
    }
}
