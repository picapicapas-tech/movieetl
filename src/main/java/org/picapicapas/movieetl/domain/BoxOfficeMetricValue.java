package org.picapicapas.movieetl.domain;

import java.util.Objects;

public class BoxOfficeMetricValue {
    private final Long value; 

    public BoxOfficeMetricValue(Long value) {
        this.value = Objects.requireNonNull(value, "Box office value cannot be null");
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoxOfficeMetricValue that = (BoxOfficeMetricValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
