package com.appiancorp.ps.cucumber.utils;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class TestDataManager {

    private static Map<String, String> scenarioData = new HashMap<>();
    private static String currentExcelFile;
    private static String currentScenarioId;

    public static void loadScenario(String scenarioId, String excelFile) {

        currentScenarioId = scenarioId;
        currentExcelFile = excelFile;

        String filePath = resolveExcelPath(excelFile);

        scenarioData =
                ExcelReader.readScenario(filePath, scenarioId);
    }

    public static String get(String columnName) {
        if (scenarioData == null || scenarioData.isEmpty()) {
            throw new RuntimeException("Test data not loaded for scenario");
        }
        String value = scenarioData.get(columnName);
        if (value == null) {
            throw new RuntimeException(
                    "Column '" + columnName + "' not found in loaded scenario");
        }
        return value;
    }

    public static void put(String columnName, String value) {
        scenarioData.put(columnName, value);
    }

    public static String getCurrentExcelFile() {
        return currentExcelFile;
    }

    public static String getCurrentScenarioId() {
        return currentScenarioId;
    }

    // =========================================================
    // NEW METHOD — Dynamic Excel Path Resolver
    // =========================================================

    private static String resolveExcelPath(String excelFile) {

        String baseDir = "src/test/resources/testdata/";
        String expectedFile = excelFile + ".xlsx";

        // Check root testdata folder
        File rootFile = new File(baseDir + expectedFile);

        if (rootFile.exists()) {
            return rootFile.getPath();
        }

        // Scan subfolders (End2End / Regression / etc.)
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

        // If not found → Throw error
        throw new RuntimeException(
                "Excel file not found in testdata or subfolders: "
                        + excelFile);
    }
}

// Old Code keeping for reference

//public class TestDataManager {
//
//    private static Map<String, String> scenarioData = new HashMap<>();
//    private static String currentExcelFile;
//    private static String currentScenarioId;
//
//    public static void loadScenario(String scenarioId, String excelFile) {
//
//        currentScenarioId = scenarioId;
//        currentExcelFile = excelFile;
//
//        String filePath =
//                "src/test/resources/testdata/" + excelFile + ".xlsx";
//
//        scenarioData =
//                ExcelReader.readScenario(filePath, scenarioId);
//    }
//
//    public static String get(String columnName) {
//        if (scenarioData == null || scenarioData.isEmpty()) {
//            throw new RuntimeException("Test data not loaded for scenario");
//        }
//        String value = scenarioData.get(columnName);
//        if (value == null) {
//            throw new RuntimeException(
//                    "Column '" + columnName + "' not found in loaded scenario");
//        }
//        return value;
//    }
//
//    public static void put(String columnName, String value) {
//        scenarioData.put(columnName, value);
//    }
//
//    public static String getCurrentExcelFile() {
//        return currentExcelFile;
//    }
//
//    public static String getCurrentScenarioId() {
//        return currentScenarioId;
//    }
//}
