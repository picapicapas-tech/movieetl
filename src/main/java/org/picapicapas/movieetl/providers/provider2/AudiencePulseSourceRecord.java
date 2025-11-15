package org.picapicapas.movieetl.providers.provider2;

public class AudiencePulseSourceRecord {
    private final String title;
    private final String year;
    private final String audienceAverageScore;
    private final String totalAudienceRatings;
    private final String domesticBoxOfficeGross;

    public AudiencePulseSourceRecord(String title, String year, String audienceAverageScore,
                                     String totalAudienceRatings, String domesticBoxOfficeGross) {
        this.title = title;
        this.year = year;
        this.audienceAverageScore = audienceAverageScore;
        this.totalAudienceRatings = totalAudienceRatings;
        this.domesticBoxOfficeGross = domesticBoxOfficeGross;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getAudienceAverageScore() {
        return audienceAverageScore;
    }

    public String getTotalAudienceRatings() {
        return totalAudienceRatings;
    }

    public String getDomesticBoxOfficeGross() {
        return domesticBoxOfficeGross;
    }
}
