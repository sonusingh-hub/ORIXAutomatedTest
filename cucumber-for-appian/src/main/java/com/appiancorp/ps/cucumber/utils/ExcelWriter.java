package com.appiancorp.ps.cucumber.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class ExcelWriter {

    /**
     * Updates a row if key exists; otherwise appends a new row.
     *
     * @param fileName  - Excel file (TestData.xlsx)
     * @param sheetName - Excel sheet (VehicleData)
     * @param keyName   - Column A value (Vehicle Category)
     * @param value     - Column B value (Captured from UI)
     */
    public static void write(String fileName, String sheetName, String keyName, String value) {
        try {
            String filePath = "src/test/resources/testdata/" + fileName;
            File file = new File(filePath);

            Workbook workbook;

            // Load if exists, create if not
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = WorkbookFactory.create(fis);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            boolean updated = false;

            // Search for existing key
            for (Row row : sheet) {
                Cell keyCell = row.getCell(0); // Column A
                if (keyCell != null && keyCell.getCellType() == CellType.STRING &&
                        keyCell.getStringCellValue().equalsIgnoreCase(keyName)) {

                    // Found → update existing row
                    Cell valueCell = row.getCell(1);
                    if (valueCell == null) {
                        valueCell = row.createCell(1);
                    }
                    valueCell.setCellValue(value);
                    updated = true;
                    break;
                }
            }

            // If not updated → append new row
            if (!updated) {
                int lastRow = sheet.getLastRowNum() + 1;
                Row newRow = sheet.createRow(lastRow);

                newRow.createCell(0).setCellValue(keyName);   // Column A (Key)
                newRow.createCell(1).setCellValue(value);     // Column B (Value)
            }

            // Save file
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Excel updated → Sheet: " + sheetName +
                    " | Key: " + keyName + " | Value: " + value);

        } catch (Exception e) {
            throw new RuntimeException("Excel write failed: " + e.getMessage(), e);
        }
    }
}
