package org.picapicapas.movieetl.providers.provider2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.picapicapas.movieetl.providers.common.JsonFileReader;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudiencePulseExtractorTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private AudiencePulseExtractor extractor;

    @Before
    public void setUp() {
        extractor = new AudiencePulseExtractor(new JsonFileReader(), new AudiencePulseTransformer());
    }

    @Test
    public void testExtractFromValidJsonFile() throws Exception {
        File jsonFile = tempFolder.newFile("test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[{\"title\": \"Inception\", \"year\": \"2010\", \"audience_average_score\": 9.1, \"total_audience_ratings\": 1500000}]");
        }

        var result = extractor.extract(jsonFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    public void testExtractMultipleRecords() throws Exception {
        File jsonFile = tempFolder.newFile("test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[" +
                    "{\"title\": \"Inception\", \"year\": \"2010\"}," +
                    "{\"title\": \"The Dark Knight\", \"year\": \"2008\"}" +
                    "]");
        }

        var result = extractor.extract(jsonFile);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testExtractFileNotFound() {
        File nonExistentFile = new File("non_existent.json");

        try {
            extractor.extract(nonExistentFile);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }
}
