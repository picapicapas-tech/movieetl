package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;

public class BoxOfficeMetric extends HistoricalMetric<BoxOfficeMetricValue> {

    public BoxOfficeMetric(Long value, String source, LocalDateTime timestamp) {
        super(new BoxOfficeMetricValue(value),
              BoxOfficeMetricValue.class, source, timestamp);
    }

    public Long getValue() {
        return getData().getValue();
    }

    @Override
    public String toString() {
        return "BoxOfficeMetric{" +
                "value=" + getValue() +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
