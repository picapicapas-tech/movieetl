package org.picapicapas.movieetl.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MetricTest {

    private Metric metric;
    private LocalDateTime timestamp;

    @Before
    public void setUp() {
        timestamp = LocalDateTime.of(2024, 1, 1, 12, 0);
        metric = new CriticMetric(85.5, 8.2, 150, "TestSource", timestamp);
    }

    @Test
    public void testMetricCreation() {
        assertNotNull(metric);
        assertEquals("TestSource", metric.getSource());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test(expected = NullPointerException.class)
    public void testMetricWithNullSource() {
        new CriticMetric(85.5, 8.2, 150, null, timestamp);
    }

    @Test(expected = NullPointerException.class)
    public void testMetricWithNullTimestamp() {
        new CriticMetric(85.5, 8.2, 150, "TestSource", null);
    }
}
