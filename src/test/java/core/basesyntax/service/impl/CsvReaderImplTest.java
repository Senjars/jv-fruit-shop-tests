package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvReaderImplTest {

    @TempDir
    private Path temDir;
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
    void read_invalidDirectoryPath_throwsRunTimeException() {
        assertThrows(RuntimeException.class, () ->
                reader.read("folder/"));
    }

    @Test
    void read_validPathFile_ok() throws IOException {
        String fileName = "test-data.csv";
        List<String> fileContent = Arrays.asList(
                "operation,fruit,quantity",
                "p,banana,10",
                "b,apple,100");

        Path tempFilePath = temDir.resolve(fileName);

        Files.write(tempFilePath, fileContent);
        String validPathFile = tempFilePath.toString();

        List<String> result = reader.read(validPathFile);

        assertEquals(3, result.size());
    }
}
