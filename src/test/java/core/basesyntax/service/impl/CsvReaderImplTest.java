package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvReaderImplTest {

    private final CsvReader reader = new CsvReaderImpl();

    @Test
    void read_pathFileNull_throwsRunTimeException() {
        assertThrows(RuntimeException.class, () ->
                reader.read(null));
    }

    @Test
    void read_fileNotFound_throwsRunTimeException() {
        assertThrows(RuntimeException.class, () ->
                reader.read("nonexistent.csv"));
    }

    @Test
    void read_InvalidFormat_throwsRunTimeException() {
        assertThrows(RuntimeException.class, () ->
                reader.read("invalidFormat.jpg"));
    }

    @Test
    void read_directoryPath_throwsRunTimeException() {
        assertThrows(RuntimeException.class, () ->
                reader.read("folder/"));
    }

    @Test
    void read_validPathFile_ok() {
        String validPathFile = "src/test/resources/test-data.csv";
        List<String> result = reader.read(validPathFile);

        assertEquals(3, result.size());
    }
}
