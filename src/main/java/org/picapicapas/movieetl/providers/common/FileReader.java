package org.picapicapas.movieetl.providers.common;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileReader<T extends Map<String, ?>> {

    List<T> read(File file) throws IOException;

    default void validateFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Path is not a file: " + file.getAbsolutePath());
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException("File is not readable: " + file.getAbsolutePath());
        }
    }
}
