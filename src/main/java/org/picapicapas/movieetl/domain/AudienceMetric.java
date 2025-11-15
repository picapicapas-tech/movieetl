package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;

public class AudienceMetric extends HistoricalMetric<AudienceMetricValue> {

    public AudienceMetric(Double averageScore, Integer totalRatings, String source, LocalDateTime timestamp) {
        super(new AudienceMetricValue(averageScore, totalRatings),
              AudienceMetricValue.class, source, timestamp);
    }

    public Double getAverageScore() {
        return getData().getAverageScore();
    }

    public Integer getTotalRatings() {
        return getData().getTotalRatings();
    }

    @Override
    public String toString() {
        return "AudienceMetric{" +
                "averageScore=" + getAverageScore() +
                ", totalRatings=" + getTotalRatings() +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
