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
}
