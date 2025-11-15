package org.picapicapas.movieetl.providers.provider1;

import org.picapicapas.movieetl.domain.CriticMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.ValidationParsers;

import java.time.LocalDateTime;

public class CriticAggNormalizer implements DataNormalizer<CriticAggSourceRecord> {
    private final LocalDateTime timestamp;

    public CriticAggNormalizer(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public UnifiedMovie normalize(CriticAggSourceRecord record, UnifiedMovie targetMovie) 
            throws DataNormalizationException {
        try {
            UnifiedMovie movie = targetMovie;
            if (movie == null) {
                MovieKey key = createMovieKey(record);
                movie = new UnifiedMovie(key);
            }

            Double criticScorePercentage = ValidationParsers.parseDouble(record.getCriticScorePercentage());
            Double topCriticScore = ValidationParsers.parseDouble(record.getTopCriticScore());
            Integer totalReviews = ValidationParsers.parseInt(record.getTotalCriticReviewsCounted());

            if (criticScorePercentage != null || topCriticScore != null || totalReviews != null) {
                CriticMetric metric = new CriticMetric(
                        criticScorePercentage,
                        topCriticScore,
                        totalReviews,
                        DataSource.CRITIC_AGG.getDisplayName(),
                        timestamp
                );
                movie.addCriticMetric(metric);
            }

            movie.addDataSource(DataSource.CRITIC_AGG);

            return movie;

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException(
                    "Failed to normalize CriticAgg record: " + e.getMessage(), e);
        }
    }

    private MovieKey createMovieKey(CriticAggSourceRecord record) throws DataNormalizationException {
        try {
            String title = record.getMovieTitle();
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Movie title is required");
            }
            Integer year = ValidationParsers.parseReleaseYear(record.getReleaseYear());
            return new MovieKey(title, year);

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException("Failed to create MovieKey: " + e.getMessage(), e);
        }
    }
}
