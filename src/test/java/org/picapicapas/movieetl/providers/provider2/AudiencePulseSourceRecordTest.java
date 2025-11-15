package org.picapicapas.movieetl.providers.provider2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudiencePulseSourceRecordTest {

    @Test
    public void testAudiencePulseSourceRecordCreation() {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Inception", "2010", "9.1", "1500000", "292576195");

        assertNotNull(record);
        assertEquals("Inception", record.getTitle());
        assertEquals("2010", record.getYear());
    }

    @Test
    public void testAudiencePulseSourceRecordGetters() {
        String avgScore = "9.1";
        String totalRatings = "1500000";
        String boxOffice = "292576195";
        
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "The Dark Knight", "2008", avgScore, totalRatings, boxOffice);

        assertEquals("The Dark Knight", record.getTitle());
        assertEquals("2008", record.getYear());
        assertEquals(avgScore, record.getAudienceAverageScore());
        assertEquals(totalRatings, record.getTotalAudienceRatings());
        assertEquals(boxOffice, record.getDomesticBoxOfficeGross());
    }

    @Test
    public void testAudiencePulseSourceRecordWithNullValues() {
        AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                "Parasite", "2019", null, null, null);

        assertEquals("Parasite", record.getTitle());
        assertEquals("2019", record.getYear());
        assertEquals(null, record.getAudienceAverageScore());
        assertEquals(null, record.getTotalAudienceRatings());
        assertEquals(null, record.getDomesticBoxOfficeGross());
    }
}
