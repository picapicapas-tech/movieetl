package org.picapicapas.movieetl.providers.provider3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FinancialSourceRecordTest {

    @Test
    public void testFinancialSourceRecordCreation() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Inception", "2010", "160000000", "100000000");

        assertNotNull(record);
        assertEquals("Inception", record.getFilmName());
        assertEquals("2010", record.getYearOfRelease());
        assertEquals("160000000", record.getProductionBudgetUsd());
        assertEquals("100000000", record.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialSourceRecordWithNullOptionalFields() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Film", "2015", null, null);

        assertNotNull(record);
        assertEquals("Film", record.getFilmName());
        assertEquals("2015", record.getYearOfRelease());
        assertNull(record.getProductionBudgetUsd());
        assertNull(record.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialSourceRecordWithPartialNullOptionalFields() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Film", "2015", "100000000", null);

        assertNotNull(record);
        assertEquals("100000000", record.getProductionBudgetUsd());
        assertNull(record.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialSourceRecordWithZeroValues() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Film", "2015", "0", "0");

        assertNotNull(record);
        assertEquals("0", record.getProductionBudgetUsd());
        assertEquals("0", record.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialSourceRecordWithLargeBudgets() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "Avatar", "2009", "280000000", "150000000");

        assertNotNull(record);
        assertEquals("280000000", record.getProductionBudgetUsd());
        assertEquals("150000000", record.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialSourceRecordWithNullFilmName() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                null, "2010", "160000000", "100000000");

        assertNotNull(record);
        assertNull(record.getFilmName());
        assertEquals("2010", record.getYearOfRelease());
        assertEquals("160000000", record.getProductionBudgetUsd());
    }

    @Test
    public void testFinancialSourceRecordWithEmptyStrings() {
        FinancialSourceRecord record = new FinancialSourceRecord(
                "", "", "", "");

        assertNotNull(record);
        assertEquals("", record.getFilmName());
        assertEquals("", record.getYearOfRelease());
        assertEquals("", record.getProductionBudgetUsd());
        assertEquals("", record.getMarketingSpendUsd());
    }
}
