package org.picapicapas.movieetl.providers.provider1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CriticAggSourceRecordTest {

    private CriticAggSourceRecord record;

    @Before
    public void setUp() {
        record = new CriticAggSourceRecord(
                "Inception",
                "2010",
                "85.5",
                "8.2",
                "150"
        );
    }

    @Test
    public void testCriticAggSourceRecordCreation() {
        assertNotNull(record);
        assertEquals("Inception", record.getMovieTitle());
        assertEquals("2010", record.getReleaseYear());
        assertEquals("85.5", record.getCriticScorePercentage());
        assertEquals("8.2", record.getTopCriticScore());
        assertEquals("150", record.getTotalCriticReviewsCounted());
    }

    @Test
    public void testCriticAggSourceRecordWithNullValues() {
        CriticAggSourceRecord nullRecord = new CriticAggSourceRecord(null, null, null, null, null);
        assertEquals(null, nullRecord.getMovieTitle());
        assertEquals(null, nullRecord.getReleaseYear());
        assertEquals(null, nullRecord.getCriticScorePercentage());
        assertEquals(null, nullRecord.getTopCriticScore());
        assertEquals(null, nullRecord.getTotalCriticReviewsCounted());
    }

    @Test
    public void testCriticAggSourceRecordWithEmptyValues() {
        CriticAggSourceRecord emptyRecord = new CriticAggSourceRecord("", "", "", "", "");
        assertEquals("", emptyRecord.getMovieTitle());
        assertEquals("", emptyRecord.getReleaseYear());
    }
}
