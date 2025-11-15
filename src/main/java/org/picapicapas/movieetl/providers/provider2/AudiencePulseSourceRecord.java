package org.picapicapas.movieetl.providers.provider2;

public class AudiencePulseSourceRecord {
    private final String title;
    private final String year;
    private final Object audienceAverageScore;
    private final Object totalAudienceRatings;
    private final Object domesticBoxOfficeGross;

    public AudiencePulseSourceRecord(String title, String year, Object audienceAverageScore,
                                     Object totalAudienceRatings, Object domesticBoxOfficeGross) {
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

    public Object getAudienceAverageScore() {
        return audienceAverageScore;
    }

    public Object getTotalAudienceRatings() {
        return totalAudienceRatings;
    }

    public Object getDomesticBoxOfficeGross() {
        return domesticBoxOfficeGross;
    }
}
