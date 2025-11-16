package org.picapicapas.movieetl.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialMetricValue that = (FinancialMetricValue) o;
        return Objects.equals(productionBudgetUsd, that.productionBudgetUsd) &&
               Objects.equals(marketingSpendUsd, that.marketingSpendUsd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionBudgetUsd, marketingSpendUsd);
    }
}
