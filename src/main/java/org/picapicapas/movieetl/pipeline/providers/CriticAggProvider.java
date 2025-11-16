package org.picapicapas.movieetl.pipeline.providers;

import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.pipeline.Provider;
import org.picapicapas.movieetl.providers.common.CsvFileReader;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.provider1.CriticAggExtractor;
import org.picapicapas.movieetl.providers.provider1.CriticAggNormalizer;
import org.picapicapas.movieetl.providers.provider1.CriticAggTransformer;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CriticAggProvider implements Provider {
    
    private final File dataFile;
    private final LocalDateTime timestamp;
    
    public CriticAggProvider(LocalDateTime timestamp) {
        this("src/test/resources/CriticAgg/provider1.csv", timestamp);
    }
    
    public CriticAggProvider(String filePath, LocalDateTime timestamp) {
        this.dataFile = new File(filePath);
        this.timestamp = timestamp;
    }
    
    @Override
    public DataSource getDataSource() {
        return DataSource.CRITIC_AGG;
    }
    
    @Override
    public List<File> getDataFiles() {
        return Collections.unmodifiableList(Arrays.asList(dataFile));
    }
    
    @Override
    public <T> List<DataExtractor<T>> getExtractors() {
        @SuppressWarnings("unchecked")
        DataExtractor<T> extractor = (DataExtractor<T>) new CriticAggExtractor(
            new CsvFileReader(),
            new CriticAggTransformer()
        );
        return Collections.unmodifiableList(Arrays.asList(extractor));
    }
    
    @Override
    public <T> List<DataNormalizer<T>> getNormalizers() {
        @SuppressWarnings("unchecked")
        DataNormalizer<T> normalizer = (DataNormalizer<T>) new CriticAggNormalizer(timestamp);
        return Collections.unmodifiableList(Arrays.asList(normalizer));
    }
}
