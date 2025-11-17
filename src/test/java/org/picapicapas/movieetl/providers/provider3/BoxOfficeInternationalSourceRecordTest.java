package org.picapicapas.movieetl.providers.provider3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BoxOfficeInternationalSourceRecordTest {

    @Test
    public void testBoxOfficeInternationalSourceRecordCreation() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Inception", "2010", "535383670");

        assertNotNull(record);
        assertEquals("Inception", record.getFilmName());
        assertEquals("2010", record.getYearOfRelease());
        assertEquals("535383670", record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeInternationalSourceRecordWithNullBoxOffice() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Film", "2015", null);

        assertNotNull(record);
        assertEquals("Film", record.getFilmName());
        assertEquals("2015", record.getYearOfRelease());
        assertNull(record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeInternationalSourceRecordWithZeroBoxOffice() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Film", "2015", "0");

        assertNotNull(record);
        assertEquals("0", record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeInternationalSourceRecordWithLargeBoxOffice() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "Avatar 2", "2022", "1900000000");

        assertNotNull(record);
        assertEquals("Avatar 2", record.getFilmName());
        assertEquals("1900000000", record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeInternationalSourceRecordWithNullValues() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                null, null, null);

        assertNotNull(record);
        assertNull(record.getFilmName());
        assertNull(record.getYearOfRelease());
        assertNull(record.getBoxOfficeGrossUsd());
    }

    @Test
    public void testBoxOfficeInternationalSourceRecordWithEmptyStrings() {
        BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                "", "", "");

        assertNotNull(record);
        assertEquals("", record.getFilmName());
        assertEquals("", record.getYearOfRelease());
        assertEquals("", record.getBoxOfficeGrossUsd());
    }
}
