package org.picapicapas.movieetl.providers.common;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvFileReader extends FileReader 
        implements FileReaderInterface<Map<String, String>> {
    
    @Override
    public List<Map<String, String>> read(File file) throws IOException {
        return readCsv(file);
    }

    public List<Map<String, String>> readCsv(File file) throws IOException {
        validateFile(file);

        List<Map<String, String>> records = new ArrayList<>();

        try (java.io.FileReader fileReader = new java.io.FileReader(file);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader)) {

            for (CSVRecord csvRecord : csvParser) {
                records.add(csvRecord.toMap());
            }
        }

        return records;
    }
}
