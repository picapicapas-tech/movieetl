package org.picapicapas.movieetl.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudienceMetricValueTest {

    @Test
    public void testAudienceMetricValueCreation() {
        AudienceMetricValue value = new AudienceMetricValue(8.5, 1000000);

        assertNotNull(value);
        assertEquals(8.5, value.getAverageScore(), 0.01);
        assertEquals(1000000, (int) value.getTotalRatings());
    }

    @Test
    public void testAudienceMetricValueWithNullAverageScore() {
        AudienceMetricValue value = new AudienceMetricValue(null, 500000);

        assertEquals(null, value.getAverageScore());
        assertEquals(500000, (int) value.getTotalRatings());
    }

    @Test
    public void testAudienceMetricValueWithNullTotalRatings() {
        AudienceMetricValue value = new AudienceMetricValue(7.5, null);

        assertEquals(7.5, value.getAverageScore(), 0.01);
        assertEquals(null, value.getTotalRatings());
    }

    @Test
    public void testAudienceMetricValueWithBothNull() {
        AudienceMetricValue value = new AudienceMetricValue(null, null);

        assertEquals(null, value.getAverageScore());
        assertEquals(null, value.getTotalRatings());
    }

    @Test
    public void testAudienceMetricValueImmutability() {
        AudienceMetricValue value1 = new AudienceMetricValue(8.5, 1000000);
        AudienceMetricValue value2 = new AudienceMetricValue(8.5, 1000000);

        assertEquals(value1.getAverageScore(), value2.getAverageScore());
        assertEquals(value1.getTotalRatings(), value2.getTotalRatings());
    }
}
