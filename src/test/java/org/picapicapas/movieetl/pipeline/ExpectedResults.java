package org.picapicapas.movieetl.pipeline;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.picapicapas.movieetl.domain.CriticMetric;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.domain.MovieKey;
import org.picapicapas.movieetl.domain.UnifiedMovie;

public class ExpectedResults {
    protected  Map<MovieKey, UnifiedMovie> buildExpectedResults(LocalDateTime testTimestamp) {
        Map<MovieKey, UnifiedMovie> expected = new HashMap<>();
        
        // Movie 1: Inception (2010)
        UnifiedMovie inception = new UnifiedMovie(new MovieKey("Inception", 2010));
        inception.addDataSource(DataSource.CRITIC_AGG);
        inception.addCriticMetric(new CriticMetric(87.0, 8.1, 450, 
                                                     DataSource.CRITIC_AGG.getDisplayName(), 
                                                     testTimestamp));
        expected.put(inception.getMovieKey(), inception);
        
        // Movie 2: The Dark Knight (2008)
        UnifiedMovie darkKnight = new UnifiedMovie(new MovieKey("The Dark Knight", 2008));
        darkKnight.addDataSource(DataSource.CRITIC_AGG);
        darkKnight.addCriticMetric(new CriticMetric(94.0, 8.6, 350, 
                                                      DataSource.CRITIC_AGG.getDisplayName(), 
                                                      testTimestamp));
        expected.put(darkKnight.getMovieKey(), darkKnight);
        
        // Movie 3: Parasite (2019)
        UnifiedMovie parasite = new UnifiedMovie(new MovieKey("Parasite", 2019));
        parasite.addDataSource(DataSource.CRITIC_AGG);
        parasite.addCriticMetric(new CriticMetric(99.0, 9.5, 475, 
                                                    DataSource.CRITIC_AGG.getDisplayName(), 
                                                    testTimestamp));
        expected.put(parasite.getMovieKey(), parasite);
        
        return expected;
    }
}
