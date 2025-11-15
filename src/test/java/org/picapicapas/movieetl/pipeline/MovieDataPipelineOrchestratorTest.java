package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.CsvFileReader;
import org.picapicapas.movieetl.providers.provider1.CriticAggExtractor;
import org.picapicapas.movieetl.providers.provider1.CriticAggNormalizer;
import org.picapicapas.movieetl.providers.provider1.CriticAggTransformer;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieDataPipelineOrchestratorTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private MovieDataPipelineOrchestrator orchestrator;

    @Before
    public void setUp() {
        orchestrator = new MovieDataPipelineOrchestrator();
    }

    @Test
    public void testOrchestratorCreation() {
        assertNotNull(orchestrator);
        assertEquals(0, orchestrator.getMovieCount());
    }

    @Test
    public void testProcessFileWithValidCsv() throws Exception {
        File csvFile = tempFolder.newFile("critics.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
        }

        orchestrator.processFile(
                csvFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(LocalDateTime.now()),
                LocalDateTime.now()
        );

        assertEquals(1, orchestrator.getMovieCount());
    }

    @Test
    public void testProcessMultipleMovies() throws Exception {
        File csvFile = tempFolder.newFile("critics.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
            writer.write("The Matrix,1999,88.0,8.5,180\n");
        }

        orchestrator.processFile(
                csvFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(LocalDateTime.now()),
                LocalDateTime.now()
        );

        assertEquals(2, orchestrator.getMovieCount());
    }

    @Test
    public void testGetResults() throws Exception {
        File csvFile = tempFolder.newFile("critics.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
        }

        orchestrator.processFile(
                csvFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(LocalDateTime.now()),
                LocalDateTime.now()
        );

        Map<MovieKey, UnifiedMovie> results = orchestrator.getResults();
        assertEquals(1, results.size());
    }

    @Test
    public void testGetMovie() throws Exception {
        File csvFile = tempFolder.newFile("critics.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
        }

        orchestrator.processFile(
                csvFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(LocalDateTime.now()),
                LocalDateTime.now()
        );

        MovieKey key = new MovieKey("Inception", 2010);
        UnifiedMovie movie = orchestrator.getMovie(key);
        assertNotNull(movie);
        assertEquals("inception", movie.getMovieKey().getNormalizedTitle());
    }

    @Test
    public void testResetOrchestrator() throws Exception {
        File csvFile = tempFolder.newFile("critics.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
            writer.write("Inception,2010,85.5,8.2,150\n");
        }

        orchestrator.processFile(
                csvFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(LocalDateTime.now()),
                LocalDateTime.now()
        );

        assertEquals(1, orchestrator.getMovieCount());
        orchestrator.reset();
        assertEquals(0, orchestrator.getMovieCount());
    }

    @Test
    public void testProcessEmptyFile() throws Exception {
        File csvFile = tempFolder.newFile("empty.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("movie_title,release_year,critic_score_percentage,top_critic_score,total_critic_reviews_counted\n");
        }

        orchestrator.processFile(
                csvFile,
                new CriticAggExtractor(new CsvFileReader(), new CriticAggTransformer()),
                new CriticAggNormalizer(LocalDateTime.now()),
                LocalDateTime.now()
        );

        assertEquals(0, orchestrator.getMovieCount());
    }
}
