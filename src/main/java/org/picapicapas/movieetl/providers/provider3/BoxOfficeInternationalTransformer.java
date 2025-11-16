package org.picapicapas.movieetl.providers.provider3;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoxOfficeInternationalTransformer implements Transformer<BoxOfficeInternationalSourceRecord> {

    @Override
    public List<BoxOfficeInternationalSourceRecord> transform(List<? extends Map<String, ?>> rawRecords)
            throws DataExtractor.DataExtractionException {
        List<BoxOfficeInternationalSourceRecord> records = new ArrayList<>();

        for (int i = 0; i < rawRecords.size(); i++) {
            Map<String, ?> rawRecord = rawRecords.get(i);

            try {
                String filmName = getRequiredField(rawRecord, "film_name");
                String yearOfRelease = getRequiredField(rawRecord, "year_of_release");
                String boxOfficeGrossUsd = getOptionalField(rawRecord, "box_office_gross_usd");

                BoxOfficeInternationalSourceRecord record = new BoxOfficeInternationalSourceRecord(
                        filmName, yearOfRelease, boxOfficeGrossUsd);
                records.add(record);

            } catch (IllegalArgumentException e) {
                throw new DataExtractor.DataExtractionException(
                        "Error processing international box office row " + (i + 2) + ": " + e.getMessage(), e);
            }
        }

        return records;
    }
}
