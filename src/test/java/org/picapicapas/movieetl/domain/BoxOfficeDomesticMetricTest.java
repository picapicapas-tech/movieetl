package org.picapicapas.movieetl.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoxOfficeDomesticMetricTest {

    @Test
    public void testBoxOfficeDomesticMetricCreation() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(292576195L), metric.getValue());
        assertEquals("BOX_OFFICE", metric.getSource());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test
    public void testBoxOfficeDomesticMetricWithZeroValue() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric = new BoxOfficeDomesticMetric(0L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(0L), metric.getValue());
    }

    @Test
    public void testBoxOfficeDomesticMetricWithLargeValue() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric = new BoxOfficeDomesticMetric(2923706994L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(2923706994L), metric.getValue());
    }

    @Test
    public void testBoxOfficeDomesticMetricEquals() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric1 = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);
        BoxOfficeDomesticMetric metric2 = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);

        assertTrue(metric1.equals(metric2));
    }

    @Test
    public void testBoxOfficeDomesticMetricNotEqualsWithDifferentValues() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric1 = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);
        BoxOfficeDomesticMetric metric2 = new BoxOfficeDomesticMetric(100000000L, "BOX_OFFICE", timestamp);

        assertFalse(metric1.equals(metric2));
    }

    @Test
    public void testBoxOfficeDomesticMetricHashCode() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric1 = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);
        BoxOfficeDomesticMetric metric2 = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);

        assertEquals(metric1.hashCode(), metric2.hashCode());
    }

    @Test
    public void testBoxOfficeDomesticMetricToString() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeDomesticMetric metric = new BoxOfficeDomesticMetric(292576195L, "BOX_OFFICE", timestamp);
        String str = metric.toString();

        assertNotNull(str);
        assertTrue(str.contains("BoxOfficeDomesticMetric"));
        assertTrue(str.contains("292576195"));
    }
}
