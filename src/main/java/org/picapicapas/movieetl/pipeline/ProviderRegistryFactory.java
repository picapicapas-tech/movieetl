package org.picapicapas.movieetl.pipeline;

import org.picapicapas.movieetl.pipeline.providers.AudiencePulseProvider;
import org.picapicapas.movieetl.pipeline.providers.BoxOfficeMetricsProvider;
import org.picapicapas.movieetl.pipeline.providers.CriticAggProvider;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ProviderRegistryFactory {
    
    private ProviderRegistryFactory() {
        // Utility class, should not be instantiated
    }
    
    public static List<Provider> getDefaultProviders() {
        LocalDateTime timestamp = LocalDateTime.now();
        return getDefaultProviders(timestamp);
    }
    
    public static List<Provider> getDefaultProviders(LocalDateTime timestamp) {
        return Arrays.asList(
            new CriticAggProvider(timestamp),
            new AudiencePulseProvider(timestamp),
            new BoxOfficeMetricsProvider(timestamp)
        );
    }
}
