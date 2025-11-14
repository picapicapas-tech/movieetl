package org.picapicapas.movieetl.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CriticMetricValueTest {

    private CriticMetricValue criticMetricValue;

    @Before
    public void setUp() {
        criticMetricValue = new CriticMetricValue(85.5, 8.2, 150);
    }

    @Test
    public void testCriticMetricValueCreation() {
        assertNotNull(criticMetricValue);
        assertEquals(Double.valueOf(85.5), criticMetricValue.getScorePercentage());
        assertEquals(Double.valueOf(8.2), criticMetricValue.getTopCriticScore());
        assertEquals(Integer.valueOf(150), criticMetricValue.getTotalReviews());
    }

    @Test
    public void testCriticMetricValueWithNullValues() {
        CriticMetricValue value = new CriticMetricValue(null, null, null);
        assertEquals(null, value.getScorePercentage());
        assertEquals(null, value.getTopCriticScore());
        assertEquals(null, value.getTotalReviews());
    }

    @Test
    public void testCriticMetricValueWithPartialValues() {
        CriticMetricValue value = new CriticMetricValue(85.5, null, 150);
        assertEquals(Double.valueOf(85.5), value.getScorePercentage());
        assertEquals(null, value.getTopCriticScore());
        assertEquals(Integer.valueOf(150), value.getTotalReviews());
    }
}
