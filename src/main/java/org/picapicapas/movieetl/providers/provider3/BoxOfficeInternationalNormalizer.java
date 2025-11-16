package org.picapicapas.movieetl.providers.provider3;

import org.picapicapas.movieetl.domain.BoxOfficeInternationalMetric;
import org.picapicapas.movieetl.domain.BoxOfficeMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.ValidationParsers;

import java.time.LocalDateTime;

public class BoxOfficeInternationalNormalizer implements DataNormalizer<BoxOfficeInternationalSourceRecord> {
    private final LocalDateTime timestamp;

    public BoxOfficeInternationalNormalizer(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public UnifiedMovie normalize(BoxOfficeInternationalSourceRecord record, UnifiedMovie targetMovie)
            throws DataNormalizationException {
        try {
            UnifiedMovie movie = targetMovie;
            if (movie == null) {
                MovieKey key = createMovieKey(record);
                movie = new UnifiedMovie(key);
            }

            Long boxOfficeGross = ValidationParsers.parseLong(record.getBoxOfficeGrossUsd());

            if (boxOfficeGross != null && boxOfficeGross > 0) {
                BoxOfficeInternationalMetric metric = new BoxOfficeInternationalMetric(
                        boxOfficeGross,
                        DataSource.BOX_OFFICE_METRICS.getDisplayName(),
                        timestamp
                );
                movie.addInternationalBoxOfficeMetric(metric);
            }

            movie.addDataSource(DataSource.BOX_OFFICE_METRICS);

            return movie;

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException(
                    "Failed to normalize international box office record: " + e.getMessage(), e);
        }
    }

    private MovieKey createMovieKey(BoxOfficeInternationalSourceRecord record) throws DataNormalizationException {
        try {
            String title = record.getFilmName();
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Film name is required");
            }
            Integer year = parseReleaseYear(record.getYearOfRelease());
            return new MovieKey(title, year);

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException("Failed to create MovieKey: " + e.getMessage(), e);
        }
    }

    private Integer parseReleaseYear(String yearStr) {
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
