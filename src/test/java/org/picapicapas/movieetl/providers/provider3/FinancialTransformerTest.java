package org.picapicapas.movieetl.providers.provider3;

import org.junit.Test;
import org.picapicapas.movieetl.providers.common.DataExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class FinancialTransformerTest {

    private final FinancialTransformer transformer = new FinancialTransformer();

    @Test
    public void testTransformSingleRecord() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("year_of_release", "2010");
        record.put("production_budget_usd", "160000000");
        record.put("marketing_spend_usd", "100000000");
        rawRecords.add(record);

        List<FinancialSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
        assertEquals("2010", result.get(0).getYearOfRelease());
        assertEquals("160000000", result.get(0).getProductionBudgetUsd());
        assertEquals("100000000", result.get(0).getMarketingSpendUsd());
    }

    @Test
    public void testTransformMultipleRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        Map<String, String> record1 = new HashMap<>();
        record1.put("film_name", "Inception");
        record1.put("year_of_release", "2010");
        record1.put("production_budget_usd", "160000000");
        record1.put("marketing_spend_usd", "100000000");
        
        Map<String, String> record2 = new HashMap<>();
        record2.put("film_name", "Avatar");
        record2.put("year_of_release", "2009");
        record2.put("production_budget_usd", "280000000");
        record2.put("marketing_spend_usd", "150000000");
        
        rawRecords.add(record1);
        rawRecords.add(record2);

        List<FinancialSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
        assertEquals("Avatar", result.get(1).getFilmName());
    }

    @Test
    public void testTransformEmptyRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();

        List<FinancialSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testTransformWithOptionalFieldsNull() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Indie Film");
        record.put("year_of_release", "2020");
        rawRecords.add(record);

        List<FinancialSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Indie Film", result.get(0).getFilmName());
        assertNull(result.get(0).getProductionBudgetUsd());
        assertNull(result.get(0).getMarketingSpendUsd());
    }

    @Test
    public void testTransformWithPartialOptionalFields() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Film");
        record.put("year_of_release", "2015");
        record.put("production_budget_usd", "100000000");
        rawRecords.add(record);

        List<FinancialSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("100000000", result.get(0).getProductionBudgetUsd());
        assertNull(result.get(0).getMarketingSpendUsd());
    }

    @Test
    public void testTransformMissingFilmNameThrowsException() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("year_of_release", "2010");
        record.put("production_budget_usd", "160000000");
        rawRecords.add(record);

        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformMissingYearThrowsException() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("production_budget_usd", "160000000");
        rawRecords.add(record);

        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformWithLargeBudgetValues() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Expensive Film");
        record.put("year_of_release", "2022");
        record.put("production_budget_usd", "500000000");
        record.put("marketing_spend_usd", "300000000");
        rawRecords.add(record);

        List<FinancialSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("500000000", result.get(0).getProductionBudgetUsd());
        assertEquals("300000000", result.get(0).getMarketingSpendUsd());
    }
}
