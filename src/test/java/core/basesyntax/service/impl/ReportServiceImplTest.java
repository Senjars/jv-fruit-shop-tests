package core.basesyntax.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceImplTest {

    ReportService reportService = new ReportServiceImpl();

    @Test
    void generateReport_emptyMap_ok() {
        Map<String, Integer> storage = Map.of();
        String report = reportService.generateReport(storage);
        assertNotNull(report);
        assertEquals("fruit,quantity", report.strip());
    }

    @Test
    void generateReport_oneProduct_ok() {
        Map<String, Integer> storage = Map.of("banana", 10);
        String report = reportService.generateReport(storage);
        assertNotNull(report);
        assertTrue(report.contains("banana"));
    }

    @Test
    void generateReport_manyProducts_ok() {
        Map<String, Integer> storage = Map.of("banana", 10, "apple", 5);
        String report = reportService.generateReport(storage);
        assertTrue(report.contains("banana"));
        assertTrue(report.contains("apple"));
        assertTrue(report.contains("10"));
        assertTrue(report.contains("5"));
    }

    @Test
    void generateReport_quantityZero_ok() {
        Map<String, Integer> storage = Map.of("banana", 0);
        String report = reportService.generateReport(storage);
        assertNotNull(report);
        assertTrue(report.contains("banana"));
        assertTrue(report.contains("0"));
    }

}