package org.picapicapas.movieetl.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AudienceMetricValue that = (AudienceMetricValue) o;
        return Objects.equals(averageScore, that.averageScore) &&
               Objects.equals(totalRatings, that.totalRatings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(averageScore, totalRatings);
    }
}
