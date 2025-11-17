package org.picapicapas.movieetl.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FinancialMetricValueTest {

    @Test
    public void testFinancialMetricValueCreation() {
        FinancialMetricValue value = new FinancialMetricValue(160000000L, 100000000L);

        assertNotNull(value);
        assertEquals(Long.valueOf(160000000L), value.getProductionBudgetUsd());
        assertEquals(Long.valueOf(100000000L), value.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricValueWithNullBudgets() {
        FinancialMetricValue value = new FinancialMetricValue(null, null);

        assertNotNull(value);
        assertNull(value.getProductionBudgetUsd());
        assertNull(value.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricValueWithPartialNullBudgets() {
        FinancialMetricValue value = new FinancialMetricValue(100000000L, null);

        assertNotNull(value);
        assertEquals(Long.valueOf(100000000L), value.getProductionBudgetUsd());
        assertNull(value.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricValueWithZeroBudgets() {
        FinancialMetricValue value = new FinancialMetricValue(0L, 0L);

        assertNotNull(value);
        assertEquals(Long.valueOf(0L), value.getProductionBudgetUsd());
        assertEquals(Long.valueOf(0L), value.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricValueWithLargeBudgets() {
        FinancialMetricValue value = new FinancialMetricValue(500000000L, 300000000L);

        assertNotNull(value);
        assertEquals(Long.valueOf(500000000L), value.getProductionBudgetUsd());
        assertEquals(Long.valueOf(300000000L), value.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricValueEquals() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);
        FinancialMetricValue value2 = new FinancialMetricValue(160000000L, 100000000L);

        assertTrue(value1.equals(value2));
    }

    @Test
    public void testFinancialMetricValueNotEqualsWithDifferentBudgets() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);
        FinancialMetricValue value2 = new FinancialMetricValue(200000000L, 100000000L);

        assertFalse(value1.equals(value2));
    }

    @Test
    public void testFinancialMetricValueNotEqualsWithDifferentMarketingSpend() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);
        FinancialMetricValue value2 = new FinancialMetricValue(160000000L, 150000000L);

        assertFalse(value1.equals(value2));
    }

    @Test
    public void testFinancialMetricValueHashCode() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);
        FinancialMetricValue value2 = new FinancialMetricValue(160000000L, 100000000L);

        assertEquals(value1.hashCode(), value2.hashCode());
    }

    @Test
    public void testFinancialMetricValueHashCodeDifferentWithDifferentValues() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);
        FinancialMetricValue value2 = new FinancialMetricValue(200000000L, 100000000L);

        assertNotEquals(value1.hashCode(), value2.hashCode());
    }

    @Test
    public void testFinancialMetricValueNotEqualsWithNull() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);

        assertFalse(value1.equals(null));
    }

    @Test
    public void testFinancialMetricValueNotEqualsWithDifferentClass() {
        FinancialMetricValue value1 = new FinancialMetricValue(160000000L, 100000000L);

        assertFalse(value1.equals("not a FinancialMetricValue"));
    }

    private void assertNotEquals(int a, int b) {
        assertTrue(a != b);
    }
}
