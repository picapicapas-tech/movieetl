package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * This test processes the complete sample data through the entire ETL pipeline
 * using the Provider Registry pattern and validates the final unified movie database.
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
    public void testEndToEndPipelineWithProviderRegistry() throws Exception {
        Map<MovieKey, UnifiedMovie> expectedResults = new ExpectedResults().buildExpectedResults(testTimestamp);

        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders(testTimestamp);
        
        for (Provider provider : providers) {
            orchestrator.processFile(
                    provider.getDataFile(),
                    provider.getExtractor(),
                    provider.getNormalizer(),
                    testTimestamp
            );
        }

        Map<MovieKey, UnifiedMovie> actualResults = orchestrator.getResults();
        assertEquals("Pipeline output should match expected unified movie database", 
                     expectedResults, actualResults);
    }

}
