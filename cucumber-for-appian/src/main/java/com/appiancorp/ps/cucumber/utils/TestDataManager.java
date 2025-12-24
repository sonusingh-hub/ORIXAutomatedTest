package com.appiancorp.ps.cucumber.utils;

import java.util.HashMap;
import java.util.Map;

public class TestDataManager {

    private static Map<String, String> scenarioData = new HashMap<>();
    private static String currentExcelFile;
    private static String currentScenarioId;

    public static void loadScenario(String scenarioId, String excelFile) {

        currentScenarioId = scenarioId;
        currentExcelFile = excelFile;

        String filePath =
                "src/test/resources/testdata/" + excelFile + ".xlsx";

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
}
