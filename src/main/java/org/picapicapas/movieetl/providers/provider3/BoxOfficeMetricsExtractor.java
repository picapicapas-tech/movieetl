package org.picapicapas.movieetl.providers.provider3;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.FileReader;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BoxOfficeMetricsExtractor implements DataExtractor<BoxOfficeDomesticSourceRecord> {
    private final FileReader<? extends Map<String, ?>> fileReader;
    private final Transformer<BoxOfficeDomesticSourceRecord> domesticTransformer;
    private final Transformer<BoxOfficeInternationalSourceRecord> internationalTransformer;
    private final Transformer<FinancialSourceRecord> financialTransformer;

    public BoxOfficeMetricsExtractor(
            FileReader<? extends Map<String, ?>> fileReader,
            Transformer<BoxOfficeDomesticSourceRecord> domesticTransformer,
            Transformer<BoxOfficeInternationalSourceRecord> internationalTransformer,
            Transformer<FinancialSourceRecord> financialTransformer) {
        this.fileReader = fileReader;
        this.domesticTransformer = domesticTransformer;
        this.internationalTransformer = internationalTransformer;
        this.financialTransformer = financialTransformer;
    }

    public List<BoxOfficeDomesticSourceRecord> extractDomestic(File file) 
            throws DataExtractor.DataExtractionException {
        try {
            List<? extends Map<String, ?>> rawRecords = fileReader.read(file);
            return domesticTransformer.transform(rawRecords);
        } catch (IOException e) {
            throw new DataExtractor.DataExtractionException(
                    "Failed to extract domestic box office data from file: " + file.getName(), e);
        }
    }

    public List<BoxOfficeInternationalSourceRecord> extractInternational(File file)
            throws DataExtractor.DataExtractionException {
        try {
            List<? extends Map<String, ?>> rawRecords = fileReader.read(file);
            return internationalTransformer.transform(rawRecords);
        } catch (IOException e) {
            throw new DataExtractor.DataExtractionException(
                    "Failed to extract international box office data from file: " + file.getName(), e);
        }
    }

    public List<FinancialSourceRecord> extractFinancial(File file)
            throws DataExtractor.DataExtractionException {
        try {
            List<? extends Map<String, ?>> rawRecords = fileReader.read(file);
            return financialTransformer.transform(rawRecords);
        } catch (IOException e) {
            throw new DataExtractor.DataExtractionException(
                    "Failed to extract financial metrics from file: " + file.getName(), e);
        }
    }

    @Override
    public List<BoxOfficeDomesticSourceRecord> extract(File file) throws DataExtractionException {
        try {
            List<? extends Map<String, ?>> rawRecords = fileReader.read(file);
            return domesticTransformer.transform(rawRecords);
        } catch (IOException e) {
            throw new DataExtractionException("Failed to extract BoxOfficeDomestic data from file: " + file.getName(), e);
        }
    }
}
