package org.picapicapas.movieetl.domain;

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
}
