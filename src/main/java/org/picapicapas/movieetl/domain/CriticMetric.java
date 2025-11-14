package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;

public class CriticMetric extends HistoricalMetric<CriticMetricValue> {

    public CriticMetric(Double scorePercentage, Double topCriticScore, Integer totalReviews,
                        String source, LocalDateTime timestamp) {
        super(new CriticMetricValue(scorePercentage, topCriticScore, totalReviews),
              CriticMetricValue.class, source, timestamp);
    }

    public Double getScorePercentage() {
        return getData().getScorePercentage();
    }

    public Double getTopCriticScore() {
        return getData().getTopCriticScore();
    }

    public Integer getTotalReviews() {
        return getData().getTotalReviews();
    }

    @Override
    public String toString() {
        return "CriticMetric{" +
                "scorePercentage=" + getScorePercentage() +
                ", topCriticScore=" + getTopCriticScore() +
                ", totalReviews=" + getTotalReviews() +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
