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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AudiencePulseProvider implements Provider {
    
    private static final String DEFAULT_DATA_DIR = "src/test/resources/AudiencePulse";
    
    private final File dataFile;
    private final LocalDateTime timestamp;
    
    public AudiencePulseProvider(LocalDateTime timestamp) {
        String dataDir = System.getenv(DATA_DIR_ENV_VAR);
        if (dataDir == null) {
            dataDir = DEFAULT_DATA_DIR;
        }
        
        this(dataDir + "/provider2.json", timestamp);
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
    public List<File> getDataFiles() {
        return Collections.unmodifiableList(Arrays.asList(dataFile));
    }
    
    @Override
    public <T> List<DataExtractor<T>> getExtractors() {
        @SuppressWarnings("unchecked")
        DataExtractor<T> extractor = (DataExtractor<T>) new AudiencePulseExtractor(
            new JsonFileReader(),
            new AudiencePulseTransformer()
        );
        return Collections.unmodifiableList(Arrays.asList(extractor));
    }
    
    @Override
    public <T> List<DataNormalizer<T>> getNormalizers() {
        @SuppressWarnings("unchecked")
        DataNormalizer<T> normalizer = (DataNormalizer<T>) new AudiencePulseNormalizer(timestamp);
        return Collections.unmodifiableList(Arrays.asList(normalizer));
    }
}
