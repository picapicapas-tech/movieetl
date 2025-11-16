package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;

public class BoxOfficeInternationalMetric extends BoxOfficeMetric {

    public BoxOfficeInternationalMetric(Long value, String source, LocalDateTime timestamp) {
        super(value, source, timestamp);
    }

    @Override
    public String toString() {
        return "BoxOfficeInternationalMetric{" +
                "value=" + getValue() +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
