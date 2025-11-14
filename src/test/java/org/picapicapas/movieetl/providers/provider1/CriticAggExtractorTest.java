package org.picapicapas.movieetl.providers.provider1;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.picapicapas.movieetl.providers.common.DataExtractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CriticAggExtractorTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private CriticAggExtractor extractor;

    @Before
    public void setUp() {
        extractor = new CriticAggExtractor(
                new TestCsvFileReader(),
                new CriticAggTransformer()
        );
    }

    @Test
    public void testExtractValidFile() throws DataExtractor.DataExtractionException, IOException {
        File csvFile = tempFolder.newFile("test.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
        }

        List<CriticAggSourceRecord> result = extractor.extract(csvFile);
        
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getMovieTitle());
        assertEquals("2010", result.get(0).getReleaseYear());
    }

    @Test
    public void testExtractEmptyFile() throws DataExtractor.DataExtractionException, IOException {
        File csvFile = tempFolder.newFile("empty.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
        }

        List<CriticAggSourceRecord> result = extractor.extract(csvFile);
        assertEquals(0, result.size());
    }

    @Test
    public void testExtractMultipleRecords() throws DataExtractor.DataExtractionException, IOException {
        File csvFile = tempFolder.newFile("multi.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
            writer.write("The Matrix,1999,88.0,8.5,180\n");
            writer.write("Interstellar,2014,72.0,8.0,200\n");
        }

        List<CriticAggSourceRecord> result = extractor.extract(csvFile);
        assertEquals(3, result.size());
    }

    // Helper test implementation of FileReaderInterface
    private static class TestCsvFileReader extends org.picapicapas.movieetl.providers.common.CsvFileReader {
    }
}
