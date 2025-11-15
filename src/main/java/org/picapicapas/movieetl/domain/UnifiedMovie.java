package org.picapicapas.movieetl.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UnifiedMovie {
    private final MovieKey movieKey;
    private final Set<DataSource> dataCompleteness;
    private final List<CriticMetric> criticMetricsHistory;
    private final List<AudienceMetric> audienceMetricsHistory;
    private final List<BoxOfficeMetric> domesticBoxOfficeHistory;


    public UnifiedMovie(MovieKey movieKey) {
        this.movieKey = Objects.requireNonNull(movieKey, "MovieKey cannot be null");
        this.dataCompleteness = new HashSet<>();
        this.criticMetricsHistory = new ArrayList<>();
        this.audienceMetricsHistory = new ArrayList<>();
        this.domesticBoxOfficeHistory = new ArrayList<>();
    }

    public MovieKey getMovieKey() {
        return movieKey;
    }

    public Set<DataSource> getDataCompleteness() {
        return new HashSet<>(dataCompleteness);
    }

    public void addDataSource(DataSource source) {
        dataCompleteness.add(source);
    }

    public List<CriticMetric> getCriticMetricsHistory() {
        return new ArrayList<>(criticMetricsHistory);
    }

    public void addCriticMetric(CriticMetric metric) {
        Objects.requireNonNull(metric, "Metric cannot be null");
        criticMetricsHistory.add(metric);
    }

    public void addAudienceMetric(AudienceMetric metric) {
        Objects.requireNonNull(metric, "Metric cannot be null");
        audienceMetricsHistory.add(metric);
    }

    public void addDomesticBoxOfficeMetric(BoxOfficeMetric metric) {
        Objects.requireNonNull(metric, "Metric cannot be null");
        domesticBoxOfficeHistory.add(metric);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnifiedMovie that = (UnifiedMovie) o;
        return Objects.equals(movieKey, that.movieKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieKey);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UnifiedMovie{\n");
        sb.append("  movieKey: \"").append(movieKey).append("\",\n");
        sb.append("  dataCompleteness: [");
        
        boolean first = true;
        for (DataSource source : dataCompleteness) {
            if (!first) sb.append(", ");
            sb.append("\"").append(source.getDisplayName()).append("\"");
            first = false;
        }
        sb.append("  ]\n");
        
        sb.append("  criticMetrics: [\n");
        for (CriticMetric metric : criticMetricsHistory) {
            sb.append("    ").append(metric).append(",\n");
        }
        sb.append("  ],\n");
        
        sb.append("  audienceMetrics: [\n");
        for (AudienceMetric metric : audienceMetricsHistory) {
            sb.append("    ").append(metric).append(",\n");
        }
        sb.append("  ],\n");
        
        sb.append("  domesticBoxOffice: [\n");
        for (BoxOfficeMetric metric : domesticBoxOfficeHistory) {
            sb.append("    ").append(metric).append(",\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
