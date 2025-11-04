package core.basesyntax.service.impl;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReportWriterImplTest {

    ReportWriter writer = new ReportWriterImpl();

    @Test
    void writeReport_validFilePath_ok() throws Exception{
        String reportContent = "fruit,quantity\nbanana,10";
        File tempFile = File.createTempFile("report",".txt");
        Path tempPath = tempFile.toPath();


        writer.writeReport(reportContent, tempFile.toString());

        String fileContent = Files.readString(tempPath).strip();
        assertEquals(reportContent, fileContent.strip());
        Files.delete(tempPath);
    }

    @Test
    void writeReport_invalidFilePath_throwsRuntimeException() throws Exception {
        String reportContent = "fruit,quantity\nbanana,10";

        assertThrows(RuntimeException.class, () ->
                writer.writeReport(reportContent, "Documents/nonexisting/file.txt"));
    }

    @Test
    void writeReport_emptyReport_throwsRuntimeException() throws Exception {
        String reportContent = "";
        File tempFile = File.createTempFile("report", ".txt");
        Path temPath = tempFile.toPath();

        assertThrows(RuntimeException.class, () ->
                writer.writeReport(reportContent, temPath.toString()));

        Files.deleteIfExists(temPath);
    }
}