package org.picapicapas.movieetl.pipeline;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.CsvFileReader;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.JsonFileReader;
import org.picapicapas.movieetl.providers.provider1.CriticAggExtractor;
import org.picapicapas.movieetl.providers.provider1.CriticAggNormalizer;
import org.picapicapas.movieetl.providers.provider1.CriticAggTransformer;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseExtractor;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseNormalizer;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseTransformer;
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

        processProvider1CriticAgg(orchestrator);
        processProvider2AudiencePulse(orchestrator);

        displayResults(orchestrator);
    }

    private static void processProvider1CriticAgg(MovieDataPipelineOrchestrator orchestrator) {
        File criticFile = new File("src/test/resources/CriticAgg/provider1.csv");
        if (criticFile.exists()) {
            try {
                orchestrator.processFile(
                        criticFile,
                        new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                        new CriticAggNormalizer(LocalDateTime.now()),
                        LocalDateTime.now());
            } catch (Exception e) {
                System.err.println("Error processing CriticAgg: " + e.getMessage());
            }
        }
    }

    private static void processProvider2AudiencePulse(MovieDataPipelineOrchestrator orchestrator) {
        File audienceFile = new File("src/test/resources/AudiencePulse/provider2.json");
        if (audienceFile.exists()) {
            try {
                orchestrator.processFile(
                        audienceFile,
                        new AudiencePulseExtractor(new JsonFileReader(), new AudiencePulseTransformer()),
                        new AudiencePulseNormalizer(LocalDateTime.now()),
                        LocalDateTime.now());
            } catch (Exception e) {
                System.err.println("Error processing AudiencePulse: " + e.getMessage());
            }
        }
    }


    private static void displayResults(MovieDataPipelineOrchestrator orchestrator) {
        Map<MovieKey, UnifiedMovie> results = orchestrator.getResults();
        System.out.println("=== Unified Movie Database ===\n");
        results.values().forEach(System.out::println);
    }

    private static Integer parseReleaseYear(String yearStr) {
        if (yearStr == null || yearStr.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(yearStr.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
