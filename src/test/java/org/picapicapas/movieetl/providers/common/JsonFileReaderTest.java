package org.picapicapas.movieetl.providers.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class JsonFileReaderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private JsonFileReader jsonFileReader = new JsonFileReader();

    @Test
    public void testJsonFileReaderReadArray() throws IOException {
        File jsonFile = tempFolder.newFile("test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[{\"name\": \"Test1\"}, {\"name\": \"Test2\"}]");
        }

        List<Map<String, Object>> result = jsonFileReader.read(jsonFile);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testJsonFileReaderReadObject() throws IOException {
        File jsonFile = tempFolder.newFile("test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\"name\": \"Test\"}");
        }

        List<Map<String, Object>> result = jsonFileReader.read(jsonFile);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testJsonFileReaderReadEmptyArray() throws IOException {
        File jsonFile = tempFolder.newFile("test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[]");
        }

        List<Map<String, Object>> result = jsonFileReader.read(jsonFile);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testJsonFileReaderReadWithValidFields() throws IOException {
        File jsonFile = tempFolder.newFile("test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[{\"title\": \"Inception\", \"year\": 2010}]");
        }

        List<Map<String, Object>> result = jsonFileReader.read(jsonFile);

        assertNotNull(result);
        assertEquals(1, result.size());
        Map<String, Object> record = result.get(0);
        assertEquals("Inception", record.get("title"));
        assertEquals(2010, record.get("year"));
    }

    @Test
    public void testJsonFileReaderFileNotFound() {
        File nonExistentFile = new File("non_existent_file.json");

        assertThrows(IllegalArgumentException.class, () -> jsonFileReader.read(nonExistentFile));
    }

    @Test
    public void testJsonFileReaderInvalidJson() throws IOException {
        File jsonFile = tempFolder.newFile("invalid.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{invalid json}");
        }

        assertThrows(IOException.class, () -> jsonFileReader.read(jsonFile));
    }
}
