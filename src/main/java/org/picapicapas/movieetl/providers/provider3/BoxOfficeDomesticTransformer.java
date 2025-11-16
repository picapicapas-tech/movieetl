package org.picapicapas.movieetl.providers.provider3;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoxOfficeDomesticTransformer implements Transformer<BoxOfficeDomesticSourceRecord> {

    @Override
    public List<BoxOfficeDomesticSourceRecord> transform(List<? extends Map<String, ?>> rawRecords)
            throws DataExtractor.DataExtractionException {
        List<BoxOfficeDomesticSourceRecord> records = new ArrayList<>();

        for (int i = 0; i < rawRecords.size(); i++) {
            Map<String, ?> rawRecord = rawRecords.get(i);

            try {
                String filmName = getRequiredField(rawRecord, "film_name");
                String yearOfRelease = getRequiredField(rawRecord, "year_of_release");
                String boxOfficeGrossUsd = getOptionalField(rawRecord, "box_office_gross_usd");

                BoxOfficeDomesticSourceRecord record = new BoxOfficeDomesticSourceRecord(
                        filmName, yearOfRelease, boxOfficeGrossUsd);
                records.add(record);

            } catch (IllegalArgumentException e) {
                throw new DataExtractor.DataExtractionException(
                        "Error processing domestic box office row " + (i + 2) + ": " + e.getMessage(), e);
            }
        }

        return records;
    }
}
