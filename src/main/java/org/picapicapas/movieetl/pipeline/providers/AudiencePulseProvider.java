package org.picapicapas.movieetl.pipeline.providers;

import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.pipeline.Provider;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.JsonFileReader;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseExtractor;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseNormalizer;
import org.picapicapas.movieetl.providers.provider2.AudiencePulseTransformer;

import java.io.File;
import java.time.LocalDateTime;

public class AudiencePulseProvider implements Provider {
    
    private final File dataFile;
    private final LocalDateTime timestamp;
    
    public AudiencePulseProvider(LocalDateTime timestamp) {
        this("src/test/resources/AudiencePulse/provider2.json", timestamp);
    }
    
    public AudiencePulseProvider(String filePath, LocalDateTime timestamp) {
        this.dataFile = new File(filePath);
        this.timestamp = timestamp;
    }
    
    @Override
    public DataSource getDataSource() {
        return DataSource.AUDIENCE_PULSE;
    }
    
    @Override
    public File getDataFile() {
        return dataFile;
    }
    
    @Override
    public <T> DataExtractor<T> getExtractor() {
        @SuppressWarnings("unchecked")
        DataExtractor<T> extractor = (DataExtractor<T>) new AudiencePulseExtractor(
            new JsonFileReader(),
            new AudiencePulseTransformer()
        );
        return extractor;
    }
    
    @Override
    public <T> DataNormalizer<T> getNormalizer() {
        @SuppressWarnings("unchecked")
        DataNormalizer<T> normalizer = (DataNormalizer<T>) new AudiencePulseNormalizer(timestamp);
        return normalizer;
    }
}
