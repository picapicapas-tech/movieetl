package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.AudienceMetric;
import org.picapicapas.movieetl.domain.BoxOfficeDomesticMetric;
import org.picapicapas.movieetl.domain.CriticMetric;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

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
        Map<MovieKey, UnifiedMovie> expectedResults = new E2EExpectedResults().buildExpectedResults(testTimestamp);

        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders(testTimestamp);
        
        for (Provider provider : providers) {
            List<File> dataFiles = provider.getDataFiles();
            List<? extends DataExtractor<?>> extractors = provider.getExtractors();
            List<? extends DataNormalizer<?>> normalizers = provider.getNormalizers();
            
            for (int i = 0; i < dataFiles.size(); i++) {
                File dataFile = dataFiles.get(i);
                DataExtractor<?> extractor = extractors.get(i);
                DataNormalizer<?> normalizer = normalizers.get(i);
                
                processFileWithDynamicTypes(dataFile, extractor, normalizer, testTimestamp);
            }
        }

        Map<MovieKey, UnifiedMovie> actualResults = orchestrator.getResults();
        
        // Compare without using equals() - validate actual content/state instead
        assertTrue("Pipeline output should match expected unified movie database\n" + 
                   formatComparison(expectedResults, actualResults),
                   movieDatabasesMatch(expectedResults, actualResults));
    }
    
    @SuppressWarnings("unchecked")
    private void processFileWithDynamicTypes(File dataFile, 
                                                  DataExtractor<?> extractor,
                                                  DataNormalizer<?> normalizer,
                                                  LocalDateTime timestamp) throws Exception {
        orchestrator.processFile(dataFile, (DataExtractor<Object>) extractor, (DataNormalizer<Object>) normalizer, timestamp);
    }
    
    private boolean movieDatabasesMatch(Map<MovieKey, UnifiedMovie> expected, 
                                        Map<MovieKey, UnifiedMovie> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        
        for (MovieKey key : expected.keySet()) {
            if (!actual.containsKey(key)) {
                return false;
            }
            
            UnifiedMovie expMovie = expected.get(key);
            UnifiedMovie actMovie = actual.get(key);
            
            // Compare data completeness
            if (!expMovie.getDataCompleteness().equals(actMovie.getDataCompleteness())) {
                return false;
            }
            
            // Compare all metrics by content
            if (!expMovie.getCriticMetricsHistory().equals(actMovie.getCriticMetricsHistory())) {
                return false;
            }
            if (!expMovie.getAudienceMetricsHistory().equals(actMovie.getAudienceMetricsHistory())) {
                return false;
            }
            if (!expMovie.getDomesticBoxOfficeHistory().equals(actMovie.getDomesticBoxOfficeHistory())) {
                return false;
            }
            if (!expMovie.getInternationalBoxOfficeHistory().equals(actMovie.getInternationalBoxOfficeHistory())) {
                return false;
            }
            if (!expMovie.getFinancialMetricsHistory().equals(actMovie.getFinancialMetricsHistory())) {
                return false;
            }
        }
        
        return true;
    }
    
    private String formatComparison(Map<MovieKey, UnifiedMovie> expected, 
                                    Map<MovieKey, UnifiedMovie> actual) {
        return "Expected:\n" + expected + "\n\nActual:\n" + actual;
    }

}
