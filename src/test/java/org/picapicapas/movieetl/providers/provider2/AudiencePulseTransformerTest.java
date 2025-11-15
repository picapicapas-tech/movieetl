package org.picapicapas.movieetl.providers.provider2;

import org.junit.Test;
import org.picapicapas.movieetl.providers.common.DataExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class AudiencePulseTransformerTest {

    private AudiencePulseTransformer transformer = new AudiencePulseTransformer();

    @Test
    public void testTransformSingleRecord() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        record.put("title", "Inception");
        record.put("year", "2010");
        record.put("audience_average_score", 9.1);
        record.put("total_audience_ratings", 1500000);
        record.put("domestic_box_office_gross", 292576195L);
        rawRecords.add(record);

        List<AudiencePulseSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        assertEquals("2010", result.get(0).getYear());
    }

    @Test
    public void testTransformMultipleRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        Map<String, Object> record1 = new HashMap<>();
        record1.put("title", "Inception");
        record1.put("year", "2010");
        record1.put("audience_average_score", 9.1);
        record1.put("total_audience_ratings", 1500000);
        record1.put("domestic_box_office_gross", 292576195L);
        
        Map<String, Object> record2 = new HashMap<>();
        record2.put("title", "The Dark Knight");
        record2.put("year", "2008");
        record2.put("audience_average_score", 9.4);
        record2.put("total_audience_ratings", 2200000);
        record2.put("domestic_box_office_gross", 533345358L);
        
        rawRecords.add(record1);
        rawRecords.add(record2);

        List<AudiencePulseSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        assertEquals("The Dark Knight", result.get(1).getTitle());
    }

    @Test
    public void testTransformEmptyRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();

        List<AudiencePulseSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testTransformMissingTitleThrowsException() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        record.put("year", "2010");
        rawRecords.add(record);

        assertThrows(DataExtractor.DataExtractionException.class, () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformMissingYearThrowsException() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        record.put("title", "Inception");
        rawRecords.add(record);

        assertThrows(DataExtractor.DataExtractionException.class, () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformWithOptionalFields() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        record.put("title", "Parasite");
        record.put("year", "2019");
        rawRecords.add(record);

        List<AudiencePulseSourceRecord> result = transformer.transform(rawRecords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(null, result.get(0).getAudienceAverageScore());
        assertEquals(null, result.get(0).getTotalAudienceRatings());
        assertEquals(null, result.get(0).getDomesticBoxOfficeGross());
    }
}
