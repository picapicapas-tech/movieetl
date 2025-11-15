package org.picapicapas.movieetl.providers.provider2;

import org.picapicapas.movieetl.domain.AudienceMetric;
import org.picapicapas.movieetl.domain.BoxOfficeMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.ValidationParsers;

import java.time.LocalDateTime;

public class AudiencePulseNormalizer implements DataNormalizer<AudiencePulseSourceRecord> {
    private final LocalDateTime timestamp;

    public AudiencePulseNormalizer(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public UnifiedMovie normalize(AudiencePulseSourceRecord record, UnifiedMovie targetMovie)
            throws DataNormalizationException {
        try {
            UnifiedMovie movie = targetMovie;
            if (movie == null) {
                MovieKey key = createMovieKey(record);
                movie = new UnifiedMovie(key);
            }

            addAudienceMetrics(movie, record);
            addDomesticBoxOfficeMetrics(movie, record);
            movie.addDataSource(DataSource.AUDIENCE_PULSE);

            return movie;

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException(
                    "Failed to normalize AudiencePulse record: " + e.getMessage(), e);
        }
    }

    private void addAudienceMetrics(UnifiedMovie movie, AudiencePulseSourceRecord record) {
        Double averageScore = ValidationParsers.parseDouble(record.getAudienceAverageScore());
        Integer totalRatings = ValidationParsers.parseInt(record.getTotalAudienceRatings());

        if (averageScore != null || totalRatings != null) {
            AudienceMetric metric = new AudienceMetric(
                    averageScore,
                    totalRatings,
                    DataSource.AUDIENCE_PULSE.getDisplayName(),
                    timestamp
            );
            movie.addAudienceMetric(metric);
        }
    }

    private void addDomesticBoxOfficeMetrics(UnifiedMovie movie, AudiencePulseSourceRecord record) {
        Long boxOfficeGross = ValidationParsers.parseLong(record.getDomesticBoxOfficeGross());

        if (boxOfficeGross != null && boxOfficeGross > 0) {
            BoxOfficeMetric metric = new BoxOfficeMetric(
                    boxOfficeGross,
                    DataSource.AUDIENCE_PULSE.getDisplayName(),
                    timestamp
            );
            movie.addDomesticBoxOfficeMetric(metric);
        }
    }

    private MovieKey createMovieKey(AudiencePulseSourceRecord record) throws DataNormalizationException {
        try {
            String title = record.getTitle();
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Movie title is required");
            }
            Integer year = ValidationParsers.parseReleaseYear(record.getYear());
            return new MovieKey(title, year);

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException("Failed to create MovieKey: " + e.getMessage(), e);
        }
    }
}