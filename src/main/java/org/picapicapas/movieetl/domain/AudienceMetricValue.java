package org.picapicapas.movieetl.domain;

public class AudienceMetricValue {
    private final Double averageScore; 
    private final Integer totalRatings;

    public AudienceMetricValue(Double averageScore, Integer totalRatings) {
        this.averageScore = averageScore;
        this.totalRatings = totalRatings;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }
}
