package org.picapicapas.movieetl.providers.provider3;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class BoxOfficeMetricsExtractorTest {

    private BoxOfficeMetricsExtractor extractor;
    private TestFileReader fileReader;
    private BoxOfficeDomesticTransformer domesticTransformer;
    private BoxOfficeInternationalTransformer internationalTransformer;
    private FinancialTransformer financialTransformer;

    @Before
    public void setUp() {
        fileReader = new TestFileReader();
        domesticTransformer = new BoxOfficeDomesticTransformer();
        internationalTransformer = new BoxOfficeInternationalTransformer();
        financialTransformer = new FinancialTransformer();
        
        extractor = new BoxOfficeMetricsExtractor(
                fileReader,
                domesticTransformer,
                internationalTransformer,
                financialTransformer
        );
    }

    @Test
    public void testExtractDomesticValidFile() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("year_of_release", "2010");
        record.put("box_office_gross_usd", "292576195");
        mockData.add(record);

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<BoxOfficeDomesticSourceRecord> result = extractor.extractDomestic(testFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
    }

    @Test
    public void testExtractInternationalValidFile() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("year_of_release", "2010");
        record.put("box_office_gross_usd", "535383670");
        mockData.add(record);

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<BoxOfficeInternationalSourceRecord> result = extractor.extractInternational(testFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
    }

    @Test
    public void testExtractFinancialValidFile() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Inception");
        record.put("year_of_release", "2010");
        record.put("production_budget_usd", "160000000");
        record.put("marketing_spend_usd", "100000000");
        mockData.add(record);

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<FinancialSourceRecord> result = extractor.extractFinancial(testFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getFilmName());
    }

    @Test
    public void testExtractDomesticEmptyFile() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<BoxOfficeDomesticSourceRecord> result = extractor.extractDomestic(testFile);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testExtractInternationalEmptyFile() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<BoxOfficeInternationalSourceRecord> result = extractor.extractInternational(testFile);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testExtractFinancialEmptyFile() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<FinancialSourceRecord> result = extractor.extractFinancial(testFile);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testExtractDomesticMultipleRecords() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();
        
        Map<String, String> record1 = new HashMap<>();
        record1.put("film_name", "Film1");
        record1.put("year_of_release", "2010");
        record1.put("box_office_gross_usd", "100000000");
        
        Map<String, String> record2 = new HashMap<>();
        record2.put("film_name", "Film2");
        record2.put("year_of_release", "2011");
        record2.put("box_office_gross_usd", "200000000");
        
        mockData.add(record1);
        mockData.add(record2);

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<BoxOfficeDomesticSourceRecord> result = extractor.extractDomestic(testFile);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testExtractDomesticIOException() {
        fileReader.setThrowException(true);

        File testFile = new File("nonexistent.csv");
        
        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> extractor.extractDomestic(testFile));
    }

    @Test
    public void testExtractInternationalIOException() {
        fileReader.setThrowException(true);

        File testFile = new File("nonexistent.csv");
        
        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> extractor.extractInternational(testFile));
    }

    @Test
    public void testExtractFinancialIOException() {
        fileReader.setThrowException(true);

        File testFile = new File("nonexistent.csv");
        
        assertThrows(DataExtractor.DataExtractionException.class, 
            () -> extractor.extractFinancial(testFile));
    }

    @Test
    public void testExtractMethodDelegatesToDomesticTransformer() throws DataExtractor.DataExtractionException, IOException {
        List<Map<String, ?>> mockData = new ArrayList<>();
        Map<String, String> record = new HashMap<>();
        record.put("film_name", "Avatar");
        record.put("year_of_release", "2009");
        record.put("box_office_gross_usd", "2923706994");
        mockData.add(record);

        fileReader.setData(mockData);

        File testFile = new File("test.csv");
        List<BoxOfficeDomesticSourceRecord> result = extractor.extract(testFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Avatar", result.get(0).getFilmName());
    }

    // Test implementation of FileReader
    private static class TestFileReader implements FileReader<Map<String, ?>> {
        private List<Map<String, ?>> data = new ArrayList<>();
        private boolean throwException = false;

        public void setData(List<Map<String, ?>> data) {
            this.data = data;
        }

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        @Override
        public List<Map<String, ?>> read(File file) throws IOException {
            if (throwException) {
                throw new IOException("Test exception");
            }
            return data;
        }
    }
}

