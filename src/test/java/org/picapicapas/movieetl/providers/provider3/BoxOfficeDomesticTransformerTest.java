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

public class BoxOfficeDomesticTransformerTest {

    private final BoxOfficeDomesticTransformer transformer = new BoxOfficeDomesticTransformer();

    @Test
    public void testTransformSingleRecord() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("year_of_release", "2010");
        record.put("box_office_gross_usd", "292576195");
        rawRecords.add(record);

        List<BoxOfficeDomesticSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
        assertEquals("2010", result.get(0).getYearOfRelease());
        assertEquals("292576195", result.get(0).getBoxOfficeGrossUsd());
    }

    @Test
    public void testTransformMultipleRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        Map<String, String> record1 = new HashMap<>();
        record1.put("film_name", "Inception");
        record1.put("year_of_release", "2010");
        record1.put("box_office_gross_usd", "292576195");
        
        Map<String, String> record2 = new HashMap<>();
        record2.put("film_name", "The Dark Knight");
        record2.put("year_of_release", "2008");
        record2.put("box_office_gross_usd", "533345358");
        
        rawRecords.add(record1);
        rawRecords.add(record2);

        List<BoxOfficeDomesticSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
        assertEquals("The Dark Knight", result.get(1).getFilmName());
    }

    @Test
    public void testTransformEmptyRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();

        List<BoxOfficeDomesticSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testTransformWithOptionalBoxOfficeNull() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Parasite");
        record.put("year_of_release", "2019");
        rawRecords.add(record);

        List<BoxOfficeDomesticSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Parasite", result.get(0).getFilmName());
        assertNull(result.get(0).getBoxOfficeGrossUsd());
    }

    @Test
    public void testTransformMissingFilmNameThrowsException() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("year_of_release", "2010");
        record.put("box_office_gross_usd", "292576195");
        rawRecords.add(record);

        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformMissingYearThrowsException() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("box_office_gross_usd", "292576195");
        rawRecords.add(record);

        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformWithLargeBoxOfficeValue() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Avatar");
        record.put("year_of_release", "2009");
        record.put("box_office_gross_usd", "2923706994");
        rawRecords.add(record);

        List<BoxOfficeDomesticSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2923706994", result.get(0).getBoxOfficeGrossUsd());
    }
}
