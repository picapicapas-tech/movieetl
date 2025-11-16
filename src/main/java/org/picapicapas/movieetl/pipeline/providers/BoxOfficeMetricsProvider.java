package org.picapicapas.movieetl.pipeline.providers;

import org.picapicapas.movieetl.pipeline.Provider;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.CsvFileReader;

import java.io.File;
import java.time.LocalDateTime;

import org.picapicapas.movieetl.providers.provider3.BoxOfficeDomesticNormalizer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeDomesticTransformer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeInternationalTransformer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeMetricsExtractor;
import org.picapicapas.movieetl.providers.provider3.FinancialTransformer;

public class BoxOfficeMetricsProvider implements  Provider {
    private final File dataFile;
    private final LocalDateTime timestamp;
    
    public BoxOfficeMetricsProvider(LocalDateTime timestamp) {
        //ToDo: escalable to use several files
        this("src/test/resources/BoxOfficeMetrics/provider3_domestic.csv", timestamp);
    }
    
    public BoxOfficeMetricsProvider(String filePath, LocalDateTime timestamp) {
        this.dataFile = new File(filePath);
        this.timestamp = timestamp;
    }
    
    @Override
    public DataSource getDataSource() {
        return DataSource.BOX_OFFICE_METRICS;
    }
    
    @Override
    public File getDataFile() {
        return dataFile;
    }
    
    @Override
    public <T> DataExtractor<T> getExtractor() {
        @SuppressWarnings("unchecked")
        DataExtractor<T> extractor = (DataExtractor<T>) new BoxOfficeMetricsExtractor(
            new CsvFileReader(),
            new BoxOfficeDomesticTransformer(),
            new BoxOfficeInternationalTransformer(),
            new FinancialTransformer()
        );
        return extractor;
    }
    
    @Override
    public <T> DataNormalizer<T> getNormalizer() {
        @SuppressWarnings("unchecked")
        DataNormalizer<T> normalizer = (DataNormalizer<T>) new BoxOfficeDomesticNormalizer(timestamp);
        return normalizer;
    }
    
}
