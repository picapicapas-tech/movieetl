package org.picapicapas.movieetl.domain;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UnifiedMovieExtendedTest {

    private static final String TITLE = "Inception";
    private static final Integer RELEASE_YEAR = 2010;

    @Test
    public void testAddMultipleAudienceMetrics() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));
        AudienceMetric metric1 = new AudienceMetric(8.5, 1000000, "AudiencePulse",
                java.time.LocalDateTime.now());
        AudienceMetric metric2 = new AudienceMetric(8.7, 1100000, "AudiencePulse",
                java.time.LocalDateTime.now());

        movie.addAudienceMetric(metric1);
        movie.addAudienceMetric(metric2);

        assertNotNull(movie);
    }

    @Test
    public void testAddAudienceMetricNullThrowsException() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));

        try {
            movie.addAudienceMetric(null);
        } catch (NullPointerException e) {
            assertTrue(e.getMessage().contains("cannot be null"));
        }
    }

    @Test
    public void testAddDomesticBoxOfficeMetric() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));
        BoxOfficeDomesticMetric metric = new BoxOfficeDomesticMetric(500000000L, "BoxOfficeMetrics",
                java.time.LocalDateTime.now());

        movie.addDomesticBoxOfficeMetric(metric);

        assertNotNull(movie);
    }

    @Test
    public void testAddMultipleDomesticBoxOfficeMetrics() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));
        BoxOfficeDomesticMetric metric1 = new BoxOfficeDomesticMetric(500000000L, "BoxOfficeMetrics",
                java.time.LocalDateTime.now());
        BoxOfficeDomesticMetric metric2 = new BoxOfficeDomesticMetric(750000000L, "BoxOfficeMetrics",
                java.time.LocalDateTime.now());

        movie.addDomesticBoxOfficeMetric(metric1);
        movie.addDomesticBoxOfficeMetric(metric2);

        assertNotNull(movie);
    }

    @Test
    public void testAddDomesticBoxOfficeMetricNullThrowsException() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));

        try {
            movie.addDomesticBoxOfficeMetric(null);
        } catch (NullPointerException e) {
            assertTrue(e.getMessage().contains("cannot be null"));
        }
    }

    @Test
    public void testUnifiedMovieWithAllMetricTypes() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        movie.addCriticMetric(new CriticMetric(87.0, 8.1, 450, "CriticAgg", now));
        movie.addAudienceMetric(new AudienceMetric(8.5, 1000000, "AudiencePulse", now));
        movie.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(500000000L, "BoxOfficeMetrics", now));
        movie.addInternationalBoxOfficeMetric(new BoxOfficeInternationalMetric(750000000L, "BoxOfficeMetrics", now));

        movie.addDataSource(DataSource.CRITIC_AGG);
        movie.addDataSource(DataSource.AUDIENCE_PULSE);

        assertTrue(movie.getDataCompleteness().contains(DataSource.CRITIC_AGG));
        assertTrue(movie.getDataCompleteness().contains(DataSource.AUDIENCE_PULSE));
    }

    @Test
    public void testUnifiedMovieToStringWithAllMetrics() {
        UnifiedMovie movie = new UnifiedMovie(new MovieKey(TITLE, RELEASE_YEAR));
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        movie.addCriticMetric(new CriticMetric(87.0, 8.1, 450, "CriticAgg", now));
        movie.addAudienceMetric(new AudienceMetric(8.5, 1000000, "AudiencePulse", now));
        movie.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(500000000L, "BoxOfficeMetrics", now));
        movie.addInternationalBoxOfficeMetric(new BoxOfficeInternationalMetric(750000000L, "BoxOfficeMetrics", now));

        String str = movie.toString();
        assertNotNull(str);
        assertTrue(str.contains("UnifiedMovie"));
        assertTrue(str.contains("movieKey"));
    }
}
