package org.picapicapas.movieetl.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FinancialMetricTest {

    @Test
    public void testFinancialMetricCreation() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(160000000L), metric.getProductionBudgetUsd());
        assertEquals(Long.valueOf(100000000L), metric.getMarketingSpendUsd());
        assertEquals("BOX_OFFICE", metric.getSource());
        assertEquals(timestamp, metric.getTimestamp());
    }

    @Test
    public void testFinancialMetricWithNullBudgets() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(null, null, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertNull(metric.getProductionBudgetUsd());
        assertNull(metric.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricWithPartialNullBudgets() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(100000000L, null, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(100000000L), metric.getProductionBudgetUsd());
        assertNull(metric.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricWithZeroBudgets() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(0L, 0L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(0L), metric.getProductionBudgetUsd());
        assertEquals(Long.valueOf(0L), metric.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricWithLargeBudgets() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(500000000L, 300000000L, "BOX_OFFICE", timestamp);

        assertNotNull(metric);
        assertEquals(Long.valueOf(500000000L), metric.getProductionBudgetUsd());
        assertEquals(Long.valueOf(300000000L), metric.getMarketingSpendUsd());
    }

    @Test
    public void testFinancialMetricEquals() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric1 = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);
        FinancialMetric metric2 = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);

        assertTrue(metric1.equals(metric2));
    }

    @Test
    public void testFinancialMetricNotEqualsWithDifferentBudgets() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric1 = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);
        FinancialMetric metric2 = new FinancialMetric(200000000L, 100000000L, "BOX_OFFICE", timestamp);

        assertFalse(metric1.equals(metric2));
    }

    @Test
    public void testFinancialMetricHashCode() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric1 = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);
        FinancialMetric metric2 = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);

        assertEquals(metric1.hashCode(), metric2.hashCode());
    }

    @Test
    public void testFinancialMetricToString() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);
        String str = metric.toString();

        assertNotNull(str);
        assertTrue(str.contains("FinancialMetric"));
        assertTrue(str.contains("160000000"));
        assertTrue(str.contains("100000000"));
    }

    @Test
    public void testFinancialMetricGetDataType() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30);
        FinancialMetric metric = new FinancialMetric(160000000L, 100000000L, "BOX_OFFICE", timestamp);
        
        assertNotNull(metric.getDataType());
        assertEquals(FinancialMetricValue.class, metric.getDataType());
    }
}
