package org.picapicapas.movieetl.providers.provider3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BoxOfficeDomesticSourceRecordTest {

    @Test
    public void testBoxOfficeDomesticSourceRecordCreation() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Inception", "2010", "292576195");

        assertNotNull(record);
        assertEquals("Inception", record.getFilmName());
        assertEquals("2010", record.getYearOfRelease());
        assertEquals("292576195", record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeDomesticSourceRecordWithNullBoxOffice() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Film", "2015", null);

        assertNotNull(record);
        assertEquals("Film", record.getFilmName());
        assertEquals("2015", record.getYearOfRelease());
        assertNull(record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeDomesticSourceRecordWithZeroBoxOffice() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Film", "2015", "0");

        assertNotNull(record);
        assertEquals("0", record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeDomesticSourceRecordWithLargeBoxOffice() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "Avatar 2", "2022", "2923706994");

        assertNotNull(record);
        assertEquals("Avatar 2", record.getFilmName());
        assertEquals("2923706994", record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeDomesticSourceRecordWithNullValues() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                null, null, null);

        assertNotNull(record);
        assertNull(record.getFilmName());
        assertNull(record.getYearOfRelease());
        assertNull(record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeDomesticSourceRecordWithEmptyStrings() {
        BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                "", "", "");

        assertNotNull(record);
        assertEquals("", record.getFilmName());
        assertEquals("", record.getYearOfRelease());
        assertEquals("", record.getBoxOfficeGrossUsd());
    }
}
