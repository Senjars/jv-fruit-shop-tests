package core.basesyntax.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportWriterImpl implements ReportWriter {
    @Override
    public void writeReport(String report, String filePath) {
        if (report == null || report.isBlank()) {
            throw new IllegalArgumentException("Cannot write an empty or null report");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save the report to file: " + filePath, e);
        }
    }
}
