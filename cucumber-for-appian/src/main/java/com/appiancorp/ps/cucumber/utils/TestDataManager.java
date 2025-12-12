package com.appiancorp.ps.cucumber.utils;
import java.util.HashMap;
import java.util.Map;

public class TestDataManager {

    private static final String TEST_DATA_FILE =
            "src/test/resources/testdata/TestData.xlsx";

    private static final Map<String, Map<String, String>> sheetCache = new HashMap<>();

    /**
     * Supports formats:
     * excel:Key
     * excel:Sheet.Key
     */
    public static String get(String input) {

        // Case 1: excel:Sheet.Key
        if (input.contains(".")) {
            String sheet = input.substring(0, input.indexOf("."));
            String key = input.substring(input.indexOf(".") + 1);

            return getValueFromSheet(sheet, key);
        }

        // Case 2: default "TestData" sheet
        return getValueFromSheet("TestData", input);
    }

    /**
     * Overloaded method for calls like:
     * TestDataManager.get("SheetName", "KeyName")
     */
    public static String get(String sheetName, String key) {
        return getValueFromSheet(sheetName, key);
    }

    private static String getValueFromSheet(String sheetName, String key) {

        if (!sheetCache.containsKey(sheetName)) {
            Map<String, String> sheetData = ExcelReader.readSheet(TEST_DATA_FILE, sheetName);
            sheetCache.put(sheetName, sheetData);
        }

        Map<String, String> data = sheetCache.get(sheetName);

        String value = data.get(key);
        if (value == null)
            throw new RuntimeException("Key '" + key + "' not found in sheet '" + sheetName + "'");

        return value;
    }
}
