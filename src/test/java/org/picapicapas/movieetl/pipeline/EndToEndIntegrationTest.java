package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.CsvFileReader;
import org.picapicapas.movieetl.providers.provider1.CriticAggExtractor;
import org.picapicapas.movieetl.providers.provider1.CriticAggNormalizer;
import org.picapicapas.movieetl.providers.provider1.CriticAggTransformer;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.picapicapas.movieetl.providers.common.JsonFileReader;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseExtractor;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseNormalizer;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseTransformer;

/**
 * This test processes the complete sample data file (provider1.csv) through the entire
 * ETL pipeline and validates the final unified movie database.
 */
public class EndToEndIntegrationTest {

    private MovieDataPipelineOrchestrator orchestrator;
    private LocalDateTime testTimestamp;

    @Before
    public void setUp() {
        orchestrator = new MovieDataPipelineOrchestrator();
        testTimestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
    }

    @Test
    public void testEndToEndPipelineWithSampleData() throws Exception {
        
        File criticFile = new File("src/test/resources/CriticAgg/provider1.csv");
        File audienceFile = new File("src/test/resources/AudiencePulse/provider2.json");
        
        Map<MovieKey, UnifiedMovie> expectedResults = new ExpectedResults().buildExpectedResults(testTimestamp);

        orchestrator.processFile(
                criticFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(testTimestamp),
                testTimestamp
        );

        orchestrator.processFile(
                audienceFile,
                new AudiencePulseExtractor(new JsonFileReader(), new AudiencePulseTransformer()),
                new AudiencePulseNormalizer(testTimestamp),
                testTimestamp
        );

        Map<MovieKey, UnifiedMovie> actualResults = orchestrator.getResults();
        assertEquals("Pipeline output should match expected unified movie database", 
                     expectedResults, actualResults);
    }

    
}
