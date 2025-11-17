package org.picapicapas.movieetl.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoxOfficeInternationalMetricTest {

    @Test
    public void testBoxOfficeInternationalMetricCreation() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(535383670L), metric.getValue());
        assertEquals("BOX_OFFICE", metric.getSource());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test
    public void testBoxOfficeInternationalMetricWithZeroValue() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric = new BoxOfficeInternationalMetric(0L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(0L), metric.getValue());
    }

    @Test
    public void testBoxOfficeInternationalMetricWithLargeValue() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric = new BoxOfficeInternationalMetric(1900000000L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(1900000000L), metric.getValue());
    }

    @Test
    public void testBoxOfficeInternationalMetricEquals() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric1 = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);
        BoxOfficeInternationalMetric metric2 = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);

        assertTrue(metric1.equals(metric2));
    }

    @Test
    public void testBoxOfficeInternationalMetricNotEqualsWithDifferentValues() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric1 = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);
        BoxOfficeInternationalMetric metric2 = new BoxOfficeInternationalMetric(100000000L, "BOX_OFFICE", timestamp);

        assertFalse(metric1.equals(metric2));
    }

    @Test
    public void testBoxOfficeInternationalMetricHashCode() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric1 = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);
        BoxOfficeInternationalMetric metric2 = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);

        assertEquals(metric1.hashCode(), metric2.hashCode());
    }

    @Test
    public void testBoxOfficeInternationalMetricToString() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        BoxOfficeInternationalMetric metric = new BoxOfficeInternationalMetric(535383670L, "BOX_OFFICE", timestamp);
        String str = metric.toString();

        assertNotNull(str);
        assertTrue(str.contains("BoxOfficeInternationalMetric"));
        assertTrue(str.contains("535383670"));
    }
}
