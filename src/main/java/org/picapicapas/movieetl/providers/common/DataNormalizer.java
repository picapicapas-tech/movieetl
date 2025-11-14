package org.picapicapas.movieetl.providers.common;

import org.picapicapas.movieetl.domain.UnifiedMovie;

public interface DataNormalizer<T> {

    UnifiedMovie normalize(T record, UnifiedMovie targetMovie) throws DataNormalizationException;

    class DataNormalizationException extends Exception {
        public DataNormalizationException(String message) {
            super(message);
        }

        public DataNormalizationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
