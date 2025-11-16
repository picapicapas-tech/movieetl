package org.picapicapas.movieetl.pipeline;

import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ProviderRegistryFactoryTest {

    @Test
    public void testGetDefaultProvidersReturnsNonEmptyList() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        assertNotNull(providers);
        assertFalse(providers.isEmpty());
    }

    @Test
    public void testGetDefaultProvidersReturnsExpectedProviderCount() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        assertEquals("Should have 3 default providers", 3, providers.size());
    }

    @Test
    public void testGetDefaultProvidersIncludesCriticAgg() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        boolean hasCriticAgg = providers.stream()
                .anyMatch(p -> p.getDataSource() == DataSource.CRITIC_AGG);
        
        assertTrue("Registry should include CriticAgg provider", hasCriticAgg);
    }

    @Test
    public void testGetDefaultProvidersIncludesAudiencePulse() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        boolean hasAudiencePulse = providers.stream()
                .anyMatch(p -> p.getDataSource() == DataSource.AUDIENCE_PULSE);
        
        assertTrue("Registry should include AudiencePulse provider", hasAudiencePulse);
    }

    @Test
    public void testGetDefaultProvidersWithTimestampReturnsNonEmptyList() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders(timestamp);
        
        assertNotNull(providers);
        assertFalse(providers.isEmpty());
    }

    @Test
    public void testGetDefaultProvidersWithTimestampReturnsExpectedProviderCount() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders(timestamp);
        
        assertEquals("Should have 3 default providers", 3, providers.size());
    }

    @Test
    public void testProvidersAreUnmodifiable() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        assertThrows("Registry should return unmodifiable list", 
                     UnsupportedOperationException.class,
                     () -> providers.add(null));
    }

    @Test
    public void testAllProvidersHaveValidDataSource() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        for (Provider provider : providers) {
            assertNotNull("Each provider should have a DataSource", provider.getDataSource());
        }
    }

    @Test
    public void testAllProvidersHaveValidDataFile() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        for (Provider provider : providers) {
            assertNotNull("Each provider should have a data file", provider.getDataFile());
        }
    }

    @Test
    public void testAllProvidersHaveValidExtractor() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        for (Provider provider : providers) {
            assertNotNull("Each provider should have an extractor", provider.getExtractor());
        }
    }

    @Test
    public void testAllProvidersHaveValidNormalizer() {
        List<Provider> providers = ProviderRegistryFactory.getDefaultProviders();
        
        for (Provider provider : providers) {
            assertNotNull("Each provider should have a normalizer", provider.getNormalizer());
        }
    }
}
