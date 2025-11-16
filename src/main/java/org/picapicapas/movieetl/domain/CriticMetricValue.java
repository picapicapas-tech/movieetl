package org.picapicapas.movieetl.domain;

import java.util.Objects;

public class CriticMetricValue {
    private final Double scorePercentage;
    private final Double topCriticScore; 
    private final Integer totalReviews;

    public CriticMetricValue(Double scorePercentage, Double topCriticScore, Integer totalReviews) {
        this.scorePercentage = scorePercentage;
        this.topCriticScore = topCriticScore;
        this.totalReviews = totalReviews;
    }

    public Double getScorePercentage() {
        return scorePercentage;
    }

    public Double getTopCriticScore() {
        return topCriticScore;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriticMetricValue that = (CriticMetricValue) o;
        return Objects.equals(scorePercentage, that.scorePercentage) &&
               Objects.equals(topCriticScore, that.topCriticScore) &&
               Objects.equals(totalReviews, that.totalReviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scorePercentage, topCriticScore, totalReviews);
    }
}
