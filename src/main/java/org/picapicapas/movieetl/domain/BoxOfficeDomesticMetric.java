package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;

public class BoxOfficeDomesticMetric extends BoxOfficeMetric {

    public BoxOfficeDomesticMetric(Long value, String source, LocalDateTime timestamp) {
        super(value, source, timestamp);
    }

    @Override
    public String toString() {
        return "BoxOfficeDomesticMetric{" +
                "value=" + getValue() +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
