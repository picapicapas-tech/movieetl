package org.picapicapas.movieetl.pipeline.providers;

import org.picapicapas.movieetl.pipeline.Provider;
import org.picapicapas.movieetl.domain.DataSource;
import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.DataNormalizer;
import org.picapicapas.movieetl.providers.common.CsvFileReader;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.picapicapas.movieetl.providers.provider3.BoxOfficeDomesticNormalizer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeDomesticTransformer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeInternationalNormalizer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeInternationalSourceRecord;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeInternationalTransformer;
import org.picapicapas.movieetl.providers.provider3.FinancialNormalizer;
import org.picapicapas.movieetl.providers.provider3.FinancialSourceRecord;
import org.picapicapas.movieetl.providers.provider3.FinancialTransformer;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeMetricsExtractor;
import org.picapicapas.movieetl.providers.provider3.BoxOfficeDomesticSourceRecord;

public class BoxOfficeMetricsProvider implements Provider {
    private final File domesticFile;
    private final File internationalFile;
    private final File financialFile;
    private final LocalDateTime timestamp;
    
    public BoxOfficeMetricsProvider(LocalDateTime timestamp) {
        this(
            "src/test/resources/BoxOfficeMetrics/provider3_domestic.csv",
            "src/test/resources/BoxOfficeMetrics/provider3_international.csv",
            "src/test/resources/BoxOfficeMetrics/provider3_financials.csv",
            timestamp
        );
    }
    
    public BoxOfficeMetricsProvider(
            String domesticFilePath,
            String internationalFilePath,
            String financialFilePath,
            LocalDateTime timestamp) {
        this.domesticFile = new File(domesticFilePath);
        this.internationalFile = new File(internationalFilePath);
        this.financialFile = new File(financialFilePath);
        this.timestamp = timestamp;
    }
    
    @Override
    public DataSource getDataSource() {
        return DataSource.BOX_OFFICE_METRICS;
    }
    
    @Override
    public List<File> getDataFiles() {
        return Collections.unmodifiableList(Arrays.asList(domesticFile, internationalFile, financialFile));
    }
    
    @Override
    public <T> List<DataExtractor<T>> getExtractors() {
        BoxOfficeMetricsExtractor boxOfficeExtractor = new BoxOfficeMetricsExtractor(
            new CsvFileReader(),
            new BoxOfficeDomesticTransformer(),
            new BoxOfficeInternationalTransformer(),
            new FinancialTransformer()
        );
        
        @SuppressWarnings("unchecked")
        List<DataExtractor<T>> extractors = (List<DataExtractor<T>>) (List<?>) Arrays.asList(
            createDomesticExtractor(boxOfficeExtractor),
            createInternationalExtractor(boxOfficeExtractor),
            createFinancialExtractor(boxOfficeExtractor)
        );
        
        return Collections.unmodifiableList(extractors);
    }
    
    @Override
    public <T> List<DataNormalizer<T>> getNormalizers() {
        @SuppressWarnings("unchecked")
        List<DataNormalizer<T>> normalizers = (List<DataNormalizer<T>>) (List<?>) Arrays.asList(
            new BoxOfficeDomesticNormalizer(timestamp),
            new BoxOfficeInternationalNormalizer(timestamp),
            new FinancialNormalizer(timestamp)
        );
        
        return Collections.unmodifiableList(normalizers);
    }
    
    private DataExtractor<?> createDomesticExtractor(BoxOfficeMetricsExtractor boxOfficeExtractor) {
        return new DataExtractor<BoxOfficeDomesticSourceRecord>() {
            @Override
            public List<BoxOfficeDomesticSourceRecord> extract(File file) 
                    throws DataExtractionException {
                return boxOfficeExtractor.extractDomestic(file);
            }
        };
    }
    
    private DataExtractor<?> createInternationalExtractor(BoxOfficeMetricsExtractor boxOfficeExtractor) {
        return new DataExtractor<BoxOfficeInternationalSourceRecord>() {
            @Override
            public List<BoxOfficeInternationalSourceRecord> extract(File file) 
                    throws DataExtractionException {
                return boxOfficeExtractor.extractInternational(file);
            }
        };
    }
    
    private DataExtractor<?> createFinancialExtractor(BoxOfficeMetricsExtractor boxOfficeExtractor) {
        return new DataExtractor<FinancialSourceRecord>() {
            @Override
            public List<FinancialSourceRecord> extract(File file) 
                    throws DataExtractionException {
                return boxOfficeExtractor.extractFinancial(file);
            }
        };
    }
}
