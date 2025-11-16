package org.picapicapas.movieetl.providers.provider3;

import org.picapicapas.movieetl.domain.BoxOfficeDomesticMetric;
import org.picapicapas.movieetl.domain.BoxOfficeMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.ValidationParsers;

import java.time.LocalDateTime;

public class BoxOfficeDomesticNormalizer implements DataNormalizer<BoxOfficeDomesticSourceRecord> {
    private final LocalDateTime timestamp;

    public BoxOfficeDomesticNormalizer(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public UnifiedMovie normalize(BoxOfficeDomesticSourceRecord record, UnifiedMovie targetMovie)
            throws DataNormalizationException {
        try {
            UnifiedMovie movie = targetMovie;
            if (movie == null) {
                MovieKey key = createMovieKey(record);
                movie = new UnifiedMovie(key);
            }

            Long boxOfficeGross = ValidationParsers.parseLong(record.getBoxOfficeGrossUsd());

            if (boxOfficeGross != null && boxOfficeGross > 0) {
                BoxOfficeDomesticMetric metric = new BoxOfficeDomesticMetric(
                        boxOfficeGross,
                        DataSource.BOX_OFFICE_METRICS.getDisplayName(),
                        timestamp
                );
                movie.addDomesticBoxOfficeMetric(metric);
            }

            movie.addDataSource(DataSource.BOX_OFFICE_METRICS);

            return movie;

        } catch (IllegalArgumentException e) {
            throw new DataNormalizationException(
                    "Failed to normalize domestic box office record: " + e.getMessage(), e);
        }
    }

    private MovieKey createMovieKey(BoxOfficeDomesticSourceRecord record) throws DataNormalizationException {
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

    private Integer parseReleaseYear(String yearStr) throws DataNormalizationException {
        try {
            if (yearStr == null || yearStr.trim().isEmpty()) {
                return null;
            }
            return Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new DataNormalizationException("Release year must be a valid integer: " + yearStr, e);
        }
    }
}
