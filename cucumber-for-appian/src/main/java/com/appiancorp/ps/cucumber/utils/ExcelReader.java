package com.appiancorp.ps.cucumber.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    public static Map<String, String> readSheet(String filePath, String sheetName) {
        Map<String, String> data = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);

            for (Row row : sheet) {

                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);

                if (keyCell == null || valueCell == null)
                    continue;

                String key = getCellValueAsString(keyCell);
                String value = getCellValueAsString(valueCell);

                data.put(key, value);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to read Excel: " + e.getMessage(), e);
        }

        return data;
    }

    // *** UNIVERSAL CELL HANDLER ***
    private static String getCellValueAsString(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue()).trim();

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                // Compute formula result
                FormulaEvaluator evaluator =
                        cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                return getCellValueAsString(evaluator.evaluateInCell(cell));

            case BLANK:
            default:
                return "";
        }
    }
}
