package org.picapicapas.movieetl.providers.provider1;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.FileReader;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CriticAggExtractor implements DataExtractor<CriticAggSourceRecord> {
    private final FileReader<? extends Map<String, ?>> fileReader;
    private final Transformer<CriticAggSourceRecord> transformer;

    public CriticAggExtractor(FileReader<? extends Map<String, ?>> fileReader, 
                              Transformer<CriticAggSourceRecord> transformer) {
        this.fileReader = fileReader;
        this.transformer = transformer;
    }

    @Override
    public List<CriticAggSourceRecord> extract(File file) throws DataExtractionException {
        try {
            List<? extends Map<String, ?>> rawRecords = fileReader.read(file);
            return transformer.transform(rawRecords);
        } catch (IOException e) {
            throw new DataExtractionException("Failed to extract CriticAgg data from file: " + file.getName(), e);
        }
    }
}
