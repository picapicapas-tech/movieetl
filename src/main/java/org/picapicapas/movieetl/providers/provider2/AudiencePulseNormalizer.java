package org.picapicapas.movieetl.providers.provider2;

import org.picapicapas.movieetl.domain.AudienceMetric;
import org.picapicapas.movieetl.domain.BoxOfficeMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

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
        Double averageScore = parseDouble(record.getAudienceAverageScore());
        Integer totalRatings = parseLong(record.getTotalAudienceRatings());

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
        Long boxOfficeGross = parseBoxOfficeValue(record.getDomesticBoxOfficeGross());

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
            Integer year = parseReleaseYear(record.getYear());
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

    private Double parseDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Long) {
            long longValue = (Long) value;
            if (longValue > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int) longValue;
        }
        if (value instanceof Number) {
            long longValue = ((Number) value).longValue();
            if (longValue > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int) longValue;
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long parseBoxOfficeValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
