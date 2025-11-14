package org.picapicapas.movieetl.providers.common;

import java.io.File;
import java.util.List;

public interface DataExtractor<T> {

    List<T> extract(File file) throws DataExtractionException;

    

    class DataExtractionException extends Exception {
        public DataExtractionException(String message) {
            super(message);
        }

        public DataExtractionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
