package org.picapicapas.movieetl.pipeline;

import org.junit.Before;
import org.junit.Test;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.pipeline.providers.CriticAggProvider;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CriticAggProviderTest {

    private Provider provider;
    private LocalDateTime testTimestamp;

    @Before
    public void setUp() {
        testTimestamp = LocalDateTime.of(2025, 11, 15, 10, 30, 0);
        provider = new CriticAggProvider(testTimestamp);
    }

    @Test
    public void testProviderReturnsCriticAggDataSource() {
        assertEquals(DataSource.CRITIC_AGG, provider.getDataSource());
    }

    @Test
    public void testProviderReturnsCorrectDataFiles() {
        java.util.List<File> dataFiles = provider.getDataFiles();
        assertNotNull(dataFiles);
        assertEquals(1, dataFiles.size());
        
        File dataFile = dataFiles.get(0);
        assertTrue(dataFile.getPath().contains("provider1.csv"));
        assertTrue(dataFile.getPath().contains("CriticAgg"));
    }

    @Test
    public void testProviderReturnsNonNullExtractors() {
        java.util.List<?> extractors = provider.getExtractors();
        assertNotNull(extractors);
        assertEquals(1, extractors.size());
        assertNotNull(extractors.get(0));
    }

    @Test
    public void testProviderReturnsNonNullNormalizers() {
        java.util.List<?> normalizers = provider.getNormalizers();
        assertNotNull(normalizers);
        assertEquals(1, normalizers.size());
        assertNotNull(normalizers.get(0));
    }

    @Test
    public void testProviderWithCustomFilePath() {
        String customPath = "custom" + File.separator + "path" + File.separator + "data.csv";
        Provider customProvider = new CriticAggProvider(customPath, testTimestamp);
        
        java.util.List<File> dataFiles = customProvider.getDataFiles();
        assertEquals(customPath, dataFiles.get(0).getPath());
    }

    @Test
    public void testProviderDefaultFilePathExists() {
        Provider defaultProvider = new CriticAggProvider(testTimestamp);
        java.util.List<File> dataFiles = defaultProvider.getDataFiles();
        
        assertTrue(dataFiles.get(0).exists());
    }
}
