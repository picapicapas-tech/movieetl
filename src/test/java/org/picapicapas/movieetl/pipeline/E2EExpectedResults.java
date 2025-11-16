package org.picapicapas.movieetl.pipeline;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.picapicapas.movieetl.domain.AudienceMetric;
import org.picapicapas.movieetl.domain.BoxOfficeDomesticMetric;
import org.picapicapas.movieetl.domain.BoxOfficeInternationalMetric;
import org.picapicapas.movieetl.domain.CriticMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.FinancialMetric;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;

public class E2EExpectedResults {
    protected  Map<MovieKey, UnifiedMovie> buildExpectedResults(LocalDateTime testTimestamp) {
        Map<MovieKey, UnifiedMovie> expected = new HashMap<>();
        
        // Movie 1: Inception (2010)
        UnifiedMovie inception = new UnifiedMovie(new MovieKey("Inception", 2010));
        inception.addDataSource(DataSource.CRITIC_AGG);
        inception.addDataSource(DataSource.AUDIENCE_PULSE);
        inception.addDataSource(DataSource.BOX_OFFICE_METRICS);
        inception.addCriticMetric(new CriticMetric(87.0, 8.1, 450, 
                                                     DataSource.CRITIC_AGG.getDisplayName(), 
                                                     testTimestamp));
        inception.addAudienceMetric(new AudienceMetric(9.1, 1500000, 
                                                     DataSource.AUDIENCE_PULSE.getDisplayName(), 
                                                     testTimestamp));
        inception.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(292576195L,  
                                                     DataSource.AUDIENCE_PULSE.getDisplayName(), 
                                                     testTimestamp));
                                                     
        inception.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(292576195L,  
                                                     DataSource.BOX_OFFICE_METRICS.getDisplayName(), 
                                                     testTimestamp));
        inception.addInternationalBoxOfficeMetric(new BoxOfficeInternationalMetric(535700000L,
                                                     DataSource.BOX_OFFICE_METRICS.getDisplayName(),
                                                     testTimestamp));
        inception.addFinancialMetric(new FinancialMetric(160000000L, 100000000L,
                                                     DataSource.BOX_OFFICE_METRICS.getDisplayName(),
                                                     testTimestamp));
        expected.put(inception.getMovieKey(), inception);
        
        // Movie 2: The Dark Knight (2008)
        UnifiedMovie darkKnight = new UnifiedMovie(new MovieKey("The Dark Knight", 2008));
        darkKnight.addDataSource(DataSource.CRITIC_AGG);
        darkKnight.addDataSource(DataSource.AUDIENCE_PULSE);
        darkKnight.addDataSource(DataSource.BOX_OFFICE_METRICS);
        darkKnight.addCriticMetric(new CriticMetric(94.0, 8.6, 350, 
                                                      DataSource.CRITIC_AGG.getDisplayName(), 
                                                      testTimestamp));
                                                      
        darkKnight.addAudienceMetric(new AudienceMetric(9.4, 2200000, 
                                                     DataSource.AUDIENCE_PULSE.getDisplayName(), 
                                                     testTimestamp));
        darkKnight.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(533345358L,  
                                                     DataSource.AUDIENCE_PULSE.getDisplayName(), 
                                                     testTimestamp));
                                                     
        darkKnight.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(533345358L,  
                                                     DataSource.BOX_OFFICE_METRICS.getDisplayName(), 
                                                     testTimestamp));
        darkKnight.addInternationalBoxOfficeMetric(new BoxOfficeInternationalMetric(469700000L,
                                                     DataSource.BOX_OFFICE_METRICS.getDisplayName(),
                                                     testTimestamp));
        darkKnight.addFinancialMetric(new FinancialMetric(185000000L, 150000000L,
                                                     DataSource.BOX_OFFICE_METRICS.getDisplayName(),
                                                     testTimestamp));
        expected.put(darkKnight.getMovieKey(), darkKnight);
        
        // Movie 3: Parasite (2019)
        UnifiedMovie parasite = new UnifiedMovie(new MovieKey("Parasite", 2019));
        parasite.addDataSource(DataSource.CRITIC_AGG);
        parasite.addDataSource(DataSource.AUDIENCE_PULSE);
        parasite.addCriticMetric(new CriticMetric(99.0, 9.5, 475, 
                                                     DataSource.CRITIC_AGG.getDisplayName(), 
                                                     testTimestamp));
        parasite.addAudienceMetric(new AudienceMetric(9.0, 800000, 
                                                     DataSource.AUDIENCE_PULSE.getDisplayName(), 
                                                     testTimestamp));
        parasite.addDomesticBoxOfficeMetric(new BoxOfficeDomesticMetric(53369749L,  
                                                     DataSource.AUDIENCE_PULSE.getDisplayName(), 
                                                     testTimestamp));
        expected.put(parasite.getMovieKey(), parasite);
        
        return expected;
    }
}
