package org.picapicapas.movieetl.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class BoxOfficeMetricValueTest {

    @Test
    public void testBoxOfficeMetricValueCreation() {
        BoxOfficeMetricValue value = new BoxOfficeMetricValue(500000000L);

        assertNotNull(value);
        assertEquals(500000000L, (long) value.getValue());
    }

    @Test
    public void testBoxOfficeMetricValueWithZero() {
        BoxOfficeMetricValue value = new BoxOfficeMetricValue(0L);

        assertEquals(0L, (long) value.getValue());
    }

    @Test
    public void testBoxOfficeMetricValueWithLargeValue() {
        long largeValue = 9999999999L;
        BoxOfficeMetricValue value = new BoxOfficeMetricValue(largeValue);

        assertEquals(largeValue, (long) value.getValue());
    }

    @Test
    public void testBoxOfficeMetricValueNullThrowsException() {
        assertThrows(NullPointerException.class, () -> new BoxOfficeMetricValue(null));
    }

    @Test
    public void testBoxOfficeMetricValueImmutability() {
        BoxOfficeMetricValue value1 = new BoxOfficeMetricValue(500000000L);
        BoxOfficeMetricValue value2 = new BoxOfficeMetricValue(500000000L);

        assertEquals(value1.getValue(), value2.getValue());
    }
}
