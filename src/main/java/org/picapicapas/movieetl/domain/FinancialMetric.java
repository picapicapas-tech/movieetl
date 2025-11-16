package org.picapicapas.movieetl.domain;

import java.time.LocalDateTime;

public class FinancialMetric extends HistoricalMetric<FinancialMetricValue> {

    public FinancialMetric(Long productionBudgetUsd, Long marketingSpendUsd, String source, LocalDateTime timestamp) {
        super(new FinancialMetricValue(productionBudgetUsd, marketingSpendUsd),
              FinancialMetricValue.class, source, timestamp);
    }

    public Long getProductionBudgetUsd() {
        return getData().getProductionBudgetUsd();
    }

    public Long getMarketingSpendUsd() {
        return getData().getMarketingSpendUsd();
    }

    @Override
    public String toString() {
        return "FinancialMetric{" +
                "productionBudgetUsd=" + getProductionBudgetUsd() +
                ", marketingSpendUsd=" + getMarketingSpendUsd() +
                ", source='" + getSource() + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}
