package org.picapicapas.movieetl.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class UnifiedMovie {
    private final MovieKey movieKey;
    private final Set<DataSource> dataCompleteness;
    private final Map<Class<?>, List<?>> metricsHistory;

    public UnifiedMovie(MovieKey movieKey) {
        this.movieKey = Objects.requireNonNull(movieKey, "MovieKey cannot be null");
        this.dataCompleteness = new HashSet<>();
        this.metricsHistory = new HashMap<>();
        this.metricsHistory.put(CriticMetric.class, new ArrayList<>());
        this.metricsHistory.put(AudienceMetric.class, new ArrayList<>());
        this.metricsHistory.put(BoxOfficeDomesticMetric.class, new ArrayList<>());
        this.metricsHistory.put(BoxOfficeInternationalMetric.class, new ArrayList<>());
        this.metricsHistory.put(FinancialMetric.class, new ArrayList<>());
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

    @SuppressWarnings("unchecked")
    public <T> List<T> getMetricsHistory(Class<T> metricType) {
        return new ArrayList<>((List<T>) metricsHistory.getOrDefault(metricType, new ArrayList<>()));
    }

    @SuppressWarnings("unchecked")
    public <T> void addMetric(T metric) {
        Objects.requireNonNull(metric, "Metric cannot be null");
        Class<?> metricClass = metric.getClass();
        List<Object> metrics = (List<Object>) metricsHistory.computeIfAbsent(metricClass, k -> new ArrayList<>());
        metrics.add(metric);
    }

    public List<AudienceMetric> getAudienceMetricsHistory() {
        return getMetricsHistory(AudienceMetric.class);
    }

    public List<BoxOfficeDomesticMetric> getDomesticBoxOfficeHistory() {
        return getMetricsHistory(BoxOfficeDomesticMetric.class);
    }

    public List<BoxOfficeInternationalMetric> getInternationalBoxOfficeHistory() {
        return getMetricsHistory(BoxOfficeInternationalMetric.class);
    }

    public List<CriticMetric> getCriticMetricsHistory() {
        return getMetricsHistory(CriticMetric.class);
    }

    public List<FinancialMetric> getFinancialMetricsHistory() {
        return getMetricsHistory(FinancialMetric.class);
    }

    public void addCriticMetric(CriticMetric metric) {
        addMetric(metric);
    }

    public void addAudienceMetric(AudienceMetric metric) {
        addMetric(metric);
    }

    public void addDomesticBoxOfficeMetric(BoxOfficeDomesticMetric metric) {
        addMetric(metric);
    }

    public void addInternationalBoxOfficeMetric(BoxOfficeInternationalMetric metric) {
        addMetric(metric);
    }

    public void addFinancialMetric(FinancialMetric metric) {
        addMetric(metric);
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
        for (CriticMetric metric : getCriticMetricsHistory()) {
            sb.append("    ").append(metric).append(",\n");
        }
        sb.append("  ],\n");
        
        sb.append("  audienceMetrics: [\n");
        for (AudienceMetric metric : getAudienceMetricsHistory()) {
            sb.append("    ").append(metric).append(",\n");
        }
        sb.append("  ],\n");
        
        sb.append("  domesticBoxOffice: [\n");
        for (BoxOfficeDomesticMetric metric : getDomesticBoxOfficeHistory()) {
            sb.append("    ").append(metric).append(",\n");
        }

        sb.append("  ],\n");

        sb.append("  internationalBoxOffice: [\n");
        for (BoxOfficeInternationalMetric metric : getInternationalBoxOfficeHistory()) {
            sb.append("    ").append(metric).append(",\n");
        }

        sb.append("  ],\n");

        sb.append("  financialMetrics: [\n");
        for (FinancialMetric metric : getFinancialMetricsHistory()) {
            sb.append("    ").append(metric).append(",\n");
        }
        sb.append("  ],\n");
        
        sb.append("}");
        return sb.toString();
    }
}
