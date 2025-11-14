package org.picapicapas.movieetl.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CriticMetricTest {

    private CriticMetric criticMetric;
    private LocalDateTime timestamp;

    @Before
    public void setUp() {
        timestamp = LocalDateTime.of(2024, 1, 1, 12, 0);
        criticMetric = new CriticMetric(85.5, 8.2, 150, "CriticAgg", timestamp);
    }

    @Test
    public void testCriticMetricCreation() {
        assertNotNull(criticMetric);
        assertEquals(Double.valueOf(85.5), criticMetric.getScorePercentage());
        assertEquals(Double.valueOf(8.2), criticMetric.getTopCriticScore());
        assertEquals(Integer.valueOf(150), criticMetric.getTotalReviews());
        assertEquals("CriticAgg", criticMetric.getSource());
        assertEquals(timestamp, criticMetric.getTimestamp());
    }

    @Test
    public void testCriticMetricWithNullValues() {
        CriticMetric metric = new CriticMetric(null, null, null, "CriticAgg", timestamp);
        assertEquals(null, metric.getScorePercentage());
        assertEquals(null, metric.getTopCriticScore());
        assertEquals(null, metric.getTotalReviews());
    }

    @Test
    public void testCriticMetricGetData() {
        CriticMetricValue value = criticMetric.getData();
        assertNotNull(value);
        assertEquals(Double.valueOf(85.5), value.getScorePercentage());
    }

    @Test
    public void testCriticMetricGetDataType() {
        assertEquals(CriticMetricValue.class, criticMetric.getDataType());
    }

    @Test
    public void testCriticMetricToString() {
        String result = criticMetric.toString();
        assertNotNull(result);
        assertTrue(result.contains("CriticMetric"));
        assertTrue(result.contains("85.5"));
    }

    private void assertTrue(boolean condition) {
        if (!condition) throw new AssertionError();
    }
}
