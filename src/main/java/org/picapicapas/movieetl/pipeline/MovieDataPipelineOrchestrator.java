package org.picapicapas.movieetl.pipeline;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
/**
 * End-to-end orchestrator for the Movie Data Pipeline.
 * Coordinates extraction, normalization, and merging of movie data from
 * multiple sources.
 *
 * The orchestrator provides a clean interface for processing data files from
 * different providers.
 * It handles the flow: File → Extract → Normalize → Merge
 *
 * The pipeline is designed to be flexible:
 * - Files can arrive in any order
 * - Multiple updates to the same movie are supported
 * - Complete metric history is preserved
 */
public class MovieDataPipelineOrchestrator {
    final MovieMerger merger;

    public MovieDataPipelineOrchestrator() {
        this.merger = new MovieMerger();
    }

    public <T> void processFile(
            File dataFile,
            DataExtractor<T> extractor,
            DataNormalizer<T> normalizer,
            LocalDateTime timestamp) throws Exception {

       List<T> sourceRecords = extractor.extract(dataFile);

        for (T sourceRecord : sourceRecords) {
            UnifiedMovie tempMovie = normalizer.normalize(sourceRecord, null);

            if (tempMovie != null) {
                UnifiedMovie existingMovie = merger.getMovie(tempMovie.getMovieKey());

                if (existingMovie != null) {
                    normalizer.normalize(sourceRecord, existingMovie);
                } else {
                    merger.merge(tempMovie);
                }
            }
        }
    }

    public Map<MovieKey, UnifiedMovie> getResults() {
        return merger.getAllMovies();
    }

    public UnifiedMovie getMovie(MovieKey key) {
        return merger.getMovie(key);
    }

    public int getMovieCount() {
        return merger.getMovieCount();
    }

    public void reset() {
        merger.clear();
    }

    //Everything below this line is created to facilitate running the pipeline from main()
    public static void main(String[] args) throws Exception {
        MovieDataPipelineOrchestrator orchestrator = new MovieDataPipelineOrchestrator();

        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        for (Provider provider : providers) {
            processProvider(orchestrator, provider);
        }

        displayResults(orchestrator);
    }

    private static <T> void processFile(
            MovieDataPipelineOrchestrator orchestrator,
            File dataFile,
            DataExtractor<T> extractor,
            DataNormalizer<T> normalizer,
            String providerName) {
        if (!dataFile.exists()) {
            System.out.println("Skipping " + providerName + " - file not found: " + dataFile.getAbsolutePath());
            return;
        }
        
        try {
            orchestrator.processFile(dataFile, extractor, normalizer, LocalDateTime.now());
            System.out.println("Successfully processed " + providerName);
        } catch (Exception e) {
            System.err.println("Error processing " + providerName + ": " + e.getMessage());
        }
    }

    private static void processProvider(MovieDataPipelineOrchestrator orchestrator, Provider provider) {
        List<File> dataFiles = provider.getDataFiles();
        List<?> extractors = provider.getExtractors();
        List<?> normalizers = provider.getNormalizers();
        String providerName = provider.getDataSource().getDisplayName();
        
        for (int i = 0; i < dataFiles.size(); i++) {
            File dataFile = dataFiles.get(i);
            
            @SuppressWarnings("unchecked")
            DataExtractor<Object> extractor = (DataExtractor<Object>) extractors.get(i);
            @SuppressWarnings("unchecked")
            DataNormalizer<Object> normalizer = (DataNormalizer<Object>) normalizers.get(i);
            
            processFile(orchestrator, dataFile, extractor, normalizer, providerName);
        }
    }

    private static void displayResults(MovieDataPipelineOrchestrator orchestrator) {
        Map<MovieKey, UnifiedMovie> results = orchestrator.getResults();
        System.out.println("\n=== Unified Movie Database ===\n");
        results.values().forEach(System.out::println);
    }
}
