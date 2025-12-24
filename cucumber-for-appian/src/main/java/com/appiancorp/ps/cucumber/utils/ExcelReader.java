package com.appiancorp.ps.cucumber.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    public static Map<String, String> readScenario(
            String filePath,
            String scenarioId) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheetAt(0); // first sheet (no hardcoding name)

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Excel header row missing");
            }

            Map<Integer, String> headers = new HashMap<>();
            for (Cell cell : headerRow) {
                headers.put(cell.getColumnIndex(), getCellValue(cell));
            }

            Map<String, Map<String, String>> allScenarioData = new HashMap<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String rowScenarioId = getCellValue(row.getCell(0));
                if (rowScenarioId.isEmpty()) continue;

                Map<String, String> rowData = new HashMap<>();

                for (Map.Entry<Integer, String> h : headers.entrySet()) {
                    Cell cell = row.getCell(h.getKey());
                    rowData.put(h.getValue(), getCellValue(cell));
                }

                allScenarioData.put(rowScenarioId, rowData);
            }

            Map<String, String> current = allScenarioData.get(scenarioId);
            if (current == null) {
                throw new RuntimeException("Scenario not found in Excel: " + scenarioId);
            }

            // Resolve ${TC001.ColumnName}
            Map<String, String> resolved = new HashMap<>();
            for (Map.Entry<String, String> e : current.entrySet()) {
                resolved.put(
                        e.getKey(),
                        resolveReference(e.getValue(), allScenarioData)
                );
            }

            return resolved;

        } catch (Exception e) {
            throw new RuntimeException("Excel scenario load failed", e);
        }
    }

    private static String resolveReference(
            String value,
            Map<String, Map<String, String>> allData) {

        if (value == null) return "";

        if (value.startsWith("${") && value.endsWith("}")) {
            String ref = value.substring(2, value.length() - 1); // TC001.Column
            String[] parts = ref.split("\\.");

            if (parts.length != 2) return value;

            Map<String, String> refRow = allData.get(parts[0]);
            return refRow != null ? refRow.getOrDefault(parts[1], "") : "";
        }
        return value;
    }

    private static String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell)
                        ? cell.getLocalDateTimeCellValue().toString()
                        : String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
