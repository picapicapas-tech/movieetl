package org.picapicapas.movieetl.providers.provider1;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.providers.common.DataExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class CriticAggTransformerTest {

    private CriticAggTransformer transformer;

    @Before
    public void setUp() {
        transformer = new CriticAggTransformer();
    }

    @Test
    public void testTransformValidRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        Map<String, String> record1 = new HashMap<>();
        record1.put("movie_title", "Inception");
        record1.put("release_year", "2010");
        record1.put("critic_score_percentage", "85.5");
        record1.put("top_critic_score", "8.2");
        record1.put("total_critic_reviews_counted", "150");
        
        rawRecords.add(record1);

        List<CriticAggSourceRecord> result = transformer.transform(rawRecords);
        
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getMovieTitle());
        assertEquals("2010", result.get(0).getReleaseYear());
    }

    @Test
    public void testTransformEmptyRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        List<CriticAggSourceRecord> result = transformer.transform(rawRecords);
        assertEquals(0, result.size());
    }

    @Test
    public void testTransformMissingRequiredField() {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        Map<String, String> record1 = new HashMap<>();
        record1.put("release_year", "2010"); // Missing movie_title
        record1.put("critic_score_percentage", "85.5");
        
        rawRecords.add(record1);

        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> transformer.transform(rawRecords));
    }

    @Test
    public void testTransformOptionalFieldsMissing() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        Map<String, String> record1 = new HashMap<>();
        record1.put("movie_title", "Inception");
        record1.put("release_year", "2010");
        
        rawRecords.add(record1);

        List<CriticAggSourceRecord> result = transformer.transform(rawRecords);
        
        assertEquals(1, result.size());
        assertEquals(null, result.get(0).getCriticScorePercentage());
        assertEquals(null, result.get(0).getTopCriticScore());
        assertEquals(null, result.get(0).getTotalCriticReviewsCounted());
    }

    @Test
    public void testTransformMultipleRecords() throws DataExtractor.DataExtractionException {
        List<Map<String, ?>> rawRecords = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Map<String, String> record = new HashMap<>();
            record.put("movie_title", "Movie " + i);
            record.put("release_year", String.valueOf(2000 + i));
            rawRecords.add(record);
        }

        List<CriticAggSourceRecord> result = transformer.transform(rawRecords);
        assertEquals(3, result.size());
    }
}
