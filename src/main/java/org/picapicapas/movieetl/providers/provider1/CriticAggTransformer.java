package org.picapicapas.movieetl.providers.provider1;

import org.picapicapas.movieetl.providers.common.DataExtractor;
import org.picapicapas.movieetl.providers.common.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CriticAggTransformer implements Transformer<CriticAggSourceRecord> {
    @Override
    public List<CriticAggSourceRecord> transform(List<? extends Map<String, ?>> rawRecords)
            throws DataExtractor.DataExtractionException {
        List<CriticAggSourceRecord> records = new ArrayList<>();

        for (int i = 0; i < rawRecords.size(); i++) {
            Map<String, ?> rawRecord = rawRecords.get(i);

            try {
                String movieTitle = getRequiredField(rawRecord, "movie_title");
                String releaseYear = getRequiredField(rawRecord, "release_year");
                String criticScorePercentage = getOptionalField(rawRecord, "critic_score_percentage");
                String topCriticScore = getOptionalField(rawRecord, "top_critic_score");
                String totalCriticReviewsCounted = getOptionalField(rawRecord, "total_critic_reviews_counted");

                CriticAggSourceRecord record = new CriticAggSourceRecord(
                        movieTitle, releaseYear, criticScorePercentage, topCriticScore, totalCriticReviewsCounted);
                records.add(record);

            } catch (IllegalArgumentException e) {
                throw new DataExtractor.DataExtractionException("Error processing row " + (i + 2) + ": " + e.getMessage(), e);
            }
        }

        return records;
    }
}
