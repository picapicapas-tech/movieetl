package org.picapicapas.movieetl.providers.common;

import java.util.List;
import java.util.Map;

public interface Transformer<T> {

    List<T> transform(List<? extends Map<String, ?>> rawRecords) throws DataExtractor.DataExtractionException;

    default String getRequiredField(Map<String, ?> record, String fieldName) {
        Object value = record.get(fieldName);
        if (value == null || value.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("Required field '" + fieldName + "' is missing or empty");
        }
        return value.toString().trim();
    }

    default String getOptionalField(Map<String, ?> record, String fieldName) {
        Object value = record.get(fieldName);
        if (value == null) {
            return null;
        }
        String stringValue = value.toString().trim();
        return stringValue.isEmpty() ? null : stringValue;
    }
}
