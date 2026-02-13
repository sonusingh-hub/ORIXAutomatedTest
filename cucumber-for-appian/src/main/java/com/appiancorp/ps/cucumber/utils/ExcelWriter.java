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

            String filePath = resolveExcelPath(excelFile);

            try (FileInputStream fis = new FileInputStream(filePath);
                 Workbook wb = WorkbookFactory.create(fis)) {

                Sheet sheet = wb.getSheetAt(0);
                Row header = sheet.getRow(0);

                int columnIndex = -1;
                for (Cell cell : header) {
                    if (cell.getStringCellValue()
                            .equalsIgnoreCase(columnName)) {
                        columnIndex = cell.getColumnIndex();
                        break;
                    }
                }

                if (columnIndex == -1) {
                    throw new RuntimeException(
                            "Column not found in Excel: " + columnName);
                }

                boolean rowUpdated = false;

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    Cell idCell = row.getCell(0);
                    if (idCell != null &&
                            scenarioId.equalsIgnoreCase(
                                    idCell.getStringCellValue())) {

                        Cell valueCell =
                                row.getCell(columnIndex);

                        if (valueCell == null) {
                            valueCell =
                                    row.createCell(columnIndex);
                        }

                        valueCell.setCellValue(value);
                        rowUpdated = true;
                        break;
                    }
                }

                if (!rowUpdated) {
                    throw new RuntimeException(
                            "Scenario ID not found in Excel: "
                                    + scenarioId);
                }

                try (FileOutputStream fos =
                             new FileOutputStream(filePath)) {
                    wb.write(fos);
                }

            } catch (Exception e) {
                throw new RuntimeException(
                        "Excel write failed", e);
            }
        }

        // =========================================================
        // SAME DYNAMIC RESOLVER AS TestDataManager
        // =========================================================

        private static String resolveExcelPath(
                String excelFile) {

            String baseDir =
                    "src/test/resources/testdata/";
            String expectedFile =
                    excelFile + ".xlsx";

            // Root folder check
            File rootFile =
                    new File(baseDir + expectedFile);

            if (rootFile.exists()) {
                return rootFile.getPath();
            }

            // Subfolder scan
            File baseFolder = new File(baseDir);

            File[] subFolders =
                    baseFolder.listFiles(File::isDirectory);

            if (subFolders != null) {
                for (File folder : subFolders) {

                    File excel =
                            new File(folder, expectedFile);

                    if (excel.exists()) {
                        return excel.getPath();
                    }
                }
            }

            // Not found
            throw new RuntimeException(
                    "Excel file not found in testdata or subfolders: "
                            + excelFile);
        }
    }


    // Old code keeping for reference
//    /**
//     * Update value in same scenario row
//     */
//    public static void write(
//            String excelFile,
//            String scenarioId,
//            String columnName,
//            String value) {
//
//        String filePath =
//                "src/test/resources/testdata/" + excelFile + ".xlsx";
//
//        try (FileInputStream fis = new FileInputStream(filePath);
//             Workbook wb = WorkbookFactory.create(fis)) {
//
//            Sheet sheet = wb.getSheetAt(0);
//            Row header = sheet.getRow(0);
//
//            int columnIndex = -1;
//            for (Cell cell : header) {
//                if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
//                    columnIndex = cell.getColumnIndex();
//                    break;
//                }
//            }
//
//            if (columnIndex == -1) {
//                throw new RuntimeException(
//                        "Column not found in Excel: " + columnName);
//            }
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//
//                Row row = sheet.getRow(i);
//                if (row == null) continue;
//
//                Cell idCell = row.getCell(0);
//                if (idCell != null &&
//                        scenarioId.equalsIgnoreCase(idCell.getStringCellValue())) {
//
//                    Cell valueCell = row.getCell(columnIndex);
//                    if (valueCell == null) {
//                        valueCell = row.createCell(columnIndex);
//                    }
//                    valueCell.setCellValue(value);
//                    break;
//                }
//            }
//
//            try (FileOutputStream fos = new FileOutputStream(filePath)) {
//                wb.write(fos);
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Excel write failed", e);
//        }
//    }
//}
