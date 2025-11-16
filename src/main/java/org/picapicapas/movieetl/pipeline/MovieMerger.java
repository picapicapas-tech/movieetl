package org.picapicapas.movieetl.pipeline;

import org.picapicapas.movieetl.domain.AudienceMetric;
import org.picapicapas.movieetl.domain.BoxOfficeDomesticMetric;
import org.picapicapas.movieetl.domain.BoxOfficeInternationalMetric;
import org.picapicapas.movieetl.domain.BoxOfficeMetric;
import org.picapicapas.movieetl.domain.CriticMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.FinancialMetric;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MovieMerger {
    private final Map<MovieKey, UnifiedMovie> movieDatabase;

    public MovieMerger() {
        this.movieDatabase = new HashMap<>();
    }

    public UnifiedMovie merge(UnifiedMovie movie) {
        Objects.requireNonNull(movie, "Movie cannot be null");
        Objects.requireNonNull(movie.getMovieKey(), "Movie key cannot be null");

        MovieKey key = movie.getMovieKey();
        UnifiedMovie existingMovie = movieDatabase.get(key);

        if (existingMovie == null) {
            movieDatabase.put(key, movie);
            return movie;
        }

        mergeMetrics(existingMovie, movie);

        return existingMovie;
    }

    private void mergeMetrics(UnifiedMovie targetMovie, UnifiedMovie sourceMovie) {
        for (CriticMetric metric : sourceMovie.getCriticMetricsHistory()) {
            targetMovie.addCriticMetric(metric);
        }

                for (AudienceMetric metric : sourceMovie.getAudienceMetricsHistory()) {
            targetMovie.addAudienceMetric(metric);
        }

        for (BoxOfficeDomesticMetric metric : sourceMovie.getDomesticBoxOfficeHistory()) {
            targetMovie.addDomesticBoxOfficeMetric(metric);
        }

        for (BoxOfficeInternationalMetric metric : sourceMovie.getInternationalBoxOfficeHistory()) {
            targetMovie.addInternationalBoxOfficeMetric(metric);
        }

        for (FinancialMetric metric : sourceMovie.getFinancialMetricsHistory()) {
            targetMovie.addFinancialMetric(metric);
        }

        for (DataSource source : sourceMovie.getDataCompleteness()) {
            targetMovie.addDataSource(source);
        }
    }

    public void registerMovie(UnifiedMovie movie) {
        Objects.requireNonNull(movie, "Movie cannot be null");
        movieDatabase.put(movie.getMovieKey(), movie);
    }

    public UnifiedMovie getMovie(MovieKey key) {
        return movieDatabase.get(key);
    }

    public Map<MovieKey, UnifiedMovie> getAllMovies() {
        return new HashMap<>(movieDatabase);
    }

    public int getMovieCount() {
        return movieDatabase.size();
    }

    public void clear() {
        movieDatabase.clear();
    }
}
