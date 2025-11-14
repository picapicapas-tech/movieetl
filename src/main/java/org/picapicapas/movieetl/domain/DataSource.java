package org.picapicapas.movieetl.domain;

public enum DataSource {
    CRITIC_AGG("CriticAgg"),
    AUDIENCE_PULSE("AudiencePulse"),
    BOX_OFFICE_METRICS("BoxOfficeMetrics");

    private final String displayName;

    DataSource(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
