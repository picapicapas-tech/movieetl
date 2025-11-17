package org.picapicapas.movieetl.providers.provider2;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.FileReader;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AudiencePulseExtractor implements DataExtractor<AudiencePulseSourceRecord> {
    private final FileReader<? extends Map<String, ?>> fileReader;
    private final Transformer<AudiencePulseSourceRecord> transformer;

    public AudiencePulseExtractor(FileReader<? extends Map<String, ?>> fileReader,
                                  Transformer<AudiencePulseSourceRecord> transformer) {
        this.fileReader = fileReader;
        this.transformer = transformer;
    }

    @Override
    public List<AudiencePulseSourceRecord> extract(File file) throws DataExtractionException {
        try {
            List<? extends Map<String, ?>> rawRecords = fileReader.read(file);
            return transformer.transform(rawRecords);
        } catch (IOException e) {
            throw new DataExtractionException("Failed to extract AudiencePulse data from file: " + file.getName(), e);
        }
    }
}
