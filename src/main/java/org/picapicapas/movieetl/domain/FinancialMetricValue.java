package org.picapicapas.movieetl.domain;

public class FinancialMetricValue {
    private final Long productionBudgetUsd;
    private final Long marketingSpendUsd;

    public FinancialMetricValue(Long productionBudgetUsd, Long marketingSpendUsd) {
        this.productionBudgetUsd = productionBudgetUsd;
        this.marketingSpendUsd = marketingSpendUsd;
    }

    public Long getProductionBudgetUsd() {
        return productionBudgetUsd;
    }

    public Long getMarketingSpendUsd() {
        return marketingSpendUsd;
    }
}
