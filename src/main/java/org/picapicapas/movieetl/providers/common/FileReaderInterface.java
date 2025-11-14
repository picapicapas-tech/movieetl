package org.picapicapas.movieetl.providers.common;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileReaderInterface<T extends Map<String, ?>> {
    List<T> read(File file) throws IOException;
}
