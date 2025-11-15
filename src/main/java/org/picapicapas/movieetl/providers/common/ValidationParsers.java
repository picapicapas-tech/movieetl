package org.picapicapas.movieetl.providers.common;

import org.picapicapas.movieetl.providers.common.DataNormalizer.DataNormalizationException;

public class ValidationParsers {
    private ValidationParsers() {
        // Utility class, should not be instantiated
    }

    public static Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse as Double: " + value, e);
        }
    }

    public static Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse as Long: " + value, e);
        }
    }

    public static Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse as Integer: " + value, e);
        }
    }

    public static Integer parseReleaseYear(String yearStr) throws DataNormalizationException {
        try {
            if (yearStr == null || yearStr.trim().isEmpty()) {
                return null;
            }
            return Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new DataNormalizationException("Release year must be a valid integer: " + yearStr, e);
        }
    }
}
