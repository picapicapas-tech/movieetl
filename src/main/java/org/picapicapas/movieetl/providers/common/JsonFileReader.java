package org.picapicapas.movieetl.providers.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonFileReader implements FileReader<Map<String, Object>> {
    private final ObjectMapper objectMapper;

    public JsonFileReader() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Map<String, Object>> read(File file) throws IOException {
        return readJson(file);
    }

    public List<Map<String, Object>> readJson(File file) throws IOException {
        validateFile(file);

        Object jsonContent = objectMapper.readValue(file, Object.class);

        if (jsonContent instanceof List) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> result = (List<Map<String, Object>>) jsonContent;
            return result;
        } else if (jsonContent instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) jsonContent;
            return Arrays.asList(map);
        } else {
            throw new IOException("JSON must be an object or array, got: " + jsonContent.getClass().getName());
        }
    }
}
