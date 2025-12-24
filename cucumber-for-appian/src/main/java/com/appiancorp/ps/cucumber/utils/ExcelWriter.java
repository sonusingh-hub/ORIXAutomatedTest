package com.appiancorp.ps.cucumber.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.*;

public class ExcelWriter {

    /**
     * Update value in same scenario row
     */
    public static void write(
            String excelFile,
            String scenarioId,
            String columnName,
            String value) {

        String filePath =
                "src/test/resources/testdata/" + excelFile + ".xlsx";

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            Row header = sheet.getRow(0);

            int columnIndex = -1;
            for (Cell cell : header) {
                if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new RuntimeException(
                        "Column not found in Excel: " + columnName);
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell idCell = row.getCell(0);
                if (idCell != null &&
                        scenarioId.equalsIgnoreCase(idCell.getStringCellValue())) {

                    Cell valueCell = row.getCell(columnIndex);
                    if (valueCell == null) {
                        valueCell = row.createCell(columnIndex);
                    }
                    valueCell.setCellValue(value);
                    break;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                wb.write(fos);
            }

        } catch (Exception e) {
            throw new RuntimeException("Excel write failed", e);
        }
    }
}
