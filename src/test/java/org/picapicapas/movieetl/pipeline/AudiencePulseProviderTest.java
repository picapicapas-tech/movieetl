package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.pipeline.providers.AudiencePulseProvider;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AudiencePulseProviderTest {

    private Provider provider;
    private LocalDateTime testTimestamp;

    @Before
    public void setUp() {
        testTimestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
        provider = new AudiencePulseProvider(testTimestamp);
    }

    @Test
    public void testProviderReturnsAudiencePulseDataSource() {
        assertEquals(DataSource.AUDIENCE_PULSE, provider.getDataSource());
    }

    @Test
    public void testProviderReturnsCorrectDataFile() {
        File dataFile = provider.getDataFile();
        assertNotNull(dataFile);
        assertTrue(dataFile.getPath().contains("provider2.json"));
        assertTrue(dataFile.getPath().contains("AudiencePulse"));
    }

    @Test
    public void testProviderReturnsNonNullExtractor() {
        DataExtractor<?> extractor = provider.getExtractor();
        assertNotNull(extractor);
    }

    @Test
    public void testProviderReturnsNonNullNormalizer() {
        DataNormalizer<?> normalizer = provider.getNormalizer();
        assertNotNull(normalizer);
    }

    @Test
    public void testProviderWithCustomFilePath() {
        String customPath = "custom" + File.separator + "path" + File.separator + "data.json";
        Provider customProvider = new AudiencePulseProvider(customPath, testTimestamp);
        
        File dataFile = customProvider.getDataFile();
        assertEquals(customPath, dataFile.getPath());
    }

    @Test
    public void testProviderDefaultFilePathExists() {
        Provider defaultProvider = new AudiencePulseProvider(testTimestamp);
        File dataFile = defaultProvider.getDataFile();
        
        assertTrue(dataFile.exists());
    }
}
