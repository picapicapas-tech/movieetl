package org.picapicapas.movieetl.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataSourceTest {

    @Test
    public void testDataSourceEnum() {
        assertEquals("CriticAgg", DataSource.CRITIC_AGG.getDisplayName());
        assertEquals("AudiencePulse", DataSource.AUDIENCE_PULSE.getDisplayName());
        assertEquals("BoxOfficeMetrics", DataSource.BOX_OFFICE_METRICS.getDisplayName());
    }

    @Test
    public void testDataSourceValues() {
        DataSource[] values = DataSource.values();
        assertEquals(3, values.length);
    }

    @Test
    public void testDataSourceValueOf() {
        DataSource source = DataSource.valueOf("CRITIC_AGG");
        assertNotNull(source);
        assertEquals(DataSource.CRITIC_AGG, source);
    }
}
