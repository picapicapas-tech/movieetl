package org.picapicapas.movieetl.providers.provider3;

public class FinancialSourceRecord {
    private final String filmName;
    private final String yearOfRelease;
    private final String productionBudgetUsd;
    private final String marketingSpendUsd;

    public FinancialSourceRecord(String filmName, String yearOfRelease, String productionBudgetUsd,
                                 String marketingSpendUsd) {
        this.filmName = filmName;
        this.yearOfRelease = yearOfRelease;
        this.productionBudgetUsd = productionBudgetUsd;
        this.marketingSpendUsd = marketingSpendUsd;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public String getProductionBudgetUsd() {
        return productionBudgetUsd;
    }

    public String getMarketingSpendUsd() {
        return marketingSpendUsd;
    }
}
