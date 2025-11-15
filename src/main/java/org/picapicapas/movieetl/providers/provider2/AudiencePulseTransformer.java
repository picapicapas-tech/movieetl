package org.picapicapas.movieetl.providers.provider2;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AudiencePulseTransformer implements Transformer<AudiencePulseSourceRecord> {
    @Override
    public List<AudiencePulseSourceRecord> transform(List<? extends Map<String, ?>> rawRecords)
            throws DataExtractor.DataExtractionException {
        List<AudiencePulseSourceRecord> records = new ArrayList<>();

        for (int i = 0; i < rawRecords.size(); i++) {
            Map<String, ?> rawRecord = rawRecords.get(i);

            try {
                String title = getRequiredField(rawRecord, "title");
                String year = getRequiredField(rawRecord, "year");
                Object audienceAverageScore = rawRecord.get("audience_average_score");
                Object totalAudienceRatings = rawRecord.get("total_audience_ratings");
                Object domesticBoxOfficeGross = rawRecord.get("domestic_box_office_gross");

                AudiencePulseSourceRecord record = new AudiencePulseSourceRecord(
                        title, year, audienceAverageScore, totalAudienceRatings, domesticBoxOfficeGross);
                records.add(record);

            } catch (IllegalArgumentException e) {
                throw new DataExtractor.DataExtractionException("Error processing record " + (i + 1) + ": " + e.getMessage(), e);
            }
        }

        return records;
    }
}
