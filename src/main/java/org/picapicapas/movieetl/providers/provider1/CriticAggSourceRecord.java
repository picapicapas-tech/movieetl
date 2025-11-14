package org.picapicapas.movieetl.providers.provider1;

public class CriticAggSourceRecord {
    private final String movieTitle;
    private final String releaseYear;
    private final String criticScorePercentage;
    private final String topCriticScore;
    private final String totalCriticReviewsCounted;

    public CriticAggSourceRecord(String movieTitle, String releaseYear, String criticScorePercentage,
                                 String topCriticScore, String totalCriticReviewsCounted) {
        this.movieTitle = movieTitle;
        this.releaseYear = releaseYear;
        this.criticScorePercentage = criticScorePercentage;
        this.topCriticScore = topCriticScore;
        this.totalCriticReviewsCounted = totalCriticReviewsCounted;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getCriticScorePercentage() {
        return criticScorePercentage;
    }

    public String getTopCriticScore() {
        return topCriticScore;
    }

    public String getTotalCriticReviewsCounted() {
        return totalCriticReviewsCounted;
    }
}
