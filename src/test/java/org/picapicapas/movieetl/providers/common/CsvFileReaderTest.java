package org.picapicapas.movieetl.providers.common;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class CsvFileReaderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private CsvFileReader csvFileReader;

    public CsvFileReaderTest() {
        this.csvFileReader = new CsvFileReader();
    }

    @Test
    public void testReadValidCsv() throws IOException {
        File csvFile = tempFolder.newFile("test.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("name,age,city\n");
            writer.write("John,30,New York\n");
            writer.write("Jane,25,Boston\n");
        }

        List<Map<String, String>> records = csvFileReader.read(csvFile);
        assertEquals(2, records.size());
        assertEquals("John", records.get(0).get("name"));
        assertEquals("30", records.get(0).get("age"));
        assertEquals("New York", records.get(0).get("city"));
    }

    @Test
    public void testReadEmptyCsv() throws IOException {
        File csvFile = tempFolder.newFile("empty.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("name,age,city\n");
        }

        List<Map<String, String>> records = csvFileReader.read(csvFile);
        assertEquals(0, records.size());
    }

    @Test
    public void testReadCsvWithNullFile() {
        assertThrows(IllegalArgumentException.class, () -> csvFileReader.read(null));
    }

    @Test
    public void testReadNonExistentFile() {
        File nonExistent = new File("nonexistent.csv");
        assertThrows(IllegalArgumentException.class, () -> csvFileReader.read(nonExistent));
    }

    @Test
    public void testReadDirectory() throws IOException {
        File dir = tempFolder.newFolder("testdir");
        assertThrows(IllegalArgumentException.class, () -> csvFileReader.read(dir));
    }

    @Test
    public void testReadCsvMethod() throws IOException {
        File csvFile = tempFolder.newFile("test2.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("id,value\n");
            writer.write("1,100\n");
            writer.write("2,200\n");
        }

        List<Map<String, String>> records = csvFileReader.readCsv(csvFile);
        assertEquals(2, records.size());
        assertEquals("100", records.get(0).get("value"));
    }
}
