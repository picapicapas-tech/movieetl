package org.picapicapas.movieetl.providers.provider3;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinancialTransformer implements Transformer<FinancialSourceRecord> {

    @Override
    public List<FinancialSourceRecord> transform(List<? extends Map<String, ?>> rawRecords)
            throws DataExtractor.DataExtractionException {
        List<FinancialSourceRecord> records = new ArrayList<>();

        for (int i = 0; i < rawRecords.size(); i++) {
            Map<String, ?> rawRecord = rawRecords.get(i);

            try {
                String filmName = getRequiredField(rawRecord, "film_name");
                String yearOfRelease = getRequiredField(rawRecord, "year_of_release");
                String productionBudgetUsd = getOptionalField(rawRecord, "production_budget_usd");
                String marketingSpendUsd = getOptionalField(rawRecord, "marketing_spend_usd");

                FinancialSourceRecord record = new FinancialSourceRecord(
                        filmName, yearOfRelease, productionBudgetUsd, marketingSpendUsd);
                records.add(record);

            } catch (IllegalArgumentException e) {
                throw new DataExtractor.DataExtractionException(
                        "Error processing financial metrics row " + (i + 2) + ": " + e.getMessage(), e);
            }
        }

        return records;
    }
}
