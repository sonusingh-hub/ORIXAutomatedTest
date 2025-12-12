package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.GenericTestException;
import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.test.AbstractLoginTest;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TempoFixtureInterfacesTest extends AbstractLoginTest<TempoFixture> {

    public static final String MASKED_VALUES = "**********";
    private String[] startPng = {new File("src/test/resources/images/FileUploadImage.png").getAbsolutePath()};
    private String[] propertiesPng = {new File("src/test/resources/images/FileUploadImage2.png").getAbsolutePath()};

    public TempoFixtureInterfacesTest() {
        super(new TempoFixture());
    }

    @BeforeEach
    public void setUpInterface() throws Exception {
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("All Fields");
    }


    @AfterEach
    public void tearDownInterface() throws Exception {
        fixture.clickOnButton("Cancel");
    }

    /**** Form ****/

    @Test
    public void testFormTitle() throws Exception {
        assertEquals(fixture.getFormTitle(), "All Fields");
    }

    @Test
    public void testGetRegexGroupFromFormTitle() throws Exception {
        assertEquals(fixture.getRegexGroupFromFormTitle("([a-zA-Z ]+)", 1), "All Fields");
    }

    @Test
    public void testFormInstructions() throws Exception {
        assertEquals(fixture.getFormInstructions(), "Instructions");
    }

    /**** Text Field ****/

    @Test
    public void testTextField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("TextField"));
        assertFalse(fixture.verifyFieldIsNotBlank("ROTextField"));

        fixture.populateFieldWith("TextField", new String[] {"text"});
        assertTrue(fixture.verifyFieldContains("TextField", new String[] {"text"}));
        assertTrue(fixture.verifyFieldIsNotBlank("TextField"));
        assertEquals("text", fixture.getFieldValue("TextField"));
        assertEquals("text", fixture.getRegexGroupFromFieldValue("([a-zA-Z0-9]{4})", 1, "TextField"));

        assertTrue(fixture.verifyFieldIsNotBlank("ROTextField"));
        assertTrue(fixture.verifyFieldContains("ROTextField", new String[] {"text"}));
        assertTrue(fixture.verifyFieldContains("ROTextField", new String[] {"te"}));
        assertEquals("text", fixture.getFieldValue("ROTextField"));

        fixture.populateFieldWith("TextField", new String[] {"apostrophe's name"});
        assertTrue(fixture.verifyFieldIsNotBlank("TextField"));
        assertTrue(fixture.verifyTextIsPresent("apostrophe's name"));
        assertTrue(fixture.verifyTextIsNotPresent("Fake text"));
        assertTrue(fixture.verifyFieldContains("TextField", new String[] {"apostrophe's name"}));
        assertEquals("apostrophe's name", fixture.getFieldValue("TextField"));

        fixture.populateFieldWith("Field'With'Apostrophe", new String[] {"value"});
        assertTrue(fixture.verifyTextIsPresent("Field'With'Apostrophe"));
        assertTrue(fixture.verifyFieldContains("TextField", new String[] {"apostrophe's name"}));
        assertEquals("apostrophe's name", fixture.getFieldValue("TextField"));

        fixture.populateFieldWith("All Fields", new String[] {"1"});
        assertTrue(fixture.verifyFieldIsNotBlank("TextField"));
        assertTrue(fixture.verifyFieldContains("All Fields", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("All Fields"));
        fixture.clearField("All Fields[1]"); // Added to prevent duplicate field input if test is run again

        fixture.populateFieldWith("All Fields[2]", new String[] {"matching"});
        assertTrue(fixture.verifyFieldIsNotBlank("All Fields[2]"));
        assertTrue(fixture.verifyFieldContains("All Fields[2]", new String[] {"matching"}));
        assertEquals("matching", fixture.getFieldValue("All Fields[2]"));

        // Side By Side Field
        fixture.populateFieldWith("TextField[3]", new String[] {"text"});
        assertTrue(fixture.verifyFieldIsNotBlank("TextField[3]"));
        assertTrue(fixture.verifyFieldContains("TextField[3]", new String[] {"text"}));
        assertEquals("text", fixture.getFieldValue("TextField[3]"));

        fixture.clearField("TextField");
        assertFalse(fixture.verifyFieldIsNotBlank("TextField"));
        assertEquals("", fixture.getFieldValue("TextField"));

        // Editable Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("[1]", "Textfield", "[1]"));
        fixture.populateGridColumnRowWith("[1]", "Textfield", "[1]", new String[] {"gridText"});
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[1]", "Textfield", "[1]"));
        assertTrue(fixture.verifyGridColumnRowContains("[1]", "TextField", "[1]", new String[] {"gridText"}));
        assertEquals("gridText", fixture.getGridColumnRowValue("[1]", "TextField", "[1]"));
        assertEquals("grid",
                fixture.getRegexGroupFromGridColumnRowValue("([a-zA-Z0-9]{4})", 1, "[2]", "TextField", "[1]"));

        // Read Only Grid
        assertTrue(fixture.verifyGridColumnRowContains("[2]", "TextField", "[1]", new String[] {"gridText"}));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[2]", "Textfield", "[1]"));
        assertEquals("gridText", fixture.getGridColumnRowValue("[2]", "TextField", "[1]"));
    }

    @Test
    public void testTextFieldIndex() throws Exception {
        fixture.populateFieldWith("TextField[2]", new String[] {"text2"});
        assertTrue(fixture.verifyFieldIsNotBlank("TextField[2]"));
        assertTrue(fixture.verifyFieldContains("TextField[2]", new String[] {"text2"}));
        assertEquals(fixture.getFieldValue("TextField[2]"), "text2");
    }

    @Test
    public void testTextFieldPopulateType() throws Exception {
        fixture.populateFieldWith("TEXT", "[1]", new String[] {"Text index"});
        assertTrue(fixture.verifyFieldContains("Textfield", new String[] {"Text index"}));
    }

    @Test
    public void testEmptyRichTextField() throws Exception {
        assertEquals(fixture.getFieldValue("EmptyRichTextField"), "");
    }

    /**** Barcode Field ****/

    @Test
    public void testBarcodeField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("BarcodeField"));
        fixture.populateFieldWith("BarcodeField", new String[] {"barcode123"});
        assertTrue(fixture.verifyFieldIsNotBlank("BarcodeField"));
        assertTrue(fixture.verifyFieldContains("BarcodeField", new String[] {"barcode123"}));
        assertEquals("barcode123", fixture.getFieldValue("BarcodeField"));

        assertTrue(fixture.verifyFieldIsNotBlank("ROBarcodeField"));
        assertTrue(fixture.verifyFieldContains("ROBarcodeField", new String[] {"barcode123"}));
        assertEquals("barcode123", fixture.getFieldValue("ROBarcodeField"));


        //Side By Side Field
        fixture.populateFieldWith("BarcodeField[2]", new String[] {"barcode123"});
        assertTrue(fixture.verifyFieldContains("BarcodeField[2]", new String[] {"barcode123"}));
        assertEquals("barcode123", fixture.getFieldValue("BarcodeField[2]"));
    }


    /**** Masked Barcode Field ****/

    @Test
    public void testMaskedBarcodeField() throws Exception {
        //Read only barcode field is always shown as MASKED_VALUES
        assertTrue(fixture.verifyFieldContains("ROMaskedBarcodeField", new String[] {MASKED_VALUES}));
        assertEquals(MASKED_VALUES, fixture.getFieldValue("ROMaskedBarcodeField"));

        fixture.populateFieldWith("MaskedBarcodeField", new String[] {"maskedBarcode123"});
        assertTrue(fixture.verifyFieldContains("MaskedBarcodeField", new String[] {"maskedBarcode123"}));
        assertEquals("maskedBarcode123", fixture.getFieldValue("MaskedBarcodeField"));

        assertTrue(fixture.verifyFieldContains("ROMaskedBarcodeField", new String[] {MASKED_VALUES}));
        assertEquals(MASKED_VALUES, fixture.getFieldValue("ROMaskedBarcodeField"));

        //Side By Side Field
        assertTrue(fixture.verifyFieldContains("ROMaskedBarcodeField[2]", new String[] {MASKED_VALUES}));
        assertEquals(MASKED_VALUES, fixture.getFieldValue("ROMaskedBarcodeField"));

        fixture.populateFieldWith("MaskedBarcodeField[2]", new String[] {"maskedBarcode123"});
        assertTrue(fixture.verifyFieldContains("MaskedBarcodeField[2]", new String[] {"maskedBarcode123"}));
        assertEquals("maskedBarcode123", fixture.getFieldValue("MaskedBarcodeField[2]"));

        assertTrue(fixture.verifyFieldContains("ROMaskedBarcodeField[2]", new String[] {MASKED_VALUES}));
        assertEquals(MASKED_VALUES, fixture.getFieldValue("ROMaskedBarcodeField"));
    }

    /**** Encrypted Text Field ****/

    @Test
    public void testEncryptedTextField() throws Exception {
        fixture.populateFieldWithValue("[5]", "encrypted");
        assertTrue(fixture.verifyFieldIsNotBlank("[5]"));
        assertTrue(fixture.verifyFieldContainsValue("[5]", "encrypted"));
        assertEquals("encrypted", fixture.getFieldValue("EncryptedTextField"));

        assertTrue(fixture.verifyFieldIsNotBlank("ROEncryptedTextField"));
        assertTrue(fixture.verifyFieldContains("ROEncryptedTextField", new String[] {"encrypted"}));
        assertEquals("encrypted", fixture.getFieldValue("ROEncryptedTextField"));

        // Editable Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("EditableGrid", "encryptedTextField", "[1]"));
        fixture.populateGridColumnRowWith("EditableGrid", "encryptedTextField", "[1]", new String[] {"gridEncrypted"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "EncryptedTextField", "[1]",
                new String[] {"gridEncrypted"}));
        assertEquals("gridEncrypted", fixture.getGridColumnRowValue("EditableGrid", "EncryptedTextField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid", "encryptedTextField", "[1]"));

        // Read Only Grid
        assertTrue(fixture.verifyGridColumnRowContains("ReadOnlyGrid", "EncryptedTextField", "[1]",
                new String[] {"gridEncrypted"}));
        assertEquals("gridEncrypted", fixture.getGridColumnRowValue("ReadOnlyGrid", "EncryptedTextField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("ReadOnlyGrid", "EncryptedTextField", "[1]"));


        fixture.clearField("[5]");
        assertTrue(fixture.verifyFieldContains("[5]", new String[] {""}));

        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("EncryptedTextField[2]"));
        fixture.populateFieldWithValue("EncryptedTextField[2]", "encrypted");
        assertTrue(fixture.verifyFieldContainsValue("EncryptedTextField[2]", "encrypted"));
        assertTrue(fixture.verifyFieldIsNotBlank("EncryptedTextField[2]"));
        assertEquals("encrypted", fixture.getFieldValue("EncryptedTextField[2]"));
    }

    /**** Paragraph Field ****/

    @Test
    public void testParagraphField() throws Exception {
        fixture.clearField("[3]");
        assertFalse(fixture.verifyFieldIsNotBlank("[3]"));
        fixture.populateFieldWith("[3]", new String[] {"paragraph"});
        assertTrue(fixture.verifyFieldContains("[3]", new String[] {"paragraph"}));
        assertEquals("paragraph", fixture.getFieldValue("[3]"));
        assertTrue(fixture.verifyFieldIsNotBlank("[3]"));


        assertTrue(fixture.verifyFieldContains("RoParagraphField", new String[] {"paragraph"}));
        assertEquals("paragraph", fixture.getFieldValue("ROParagraphField"));
        assertTrue(fixture.verifyFieldIsNotBlank("RoParagraphField"));

        // Editable Grid
        fixture.clearGridColumnRow("EditableGrid", "[3]", "[1]");
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("EditableGrid", "[3]", "[1]"));
        fixture.populateGridColumnRowWith("EditableGrid", "[3]", "[1]", new String[] {"gridParagraph"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[] {"gridParagraph"}));
        assertEquals("gridParagraph", fixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid", "[3]", "[1]"));


        // Read Only Grid
        assertTrue(fixture.verifyGridColumnRowContains("ReadOnlyGrid", "[2]", "[1]", new String[] {"gridParagraph"}));
        assertEquals("gridParagraph", fixture.getGridColumnRowValue("ReadOnlyGrid", "[2]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("ReadOnlyGrid", "[2]", "[1]"));

        // Clear field
        fixture.clearField("ParagraphField[1]");
        assertEquals("", fixture.getFieldValue("ParagraphField[1]"));

        // Clear grid
        fixture.clearGridColumnRow("EditableGrid", "[3]", "[1]");
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "[3]", "[1]", new String[] {""}));
        assertEquals("", fixture.getGridColumnRowValue("EditableGrid", "[3]", "[1]"));

        // Side By Side Field
        fixture.clearField("ParagraphField[2]");
        assertFalse(fixture.verifyFieldIsNotBlank("ParagraphField[2]"));
        fixture.populateFieldWith("ParagraphField[2]", new String[] {"paragraph"});
        assertTrue(fixture.verifyFieldContains("ParagraphField[2]", new String[] {"paragraph"}));
        assertEquals("paragraph", fixture.getFieldValue("ParagraphField[2]"));
        assertTrue(fixture.verifyFieldIsNotBlank("ParagraphField[2]"));

        assertTrue(fixture.verifyFieldContains("RoParagraphField[2]", new String[] {"paragraph"}));
        assertEquals("paragraph", fixture.getFieldValue("ROParagraphField[2]"));
    }

    @Test
    public void testEditableGridCellValidations() throws Exception {
        // > 10 characters causes validation message to appear
        fixture.populateGridColumnRowWithValue("EditableGrid", "TextField", "[1]", "This text is too long");
        assertEquals(
            "Text field cannot exceed 10 characters",
            fixture.getGridColumnRowValidationMessage("EditableGrid", "TextField", "[1]")
        );

        // <= 0 causes validation message to appear
        fixture.populateGridColumnRowWithValue("EditableGrid", "IntegerField", "[1]", "0");
        assertEquals(
            "Integer must be greater than 0",
            fixture.getGridColumnRowValidationMessage("EditableGrid", "IntegerField", "[1]")
        );

        // > 100 causes validation message to appear
        fixture.populateGridColumnRowWithValue("EditableGrid", "DecimalField", "[1]", "150");
        assertEquals(
            "Decimal value cannot exceed 100",
            fixture.getGridColumnRowValidationMessage("EditableGrid", "DecimalField", "[1]")
        );

        // date in the future causes validation message to appear
        fixture.populateGridColumnRowWithValue("EditableGrid", "DateField", "[1]", "12/31/2050");
        assertEquals(
            "Date cannot be in the future",
            fixture.getGridColumnRowValidationMessage("EditableGrid", "DateField", "[1]")
        );

        fixture.clearGridColumnRow("EditableGrid", "TextField", "[1]");
        fixture.clearGridColumnRow("EditableGrid", "IntegerField", "[1]");
        fixture.clearGridColumnRow("EditableGrid", "DecimalField", "[1]");
        fixture.clearGridColumnRow("EditableGrid", "DateField", "[1]");
    }

    @Test
    public void testParagraphPopulateNewLine() throws Exception {
        fixture.clearField("ParagraphField[1]");
        fixture.populateFieldWith("ParagraphField", new String[] {"First Line\r\nSecond Line"});
        assertTrue(fixture.verifyFieldContains("ParagraphField", new String[] {"First Line\r\nSecond Line"}),
                "First Line\r\nSecond Line");
    }

    @Test
    public void testParagraphPopulateFieldType() throws Exception {
        fixture.populateFieldWith("PARAGRAPH", "[1]", new String[] {"Paragraph index"});
        assertTrue(fixture.verifyFieldContains("Paragraphfield", new String[] {"Paragraph index"}));
    }

    /**** Integer Field ****/

    @Test
    public void testIntegerField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("IntegerField"));
        fixture.populateFieldWith("IntegerField", new String[] {"1"});
        assertTrue(fixture.verifyFieldContains("IntegerField", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("IntegerField"));
        assertTrue(fixture.verifyFieldIsNotBlank("IntegerField"));


        assertTrue(fixture.verifyFieldContains("ROIntegerField", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("ROIntegerField"));
        assertTrue(fixture.verifyFieldIsNotBlank("ROIntegerField"));

        // Editable Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("[1]", "[5]", "[1]"));
        fixture.populateGridColumnRowWith("[1]", "[5]", "[1]", new String[] {"1"});
        assertTrue(fixture.verifyGridColumnRowContains("[1]", "[5]", "[1]", new String[] {"1"}));
        assertEquals("1", fixture.getGridColumnRowValue("[1]", "[5]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[1]", "[5]", "[1]"));


        // Read Only Grid
        assertTrue(fixture.verifyGridColumnRowContains("[2]", "[4]", "[1]", new String[] {"1"}));
        assertEquals("1", fixture.getGridColumnRowValue("[2]", "[4]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[2]", "[4]", "[1]"));

        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("IntegerField[2]"));
        fixture.populateFieldWith("IntegerField[2]", new String[] {"1"});
        assertTrue(fixture.verifyFieldContains("IntegerField[2]", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("IntegerField[2]"));
        assertTrue(fixture.verifyFieldIsNotBlank("IntegerField[2]"));

        assertTrue(fixture.verifyFieldContains("ROIntegerField[2]", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("ROIntegerField[2]"));
        assertTrue(fixture.verifyFieldIsNotBlank("ROIntegerField[2]"));
    }

    /**** Decimal Field ****/

    @Test
    public void testDecimalField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("DecimalField"));
        fixture.populateFieldWith("DecimalField", new String[] {"2.2"});
        assertTrue(fixture.verifyFieldContains("decimalField", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getFieldValue("DecimalField"));
        assertTrue(fixture.verifyFieldIsNotBlank("DecimalField"));

        assertTrue(fixture.verifyFieldContains("RODecimalField", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getFieldValue("RODecimalField"));
        assertTrue(fixture.verifyFieldIsNotBlank("RODecimalField"));


        // Editable Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("[1]", "DecimalField", "[1]"));
        fixture.populateGridColumnRowWith("[1]", "DecimalField", "[1]", new String[] {"2.2"});
        assertTrue(fixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[1]", "DecimalField", "[1]"));


        // Read Only Grid
        assertTrue(fixture.verifyGridColumnRowContains("[1]", "DecimalField", "[1]", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getGridColumnRowValue("[1]", "DecimalField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[1]", "DecimalField", "[1]"));


        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("DecimalField[2]"));
        fixture.populateFieldWith("DecimalField[2]", new String[] {"2.2"});
        assertTrue(fixture.verifyFieldContains("DecimalField[2]", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getFieldValue("DecimalField[2]"));
        assertTrue(fixture.verifyFieldIsNotBlank("DecimalField[2]"));

        assertTrue(fixture.verifyFieldContains("RODecimalField[2]", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getFieldValue("RODecimalField[2]"));
    }

    /**** Dropdown Field ****/

    @Test
    public void testDropdownField() throws Exception {
        fixture.verifyFieldIsNotBlank("DropdownField[1]");
        fixture.populateFieldWithValue("DropdownField[1]", "Option 2");
        assertTrue(fixture.verifyFieldContainsValue("DropdownField[1]", "Option 2"));
        assertEquals("Option 2", fixture.getFieldValue("Dropdownfield"));

        try {
            fixture.populateFieldWithValue("Dropdownfield", "Option X");
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }

        // Grid
        fixture.populateGridColumnRowWith("EditableGrid[2]", "[2]", "[1]", new String[] {"[1]"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "[2]", "[1]", new String[] {"[1]"}));
        assertEquals("Option 1", fixture.getGridColumnRowValue("EditableGrid[2]", "[2]", "[1]"));

        // Side By Side Field
        fixture.populateFieldWithValue("DropdownField[2]", "Option 2");
        assertTrue(fixture.verifyFieldContainsValue("DropdownField[2]", "Option 2"));
        assertEquals("Option 2", fixture.getFieldValue("DropdownField[2]"));
    }

    @Test
    public void testDropdownFieldLong() throws Exception {

        int[] indices = new int[] {1, 145, 150};

        for (int index : indices) {
            fixture.populateFieldWithValue("Long DropdownField", "[" + index + "]");
            assertTrue(fixture.verifyFieldContainsValue("Long DropdownField", "[" + index + "]"));
            assertEquals("Option " + index, fixture.getFieldValue("Long DropdownField"));
        }

        for (int index : indices) {
            fixture.populateFieldWithValue("Long DropdownField", "Option " + index);
            assertTrue(fixture.verifyFieldContainsValue("Long DropdownField", "Option " + index));
            assertEquals("Option " + index, fixture.getFieldValue("Long DropdownField"));
        }
    }

    @Test
    public void testMultipleDropdownFieldLong() throws Exception {
        int[] indices = new int[] {1, 145, 123};
        int[] secondChoiceIndices = new int[] {2, 147, 150};

        for (int i = 0; i < indices.length; i++) {
            fixture.clearField("Long MultipleDropdownField");
            fixture.populateFieldWith("Long MultipleDropdownField",
                    new String[] {"Option " + indices[i], "[" + secondChoiceIndices[i] + "]"});
            assertTrue(fixture.verifyFieldContains("Long MultipleDropdownField",
                    new String[] {"Option " + indices[i], "[" + secondChoiceIndices[i] + "]"}));
            assertEquals("Option " + indices[i] + ",Option " + secondChoiceIndices[i],
                    fixture.getFieldValue("Long MultipleDropdownField"));
        }

        fixture.populateFieldWith("Long MultipleDropdownField",
            new String[] {"Option 66", "Option 88"});
        fixture.clearFieldOf("Long MultipleDropdownField", new String[] {"Option 66"});
        assertTrue(fixture.verifyFieldContains("Long MultipleDropdownField", new String[] {"Option 88"}));
        assertFalse(fixture.verifyFieldContains("Long MultipleDropdownField", new String[] {"Option 66"}));

        fixture.clearField("Long MultipleDropdownField");
    }
    
    @Test
    public void testSearchBoxMultipleDropdownFieldLong() throws Exception {
        // Test regular multiple dropdown functionality
        int[] indices = new int[] {1, 145, 123};
        int[] secondChoiceIndices = new int[] {2, 147, 150};

        for (int i = 0; i < indices.length; i++) {
            fixture.clearField("Long SearchBoxMultipleDropdownField");
            fixture.populateFieldWith("Long SearchBoxMultipleDropdownField",
                new String[] {"Option " + indices[i], "[" + secondChoiceIndices[i] + "]"});
            assertTrue(fixture.verifyFieldContains("Long SearchBoxMultipleDropdownField",
                new String[] {"Option " + indices[i], "[" + secondChoiceIndices[i] + "]"}));
            assertEquals("Option " + indices[i] + ",Option " + secondChoiceIndices[i],
                fixture.getFieldValue("Long SearchBoxMultipleDropdownField"));
        }

        fixture.clearField("Long SearchBoxMultipleDropdownField");

        // Test searchbox functionality
        fixture.populateDropdownSearchBoxWith("Long SearchBoxMultipleDropdownField", "Option 37");
        assertTrue(fixture.verifyTextIsPresent("Option 37"));
        assertTrue(fixture.verifyTextIsNotPresent("Option 7"));
        try {
            fixture.populateDropdownSearchBoxWith("Long SearchBoxMultipleDropdownField", "Not an option");
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentException e) {
        }

        fixture.clearField("Long SearchBoxMultipleDropdownField");
    }

    @Test
    public void testNoPlaceholderDropdownField() throws Exception {
        int[] indices = new int[] {1, 145, 150};

        for (int index : indices) {
            fixture.populateFieldWithValue("No Placeholder DropdownField", "[" + index + "]");
            assertTrue(fixture.verifyFieldContainsValue("No Placeholder DropdownField", "[" + index + "]"));
            assertEquals("Option " + index, fixture.getFieldValue("No Placeholder DropdownField"));
        }
    }

    @Test
    public void testDropdownFieldPopulateWithValue() throws Exception {
        fixture.populateFieldWithValue("DropdownField", "Option, 1");
        assertTrue(fixture.verifyFieldContainsValue("dropdownField", "Option, 1"));
        assertEquals("Option, 1", fixture.getFieldValue("DropdownField"));
    }

    @Test
    public void testDropdownFieldPopulateType() throws Exception {
        try {
            fixture.populateFieldWith("DROPDOWN", "DropdownField", new String[] {"Value"});
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }
    }

    @Test
    public void testDropdownFieldSearchBox() throws Exception {
        fixture.populateDropdownSearchBoxWith("SearchBoxDropdownField", "DropdownSearchBoxOption 1");
        assertTrue(fixture.verifyTextIsPresent("DropdownSearchBoxOption 1"));
        assertTrue(fixture.verifyTextIsNotPresent("DropdownSearchBoxOption 2"));
        try {
            fixture.populateDropdownSearchBoxWith("SearchBoxDropdownField", "Option 3");
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**** Multiple Dropdown Field ****/

    @Test
    public void testMultipleDropdownField() throws Exception {
        fixture.populateFieldWith("MultipleDropdownField", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("MultipleDropdownField", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("MultipleDropdownField"));

        // Side By Side Field
        fixture.populateFieldWith("MultipleDropdownField[2]", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("MultipleDropdownField[2]", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("MultipleDropdownField[2]"));

        fixture.clearField("MultipleDropdownField");
    }

    /*
     * This tests an edge case where verifying dropdown fields fail if another dropdown field is populated
     * in the same test run.
     */
    @Test
    public void testMultipleValuesFields() throws Exception {
        fixture.populateFieldWithValue("DropdownField", "Option, 1");
        fixture.populateFieldWith("MultipleDropdownField", new String[] {"Option 1", "Option 2"});

        assertTrue(fixture.verifyFieldContainsValue("dropdownField", "Option, 1"));
        assertTrue(fixture.verifyFieldContains("MultipleDropdownField", new String[] {"Option 1", "Option 2"}));

        assertEquals("Option, 1", fixture.getFieldValue("DropdownField"));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("MultipleDropdownField"));

        fixture.clearField("DropdownField");
        fixture.clearField("MultipleDropdownField");
    }

    @Test
    public void testMultipleDropdownFieldSearchBox() throws Exception {
        fixture.populateDropdownSearchBoxWith("SearchBoxMultipleDropdownField", "MultipleDropdownSearchBoxOption 1");
        assertTrue(fixture.verifyTextIsPresent("MultipleDropdownSearchBoxOption 1"));
        assertTrue(fixture.verifyTextIsNotPresent("MultipleDropdownSearchBoxOption 2"));
        try {
            fixture.populateDropdownSearchBoxWith("SearchBoxMultipleDropdownField", "Option 3");
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testMultipleDropdownFieldClearOf() throws Exception {
        fixture.populateFieldWith("MultipleDropdownField", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("MultipleDropdownField", new String[] {"Option 1", "Option 2"}));

        fixture.clearFieldOf("MultipleDropdownField", new String[] {"Option 1"});
        assertTrue(fixture.verifyFieldContains("MultipleDropdownField", new String[] {"Option 2"}));
        assertFalse(fixture.verifyFieldContains("MultipleDropdownField", new String[] {"Option 1"}));

        fixture.clearField("MultipleDropdownField");
    }

    /**** Checkbox Field ****/

    @Test
    public void testCheckboxField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("CheckboxField"));
        fixture.populateFieldWith("CheckboxField", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("CheckboxField", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("CheckboxField"));
        assertTrue(fixture.verifyFieldIsNotBlank("CheckboxField"));

        // Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("[3]", "[4]", "[1]"));
        fixture.populateGridColumnRowWith("[3]", "[4]", "[1]", new String[] {"Option 1", "[2]"});
        assertTrue(fixture.verifyGridColumnRowContains("[3]", "[4]", "[1]", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getGridColumnRowValue("EditableGrid[2]", "[4]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("[3]", "[4]", "[1]"));


        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("CheckboxField[3]"));
        fixture.populateFieldWith("CheckboxField[3]", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("CheckboxField[3]", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("CheckboxField[3]"));
        assertTrue(fixture.verifyFieldIsNotBlank("CheckboxField[3]"));


        fixture.populateGridColumnRowWith("EditableGrid[2]", "CheckboxField", "[2]", new String[] {"[1]"});
        assertTrue(
                fixture.verifyGridColumnRowContains("EditableGrid[2]", "CheckboxField", "[2]", new String[] {"[1]"}));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[2]", "CheckboxField", "[2]"));
    }

    @Test
    public void testCheckboxFieldOptions() throws Exception {
        fixture.clickOnCheckboxOption("Click Me Too!");
        fixture.clickOnCheckboxOption("Click Me[2]");
    }

    @Test
    public void testCheckboxFieldOptionsShortLabel() throws Exception {
        // Need a short label to reproduce the issue from AN-94100
        fixture.clickOnCheckboxOption("longerName");
        fixture.clickOnCheckboxOption("z");
        fixture.clickOnCheckboxOption("st");

        assertEquals("longerName,z,st", fixture.getFieldValue("CheckboxeswithShortLabels"));
    }

    /**** Radio Field ****/

    @Test
    public void testRadioField() throws Exception {
        String radioFieldName = "RadioField";
        assertFalse(fixture.verifyFieldIsNotBlank(radioFieldName));
        fixture.populateFieldWith(radioFieldName, new String[] {"Option 1"});
        assertTrue(fixture.verifyFieldContains(radioFieldName, new String[] {"Option 1"}));
        assertEquals("Option 1", fixture.getFieldValue(radioFieldName));
        assertTrue(fixture.verifyFieldIsNotBlank(radioFieldName));

        if (atLeastVersion(21.1)) {
            String optionName = "Option 2";
            if (atLeastVersion(23.3)) {
                optionName = "Option 3";
            }
            radioFieldName = "CardsStyleRadioField";
            assertFalse(fixture.verifyFieldIsNotBlank(radioFieldName));
            fixture.populateFieldWith(radioFieldName, new String[] {optionName});
            assertTrue(fixture.verifyFieldContains(radioFieldName, new String[] {optionName}));
            assertEquals(optionName, fixture.getFieldValue(radioFieldName));
            assertTrue(fixture.verifyFieldIsNotBlank(radioFieldName));
        }

        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("RadioField[2]"));
        fixture.populateFieldWith("RadioField[2]", new String[] {"Option 1"});
        assertTrue(fixture.verifyFieldContains("RadioField[2]", new String[] {"Option 1"}));
        assertEquals("Option 1", fixture.getFieldValue("RadioField[2]"));
        assertTrue(fixture.verifyFieldIsNotBlank("RadioField[2]"));


        // Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[2]", "RadioField", "[1]"));
        fixture.populateGridColumnRowWith("EditableGrid[2]", "RadioField", "[1]", new String[] {"Option 1"});
        assertTrue(
                fixture.verifyGridColumnRowContains("EditableGrid[2]", "RadioField", "[1]", new String[] {"Option 1"}));
        assertEquals("Option 1", fixture.getGridColumnRowValue("EditableGrid[2]", "RadioField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[2]", "RadioField", "[1]"));
    }

    @Test
    public void testRadioFieldOptions() throws Exception {
        fixture.clickOnRadioOption("Click Me!");
        fixture.clickOnRadioOption("Click Me[2]");
        Assumptions.assumeTrue(atLeastVersion(21.1));
        fixture.clickOnRadioOption("cards style choice!");
    }

    /**** Date Field ****/

    @Test
    public void testDateField() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(fixture.getSettings().getDateFormat());
        SimpleDateFormat ddf = new SimpleDateFormat(fixture.getSettings().getDateDisplayFormat());

        fixture.populateFieldWith("DateField", new String[] {"2025-01-01 +3 days"});
        assertTrue(fixture.verifyFieldContains("DateField", new String[] {"2025-01-01 +3 days"}));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2025);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        assertEquals(df.format(DateUtils.addDays(cal.getTime(), 3)), fixture.getFieldValue("DateField"));
        assertTrue(fixture.verifyFieldIsNotBlank("DateField"));


        fixture.populateFieldWith("DateField", new String[] {"+1 month"});
        assertTrue(fixture.verifyFieldContains("DateField", new String[] {"+1 month"}));
        assertEquals(df.format(DateUtils.addMonths(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getFieldValue("DateField"));

        assertTrue(fixture.verifyFieldContains("RODateField", new String[] {"+1 month"}));
        assertEquals(ddf.format(DateUtils.addMonths(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getFieldValue("RODateField"));

        // All fields below are not within the visibility of the Chrome browser in CI tests
        // Bug described by AN-99346
        Assumptions.assumeFalse(Settings.isLinux() && isBrowser("CHROME"));

        fixture.populateFieldWith("DateField[2]", new String[] {"2015-11-15"});
        assertTrue(fixture.verifyFieldContains("DateField[2]", new String[] {"2015-11-15"}));
        assertEquals(df.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15")),
                fixture.getFieldValue("DateField[2]"));

        fixture.populateFieldWith("DateField[3]", new String[] {"+5 days"});
        assertTrue(fixture.verifyFieldContains("DateField[3]", new String[] {"+5 days"}));
        assertEquals(df.format(DateUtils.addDays(fixture.getSettings().getStartDatetime(), 5)),
                fixture.getFieldValue("DateField[3]"));

        // Editable Grid
        fixture.populateGridColumnRowWith("EditableGrid", "[7]", "[1]", new String[] {"+1 year"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "[7]", "[1]", new String[] {"+1 year"}));
        assertEquals(df.format(DateUtils.addYears(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getGridColumnRowValue("EditableGrid", "[7]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid", "[7]", "[1]"));

        // Read Only Grid
        assertTrue(fixture.verifyGridColumnRowContains("ReadOnlyGrid", "[6]", "[1]", new String[] {"+1 year"}));
        assertEquals(ddf.format(DateUtils.addYears(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getGridColumnRowValue("ReadOnlyGrid", "[6]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("ReadOnlyGrid", "[6]", "[1]"));

        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("DateField[4]"));
        fixture.populateFieldWith("DateField[4]", new String[] {"+5 days"});
        assertTrue(fixture.verifyFieldContains("DateField[4]", new String[] {"+5 days"}));
        assertEquals(df.format(DateUtils.addDays(fixture.getSettings().getStartDatetime(), 5)),
                fixture.getFieldValue("DateField[4]"));
        assertTrue(fixture.verifyFieldIsNotBlank("DateField[4]"));
    }

    @Test
    public void testDateFieldRepopulation() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(fixture.getSettings().getDateFormat());

        fixture.populateFieldWith("DateField", new String[] {"+1 month"});
        assertTrue(fixture.verifyFieldContains("DateField", new String[] {"+1 month"}));
        assertEquals(df.format(DateUtils.addMonths(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getFieldValue("DateField"));

        fixture.populateFieldWith("DateField", new String[] {"2015-11-15"});
        assertTrue(fixture.verifyFieldContains("DateField", new String[] {"2015-11-15"}));
        assertEquals(df.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15")),
                fixture.getFieldValue("DateField"));
    }

    /**** Datetime Field ****/

    @Test
    public void testDatetimeField() throws Exception {
        SimpleDateFormat dtf = new SimpleDateFormat(fixture.getSettings().getDatetimeFormat());
        SimpleDateFormat ddtf = new SimpleDateFormat(fixture.getSettings().getDatetimeDisplayFormat());

        // Check with Time combination
        fixture.populateFieldWith("DatetimeField", new String[] {"17:25"});
        Calendar cal = Calendar.getInstance();
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {
                cal.get(Calendar.YEAR) + "-" + new SimpleDateFormat("M").format(cal.getTime()) + "-" +
                        cal.get(Calendar.DAY_OF_MONTH) + " 17:25"}));
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 25);
        assertEquals(dtf.format(cal.getTime()), fixture.getFieldValue("DatetimeField"));

        // Check with DateTime combination
        fixture.populateFieldWith("DatetimeField", new String[] {"2025-01-06 17:25"});
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {"2025-01-06 17:25"}));
        cal.set(Calendar.YEAR, 2025);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 6);
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 25);
        assertEquals(dtf.format(cal.getTime()), fixture.getFieldValue("DatetimeField"));

        // Check with DateTime combination + offsets
        fixture.populateFieldWith("DatetimeField",
                new String[] {"2025-01-06 17:25 +3 years +5 months +2 day -4 minutes"});
        assertTrue(fixture.verifyFieldContains("DatetimeField",
                new String[] {"2025-01-06 17:25 +3 years +5 months +2 day -4 minutes"}));
        assertEquals(dtf.format(DateUtils.addYears(
                        DateUtils.addMonths(DateUtils.addDays(DateUtils.addMinutes(cal.getTime(), -4), 2), 5), 3)),
                fixture.getFieldValue("DatetimeField"));

        // Check with relative time
        fixture.populateFieldWith("DatetimeField", new String[] {"+1 hour"});
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {"+1 hour"}));
        assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getFieldValue("DatetimeField"));

        assertTrue(fixture.verifyFieldContains("RODatetimeField", new String[] {"+1 hour"}));
        assertEquals(ddtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getFieldValue("RODatetimeField"));

        // Check with absolute time
        fixture.populateFieldWith("DatetimeField", new String[] {"2015-11-15 14:00"});
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {"2015-11-15 14:00"}));
        assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
                fixture.getFieldValue("DatetimeField"));

        assertTrue(fixture.verifyFieldContains("RODatetimeField", new String[] {"2015-11-15 14:00"}));
        assertEquals(ddtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
                fixture.getFieldValue("RODatetimeField"));

        // All fields below are not within the visibility of the Chrome browser in CI tests
        // Bug described by AN-99346
        Assumptions.assumeFalse(Settings.isLinux() && (isBrowser("CHROME") || isBrowser("REMOTE_CHROME")));

        fixture.populateFieldWith("DatetimeField[2]", new String[] {"2015-11-15"});
        assertTrue(fixture.verifyFieldContains("DatetimeField[2]", new String[] {"2015-11-15"}));
        assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15")),
                fixture.getFieldValue("DatetimeField[2]"));

        fixture.populateFieldWith("DatetimeField[3]", new String[] {"2015-11-15 14:00"});
        assertTrue(fixture.verifyFieldContains("DatetimeField[3]", new String[] {"2015-11-15 14:00"}));
        assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
                fixture.getFieldValue("DatetimeField[3]"));

        fixture.populateFieldWith("DatetimeField[4]", new String[] {"+5 minutes"});
        assertTrue(fixture.verifyFieldContains("DatetimeField[4]", new String[] {"+5 minutes"}));
        assertEquals(dtf.format(DateUtils.addMinutes(fixture.getSettings().getStartDatetime(), 5)),
                fixture.getFieldValue("DatetimeField[4]"));

        fixture.populateFieldWith("DatetimeField[5]", new String[] {"+5 hours"});
        assertTrue(fixture.verifyFieldContains("DatetimeField[5]", new String[] {"+5 hours"}));
        assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 5)),
                fixture.getFieldValue("DatetimeField[5]"));

        fixture.populateFieldWith("DatetimeField[6]", new String[] {"+5 days"});
        assertTrue(fixture.verifyFieldContains("DatetimeField[6]", new String[] {"+5 days"}));
        assertEquals(dtf.format(DateUtils.addDays(fixture.getSettings().getStartDatetime(), 5)),
                fixture.getFieldValue("DatetimeField[6]"));

        // Side By Side Field
        fixture.populateFieldWith("DatetimeField[7]", new String[] {"+5 days"});
        assertTrue(fixture.verifyFieldContains("DatetimeField[7]", new String[] {"+5 days"}));
        assertEquals(dtf.format(DateUtils.addDays(fixture.getSettings().getStartDatetime(), 5)),
                fixture.getFieldValue("DatetimeField[7]"));

        // Grid
        fixture.populateGridColumnRowWith("EditableGrid[2]", "DatetimeField", "[1]", new String[] {"+1 hour"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[2]", "DatetimeField", "[1]",
                new String[] {"+1 hour"}));
        assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getGridColumnRowValue("EditableGrid[2]", "DatetimeField", "[1]"));
    }

    @Test
    public void testDatetimeFieldRepopulation() throws Exception {
        SimpleDateFormat dtf = new SimpleDateFormat(fixture.getSettings().getDatetimeFormat());

        fixture.populateFieldWith("DatetimeField", new String[] {"+1 hour"});
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {"+1 hour"}));
        assertEquals(dtf.format(DateUtils.addHours(fixture.getSettings().getStartDatetime(), 1)),
                fixture.getFieldValue("DatetimeField"));

        fixture.populateFieldWith("DatetimeField", new String[] {"2015-11-15 14:00"});
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {"2015-11-15 14:00"}));
        assertEquals(dtf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2015-11-15 14:00")),
                fixture.getFieldValue("DatetimeField"));
    }

    @Test
    public void testFieldLocators() throws Exception {
        fixture.populateFieldWithPlaceholderWith("text placeholder", new String[] {"Text Placeholder"});
        assertTrue(fixture.verifyFieldWithPlaceholderContains("text placeholder", new String[] {"Text Placeholder"}));
        assertEquals("Text Placeholder", fixture.getFieldWithPlaceholderValue("text placeholder"));

        fixture.populateFieldWithInstructionsWith("text instructions", new String[] {"Text Instructions"});
        assertTrue(
                fixture.verifyFieldWithInstructionsContains("text instructions", new String[] {"Text Instructions"}));
        assertEquals("Text Instructions", fixture.getFieldWithInstructionsValue("text instructions"));

        fixture.populateFieldWithTooltipWith("text tooltip", new String[] {"Text Tooltip"});
        assertTrue(fixture.verifyFieldWithTooltipContains("text tooltip", new String[] {"Text Tooltip"}));
        assertEquals("Text Tooltip", fixture.getFieldWithTooltipValue("text tooltip"));

        fixture.populateFieldWithPlaceholderWith("paragraph placeholder", new String[] {"Paragraph Placeholder"});
        assertTrue(fixture.verifyFieldWithPlaceholderContains("paragraph placeholder",
                new String[] {"Paragraph Placeholder"}));
        assertEquals("Paragraph Placeholder", fixture.getFieldWithPlaceholderValue("paragraph placeholder"));

        fixture.populateFieldWithInstructionsWith("paragraph instructions", new String[] {"Paragraph Instructions"});
        assertTrue(fixture.verifyFieldWithInstructionsContains("paragraph instructions",
                new String[] {"Paragraph Instructions"}));
        assertEquals("Paragraph Instructions", fixture.getFieldWithInstructionsValue("paragraph instructions"));

        fixture.populateFieldWithTooltipWith("paragraph tooltip", new String[] {"Paragraph Tooltip"});
        assertTrue(fixture.verifyFieldWithTooltipContains("paragraph tooltip", new String[] {"Paragraph Tooltip"}));
        assertEquals("Paragraph Tooltip", fixture.getFieldWithTooltipValue("paragraph tooltip"));

        fixture.populateFieldWithPlaceholderWith("picker placeholder", new String[] {"1"});
        // A picker loses it's placeholder when you populate it, so can't check a value that way.

        fixture.populateFieldWithInstructionsWith("picker instructions", new String[] {"2"});
        assertTrue(fixture.verifyFieldWithInstructionsContains("picker instructions", new String[] {"2"}));
        assertEquals("2", fixture.getFieldWithInstructionsValue("picker instructions"));

        fixture.populateFieldWithTooltipWith("picker tooltip", new String[] {"3"});
        assertTrue(fixture.verifyFieldWithTooltipContains("picker tooltip", new String[] {"3"}));
        assertEquals("3", fixture.getFieldWithTooltipValue("picker tooltip"));
    }

    /*** Tag Field ***/
    @Test
    public void testTagField() throws Exception {
        assertTrue(fixture.verifyTagFieldIsPresent("TagField"));
        assertTrue(fixture.verifyTagItemIsPresent("Silver Member"));
        assertTrue(fixture.verifyTagItemIsPresent("Corporate Partner"));
        assertTrue(fixture.verifyFieldContains("TagField", new String[] {"Silver Member", "Corporate Partner"}));
        assertTrue(fixture.verifyFieldContains("TagField[1]", new String[] {"Silver Member", "Corporate Partner"}));
        assertTrue(fixture.verifyFieldIsPresent("TagField"));
        fixture.clickOnTagFieldTagItem("TagField", "Silver Member");
        assertTrue(fixture.verifyFieldIsNotPresent("TagField"));  //we have click away
        //go back to all fields interface
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("All Fields");

        assertTrue(fixture.verifyFieldIsPresent("TagField"));
        fixture.clickOnTagItem("Silver Member");
        assertTrue(fixture.verifyFieldIsNotPresent("TagField"));  //we have click away
        //go back to all fields interface
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("All Fields");
    }

    /**** Picker Field ****/

    @Test
    public void testPickerFields() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("UserPicker"));
        fixture.populateFieldWith("UserPicker", new String[] {"Fitnesse User", "fitnesse twoser"});
        assertTrue(fixture.verifyFieldContains("Userpicker", new String[] {"Fitnesse User", "Fitnesse Twoser"}));
        assertEquals("Fitnesse User,Fitnesse Twoser", fixture.getFieldValue("UserPicker"));

        assertTrue(fixture.verifyFieldContains("ROUserPicker", new String[] {"Fitnesse User", "Fitnesse Twoser"}));
        assertEquals("Fitnesse User,Fitnesse Twoser", fixture.getFieldValue("ROUserPicker"));
        assertTrue(fixture.verifyFieldIsNotBlank("UserPicker"));


        fixture.clearFieldOf("UserPicker", new String[] {"Fitnesse User"});
        assertFalse(fixture.verifyFieldContains("UserPicker", new String[] {"Fitnesse User"}));
        fixture.clearFieldOf("UserPicker[1]", new String[] {"Fitnesse Twoser"});
        assertFalse(fixture.verifyFieldContains("UserPicker[1]", new String[] {"Fitnesse Twoser"}));

        fixture.populateFieldWith("CustomPicker", new String[] {"5"});
        assertTrue(fixture.verifyFieldContains("CustomPicker", new String[] {"5"}));
        assertEquals("5", fixture.getFieldValue("CustomPicker"));

        fixture.populateFieldWithContains("PartialPicker", new String[] {"2"});
        assertTrue(fixture.verifyFieldContains("PartialPicker", new String[] {"2"}));
        TimeUnit.SECONDS.sleep(1);
        assertEquals("2", fixture.getFieldValue("PartialPicker"));

        assertTrue(fixture.verifyFieldContains("ROPartialPicker", new String[] {"2"}));
        assertEquals("2", fixture.getFieldValue("ROPartialPicker"));


        // Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[4]", "[1]", "[1]"));
        fixture.populateGridColumnRowWith("EditableGrid[4]", "[1]", "[1]",
                new String[] {"Fitnesse User", "Fitnesse Twoser"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[1]", "[1]",
                new String[] {"Fitnesse User", "Fitnesse Twoser"}));
        assertEquals("Fitnesse User,Fitnesse Twoser", fixture.getGridColumnRowValue("EditableGrid[4]", "[1]", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[4]", "[1]", "[1]"));

        // Grid picker field with suggestion partially matching input
        fixture.populateGridColumnRowWithPartiallyMatchingPickerFieldSuggestion("EditableGrid[4]", "[3]", "[2]",
                new String[] {"1"});
        TimeUnit.SECONDS.sleep(1);
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid[4]", "[3]", "[2]", new String[] {"1"}));
        assertEquals("1", fixture.getGridColumnRowValue("EditableGrid[4]", "[3]", "[2]"));

        // Side By Side Field
        assertFalse(fixture.verifyFieldIsNotBlank("UserPicker[2]"));
        fixture.populateFieldWith("UserPicker[2]", new String[] {"Fitnesse User", "fitnesse twoser"});
        assertTrue(fixture.verifyFieldContains("Userpicker[2]", new String[] {"Fitnesse User", "Fitnesse Twoser"}));
        assertEquals("Fitnesse User,Fitnesse Twoser", fixture.getFieldValue("UserPicker[2]"));
        assertTrue(fixture.verifyFieldIsNotBlank("UserPicker[2]"));
    }

    @Test
    public void testRecordPickerField() {
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("Automated Testing Input");

        String randString = fixture.getRandomString(5);
        int randInt = fixture.getRandomIntegerFromTo(0, 9);
        double randDecimal = fixture.getRandomDecimalFromToWith(1.0, 2.0, 4);
        fixture.populateFieldWith("Title", new String[] {randString});
        fixture.populateFieldWith("Quantity", new String[] {String.valueOf(randInt)});
        fixture.populateFieldWith("Price", new String[] {String.valueOf(randDecimal)});
        fixture.populateFieldWith("Start Date", new String[] {"2016-02-04 02:00"});

        fixture.clickOnButton("Submit");

        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("All Fields");

        fixture.populateFieldWithContains("Record Picker", new String[] {randString});
        assertTrue(fixture.verifyFieldContains("Record Picker", new String[] {randString}));
    }

    @Test
    public void testPickerFieldBulkClear() {
        fixture.populateFieldWith("UserPicker", new String[] {"Fitnesse User", "fitnesse twoser"});
        fixture.clearField("UserPicker");
        assertEquals("", fixture.getFieldValue("UserPicker"));
    }

    /**** Gauge Field ****/

    @Test
    public void testGaugeField() throws Exception {
        assertTrue(fixture.verifyFieldIsPresent("GaugeField"));

        assertTrue(fixture.verifyGaugeFieldPercentageIs("GaugeField", "70"));
        assertEquals("70", fixture.getGaugeFieldPercentage("GaugeField"));
    }

    /**** Milestone Field ****/
    @Test
    public void testMilestoneField() throws Exception {
        assertTrue(fixture.verifyFieldIsPresent("Milestone"));

        fixture.clickOnMilestoneStep("Milestone", "Step 1");
        assertTrue(fixture.verifyMilestoneStepIs("Milestone", new String[] {"Step 1"}));
        assertEquals("Step 1", fixture.getMilestoneStep("Milestone"));

        fixture.clickOnMilestoneStep("Milestone[1]", "[2]");
        assertTrue(fixture.verifyMilestoneStepIs("Milestone", new String[] {"Step 2"}));

        // Side By Side Field
        fixture.clickOnMilestoneStep("Milestone[2]", "Step 1");
        assertTrue(fixture.verifyMilestoneStepIs("Milestone[2]", new String[] {"Step 1"}));
        assertEquals("Step 1", fixture.getMilestoneStep("Milestone[2]"));

        fixture.clickOnMilestoneStep("Milestone[2]", "[2]");
        assertTrue(fixture.verifyMilestoneStepIs("Milestone[2]", new String[] {"Step 2"}));

        // Color milestone
        assertTrue(fixture.verifyFieldIsPresent("Color Milestone"));
        fixture.clickOnMilestoneStep("Color Milestone", "Step 1");
        assertTrue(fixture.verifyMilestoneStepIs("Color Milestone", new String[] {"Step 1"}));
        assertEquals("Step 1", fixture.getMilestoneStep("Color Milestone"));
    }


    /**** Vertical Milestone Field ****/
    @Test
    public void testMilestoneFieldVertical() throws Exception {
        assertTrue(fixture.verifyFieldIsPresent("Vertical Milestone"));
        fixture.clickOnMilestoneStep("Vertical Milestone", "Step 1");
        assertTrue(fixture.verifyMilestoneStepIs("Vertical Milestone", new String[] {"Step 1"}));
        assertEquals("Step 1", fixture.getMilestoneStep("Vertical Milestone"));

        fixture.clickOnMilestoneStep("Vertical Milestone[1]", "[2]");
        assertTrue(fixture.verifyMilestoneStepIs("Vertical Milestone", new String[] {"Step 2"}));

        // Side By Side Field
        fixture.clickOnMilestoneStep("Vertical Milestone[2]", "Step 1");
        assertTrue(fixture.verifyMilestoneStepIs("Vertical Milestone[2]", new String[] {"Step 1"}));
        assertEquals("Step 1", fixture.getMilestoneStep("Vertical Milestone[2]"));

        fixture.clickOnMilestoneStep("Vertical Milestone[2]", "[2]");
        assertTrue(fixture.verifyMilestoneStepIs("Vertical Milestone[2]", new String[] {"Step 2"}));
    }

    /**** Link Field ****/

    @Test
    public void testLinkField() throws Exception {
        assertTrue(fixture.verifyLinkIsPresent("Safe Link"));
        assertTrue(fixture.verifyLinkIsNotPresent("Fake Link"));

        assertTrue(fixture.verifyLinkURLContains("Safe Link", "https://google.com/"));
        assertTrue(fixture.verifyLinkURLContains("Strong Style Link", "suite"));

        assertTrue(fixture.getLinkURL("Safe Link").equals("https://google.com/"));
        assertTrue(fixture.getLinkURL("Strong Style Link").contains("suite"));

        fixture.clickOnLink("Click link");
        assertTrue(fixture.verifyFieldContains("Link Clicked", new String[] {"Clicked"}));

        fixture.clickOnLink("Click link[2]");
        assertTrue(fixture.verifyFieldContains("Link Clicked", new String[] {"Clicked again"}));

        fixture.clickOnLink("Strong Style Link");

        assertTrue(fixture.verifyFieldContains("Link with Value", new String[] {"Link Label"}));
        assertEquals("Link Label", fixture.getFieldValue("Link with Value"));

        // Side By Side Field
        fixture.clickOnLink("Click link[3]");
        assertTrue(fixture.verifyFieldContains("Link Clicked[2]", new String[] {"Clicked"}));
    }

    /**** Icon Link Field ****/
    @Test
    public void testIconLink() {
        //Get initial values
        String iconWithAltTextLabel = "icon link alt text clicked";
        String iconWithoutAltTextLabel = "icon link clicked";
        String standaloneIconWithAltTextLabel = "Standalone icon link alt text clicked";
        String standaloneIconWithoutAltTextLabel = "Standalone icon link clicked";
        String iconWithAltText = fixture.getFieldValue(iconWithAltTextLabel);
        String iconWithoutAltText = fixture.getFieldValue(iconWithoutAltTextLabel);
        String standaloneIconWithAltText = fixture.getFieldValue(standaloneIconWithAltTextLabel);
        String standaloneIconWithoutAltText = fixture.getFieldValue(standaloneIconWithoutAltTextLabel);

        fixture.clickOnIconLink("icon link alt text");

        if (iconWithAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(iconWithAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(iconWithAltTextLabel));
        }

        fixture.clickOnIconLink("[2]");

        if (iconWithoutAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(iconWithoutAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(iconWithoutAltTextLabel));
        }

        fixture.clickOnIconLink("standalone icon link alt text");

        if (standaloneIconWithAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(standaloneIconWithAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(standaloneIconWithAltTextLabel));
        }

        fixture.clickOnIconLink("[4]");

        if (standaloneIconWithoutAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(standaloneIconWithoutAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(standaloneIconWithoutAltTextLabel));
        }
    }

    /**** Document Image Link ****/
    @Test
    public void testDocumentImageLink() {
        String documentImageWithAltTextLabel = "document image link alt text clicked";
        String documentImageWithoutAltTextLabel = "document image link clicked";
        String avatarDocumentImageWithAltTextLabel = "avatar document image link alt text clicked";
        String avatarDocumentImageWithoutAltTextLabel = "avatar document image link clicked";
        String richTextDocumentImageWithAltTextLabel = "rich text document image link clicked";
        String documentImageWithAltText = fixture.getFieldValue(documentImageWithAltTextLabel);
        String documentImageWithoutAltText = fixture.getFieldValue(documentImageWithoutAltTextLabel);
        String avatarDocumentImageWithAltText = fixture.getFieldValue(avatarDocumentImageWithAltTextLabel);
        String avatarDocumentImageWithoutAltText = fixture.getFieldValue(avatarDocumentImageWithoutAltTextLabel);
        String richTextDocumentImageWithAltText = fixture.getFieldValue(richTextDocumentImageWithAltTextLabel);

        fixture.clickOnDocumentImageLink("document image alt text");
        if (documentImageWithAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(documentImageWithAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(documentImageWithAltTextLabel));
        }

        fixture.clickOnDocumentImageLink("[2]");
        if (documentImageWithoutAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(documentImageWithoutAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(documentImageWithoutAltTextLabel));
        }

        fixture.clickOnDocumentImageLink("avatar document image alt text");
        if (avatarDocumentImageWithAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(avatarDocumentImageWithAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(avatarDocumentImageWithAltTextLabel));
        }

        fixture.clickOnDocumentImageLink("[4]");
        if (avatarDocumentImageWithoutAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(avatarDocumentImageWithoutAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(avatarDocumentImageWithoutAltTextLabel));
        }

        fixture.clickOnDocumentImageLink("rich text document image link alt text");
        if (richTextDocumentImageWithAltText.equals("clicked")) {
            assertEquals("not clicked", fixture.getFieldValue(richTextDocumentImageWithAltTextLabel));
        } else {
            assertEquals("clicked", fixture.getFieldValue(richTextDocumentImageWithAltTextLabel));
        }
    }

    @Test
    public void testBarChartDrilldown() {
        String barChartTextFieldLabel = "bar chart bar clicked";
        String barChartTextField = fixture.getFieldValue(barChartTextFieldLabel);

        assertEquals("0", barChartTextField);
        fixture.clickOnBarChartBar("Bar Chart", "[1]");
        assertEquals("1", fixture.getFieldValue(barChartTextFieldLabel));
        fixture.clickOnBarChartBar("Bar Chart[1]", "[2]");
        assertEquals("3", fixture.getFieldValue(barChartTextFieldLabel));
        fixture.clickOnBarChartBar("Bar Chart[2]", "[1]");
        assertEquals("7", fixture.getFieldValue(barChartTextFieldLabel));
    }

    @Test
    public void testDownloadChart() {
        Assumptions.assumeTrue(atLeastVersion(23.4));

        String downloadedBarChart = "Bar-Chart.png";
        String downloadedColumnChart = "Column-Chart.png";

        fixture.downloadChartImage("Bar Chart");
        File downloadedFile = new File(
                PropertiesUtilities.getProps().getProperty(Constants.DOWNLOAD_DIRECTORY) + "/" + downloadedBarChart);
        assertTrue(downloadedFile.exists());

        fixture.downloadChartImage("Column Chart");
        downloadedFile = new File(
                PropertiesUtilities.getProps().getProperty(Constants.DOWNLOAD_DIRECTORY) + "/" + downloadedColumnChart);
        assertTrue(downloadedFile.exists());

        fixture.deleteFile(downloadedBarChart);
        fixture.deleteFile(downloadedColumnChart);
    }

    @Test
    public void testColumnChartDrilldown() {
        String columnChartTextFieldLabel = "column chart column clicked";
        String columnChartTextField = fixture.getFieldValue(columnChartTextFieldLabel);

        assertEquals("0", columnChartTextField);
        fixture.clickOnColumnChartColumn("Column Chart", "[1]");
        assertEquals("1", fixture.getFieldValue(columnChartTextFieldLabel));
        fixture.clickOnColumnChartColumn("Column Chart", "[2]");
        assertEquals("3", fixture.getFieldValue(columnChartTextFieldLabel));
    }

    @Test
    public void testPieChartDrilldown() {
        String pieChartTextFieldLabel = "pie chart slice clicked";
        String pieChartTextField = fixture.getFieldValue(pieChartTextFieldLabel);

        assertEquals("0", pieChartTextField);
        fixture.clickOnPieChartPieSlice("Pie Chart", "[1]");
        assertEquals("1", fixture.getFieldValue(pieChartTextFieldLabel));
        fixture.clickOnPieChartPieSlice("Pie Chart", "[2]");
        assertEquals("3", fixture.getFieldValue(pieChartTextFieldLabel));
    }

    @Test
    public void testLineChartDrilldown() {
        String lineChartTextFieldLabel = "line chart point clicked";
        String lineChartTextField = fixture.getFieldValue(lineChartTextFieldLabel);

        assertEquals("0", lineChartTextField);
        fixture.clickOnLineChartPoint("Line Chart", "[1]");
        assertTrue(fixture.verifyFieldContainsValue(lineChartTextFieldLabel, "1"));
        fixture.clickOnLineChartPoint("Line Chart", "[2]");
        assertTrue(fixture.verifyFieldContainsValue(lineChartTextFieldLabel, "3"));
    }

    @Test
    public void testCardIsPresent() throws Exception {
        assertTrue(fixture.verifyCardIsPresent("Click card"));
        assertTrue(fixture.verifyCardIsNotPresent("Fake Card"));
    }

    /**** Card Link Field ****/

    @Test
    public void testGetContainerContents() throws Exception {
        SimpleDateFormat ddf = new SimpleDateFormat(fixture.getSettings().getDateDisplayFormat());

        JSONObject jo = new JSONObject()
                .put("Integer", "1")
                .put("Decimal Field", "1.2")
                .put("Text", "text A")
                .put("Paragraph", "paragraph A")
                .put("Date", ddf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2019-08-19")));
        JSONObject ji = fixture.getCardContents("Card Container");
        assertTrue(jo.similar(ji));

        jo = new JSONObject()
                .put("Integer", "2")
                .put("Decimal Field", "2.2")
                .put("Text", "text B")
                .put("Paragraph", "paragraph B")
                .put("Date", ddf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2019-08-20")));
        ji = fixture.getSectionContents("Section Container");
        assertTrue(jo.similar(ji));

        jo = new JSONObject()
                .put("Integer", "3")
                .put("Decimal Field", "3.2")
                .put("Text", "text C")
                .put("Paragraph", "paragraph C")
                .put("Date", ddf.format(AppianObject.getInstance(fixture.getSettings()).parseDate("2019-08-21")));
        ji = fixture.getBoxContents("Box Container");
        assertTrue(jo.similar(ji));
    }

    @Test
    public void testCardLinkField() throws Exception {
        /** Short **/
        assertEquals("Not Clicked", fixture.getFieldValue("Card Clicked"));
        fixture.clickOnCard("Click card");
        fixture.verifyFieldContains("Card Clicked", new String[] {"First Card"});
        /** Medium **/
        fixture.clickOnCard("Click card[2]");
        String secondCardName = atLeastVersion(21.2) ? "Second Card - SEMI_ROUNDED" : "Second Card";
        fixture.verifyFieldContains("Card Clicked", new String[] {secondCardName});
        /** Tall **/
        fixture.clickOnCard("Click card[3]");
        String thirdCardName = atLeastVersion(21.2) ? "Third Card - ROUNDED" : "Third Card";
        fixture.verifyFieldContains("Card Clicked", new String[] {thirdCardName});
    }

    /**** Card Choice Field ****/

    @Test
    public void testCardChoiceFieldSelectOne() throws Exception {
        String fieldText = "Card Choices - Select One";
        String firstChoiceText = "First Choice";
        String secondChoiceText = "Second Choice";
        assertTrue(fixture.verifyTextIsPresent(fieldText));
        assertTrue(fixture.verifyTextIsPresent(firstChoiceText));
        assertTrue(fixture.verifyTextIsPresent(secondChoiceText));
        fixture.clickOnCardChoice(firstChoiceText, fieldText);
        assertTrue(fixture.verifyCardChoiceIsSelected(firstChoiceText, fieldText));
        assertFalse(fixture.verifyCardChoiceIsSelected(secondChoiceText, fieldText));
        fixture.clickOnCardChoice(secondChoiceText, fieldText);
        assertFalse(fixture.verifyCardChoiceIsSelected(firstChoiceText, fieldText));
        assertTrue(fixture.verifyCardChoiceIsSelected(secondChoiceText, fieldText));
    }

    @Test
    public void testCardChoiceFieldSelectMultiple() throws Exception {
        String fieldText = "Card Choices - Select Multiple";
        String firstChoiceText = "First Choice";
        String secondChoiceText = "Second Choice";
        assertTrue(fixture.verifyTextIsPresent(fieldText));
        assertTrue(fixture.verifyTextIsPresent(firstChoiceText));
        assertTrue(fixture.verifyTextIsPresent(secondChoiceText));
        fixture.clickOnCardChoice(firstChoiceText, fieldText);
        assertTrue(fixture.verifyCardChoiceIsSelected(firstChoiceText, fieldText));
        assertFalse(fixture.verifyCardChoiceIsSelected(secondChoiceText, fieldText));
        fixture.clickOnCardChoice(secondChoiceText, fieldText);
        assertTrue(fixture.verifyCardChoiceIsSelected(firstChoiceText, fieldText));
        assertTrue(fixture.verifyCardChoiceIsSelected(secondChoiceText, fieldText));
    }

    /**** File Upload Field ****/

    @Test
    public void testFileUploadField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("FileUploadField"));
        fixture.populateFieldWith("FileUploadField", startPng);
        assertTrue(fixture.verifyFieldContains("FileUploadField", startPng));
        assertEquals("FileUploadImage.png", fixture.getFieldValue("FileUploadField"));
        assertTrue(fixture.verifyFieldIsNotBlank("FileUploadField"));

        fixture.clearField("FileUploadField");

        // Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[3]",
                "FileUploadField",
                "[1]"));
        fixture.populateGridColumnRowWith(
                "EditableGrid[3]",
                "FileUploadField",
                "[1]",
                startPng);
        assertTrue(fixture.verifyGridColumnRowContains(
                "EditableGrid[3]",
                "FileUploadField",
                "[1]",
                startPng));
        assertEquals("FileUploadImage.png", fixture.getGridColumnRowValue("EditableGrid[3]", "FileUploadField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[3]",
                "FileUploadField",
                "[1]"));
    }

    @Test
    public void testMultipleFileUploadField() throws Exception {
        assertFalse(fixture.verifyFieldIsNotBlank("MultipleFileUploadField"));
        fixture.populateFieldWith("MultipleFileUploadField", startPng);
        assertTrue(fixture.verifyFieldContains("MultipleFileUploadField", startPng));
        assertEquals("FileUploadImage.png", fixture.getFieldValue("MultipleFileUploadField"));
        assertTrue(fixture.verifyFieldIsNotBlank("MultipleFileUploadField"));


        fixture.populateFieldWith("MultipleFileUploadField", propertiesPng);
        assertTrue(fixture.verifyFieldContains("MultipleFileUploadField", propertiesPng));
        assertEquals("FileUploadImage.png:FileUploadImage2.png", fixture.getFieldValue("MultipleFileUploadField"));

        fixture.clearField("MultipleFileUploadField"); // Should clear all fields
        // Should not throw an error if the upload field doesn't have any uploaded files
        fixture.clearField("MultipleFileUploadField");

        // Grid
        assertFalse(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[3]",
                "MultipleFileUploadField",
                "[1]"));
        fixture.populateGridColumnRowWith(
                "EditableGrid[3]",
                "MultipleFileUploadField",
                "[1]",
                startPng);
        assertTrue(fixture.verifyGridColumnRowContains(
                "EditableGrid[3]",
                "MultipleFileUploadField",
                "[1]",
                startPng));
        assertEquals("FileUploadImage.png",
                fixture.getGridColumnRowValue("EditableGrid[3]", "MultipleFileUploadField", "[1]"));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("EditableGrid[3]",
                "MultipleFileUploadField",
                "[1]"));

        // Grid
        fixture.populateGridColumnRowWith(
                "EditableGrid[3]",
                "MultipleFileUploadField",
                "[1]",
                propertiesPng);
        assertTrue(fixture.verifyGridColumnRowContains(
                "EditableGrid[3]",
                "MultipleFileUploadField",
                "[1]",
                propertiesPng));
        assertEquals("FileUploadImage.png:FileUploadImage2.png",
                fixture.getGridColumnRowValue("EditableGrid[3]", "MultipleFileUploadField", "[1]"));
    }

    @Test
    public void testFileUploadFieldPopulateType() throws Exception {
        fixture.populateFieldWith("FILE_UPLOAD", "[3]", startPng);
        assertTrue(fixture.verifyFieldContains("FileUploadField[2]", startPng));
    }

    /**** Image Field ****/

    @Test
    public void testImageField() throws Exception {
        assertTrue(fixture.verifyFieldContains("ImageField", new String[] {"not done"}));
        assertTrue(fixture.verifyFieldInSectionContains("ImageField", "Images", new String[] {"not done"}));
        assertEquals("not done", fixture.getFieldValue("ImageField"));
        assertTrue(fixture.verifyFieldIsNotBlank("ImageField"));


        // Grid
        assertEquals(fixture.getGridColumnRowValue("ImageGrid", "ImageField", "[1]"), "done");
        assertTrue(fixture.verifyGridColumnRowContains("ImageGrid", "ImageField", "[1]", new String[] {"done"}));
        assertTrue(fixture.verifyGridColumnRowIsNotBlank("ImageGrid",
                "ImageField",
                "[1]"));
    }

    /**** Stamp Field ****/
    @Test
    public void testStampField() throws Exception {
        assertTrue(fixture.verifyStampFieldContainsText("StampField Label", "firstStampFieldText"));
        assertTrue(fixture.verifyStampFieldContainsText("StampField Label[2]", "secondStampFieldText"));
    }

    /**** Grid ****/

    @Test
    public void testGridContains() throws Exception {
        assertTrue(fixture.verifyGridColumnRowContains("PagingGrid", "[2]", "[1]", new String[] {"Value 1"}));
    }

    @Test
    public void testGridSort() throws Exception {
        fixture.sortGridByColumn("PagingGrid", "Column Label");
        assertTrue(
                fixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] {"Value 1"}));
        fixture.sortGridByColumn("PagingGrid[1]", "Column Label 1");
        assertTrue(fixture.verifyGridColumnRowContains("PagingGrid[1]", "[2]", "[1]", new String[] {"Value 9"}));
        fixture.sortGridByColumn("PagingGrid[1]", "Column Label 2");

        assertTrue(fixture.verifyGridColumnRowContains("[6]", "Column Label 2", "[1]", new String[] {"Description 1"}));
        fixture.sortGridByColumn("[6]", "Column Label 2");
        assertTrue(fixture.verifyGridColumnRowContains("[6]", "[3]", "[1]", new String[] {"Description 9"}));
    }

    @Test
    public void testGridClickOnCell() throws Exception {
        assertEquals((long) 3, (long) fixture.countGridRows("RemovableRowsGrid"));
        fixture.clickOnGridColumnRow("RemovableRowsGrid", "[2]", "[3]");
        assertEquals((long) 2, (long) fixture.countGridRows("RemovableRowsGrid"));
        assertTrue(fixture.verifyGridColumnRowContains("RemovableRowsGrid", "[1]", "[2]", new String[] {"Item 2"}));

        fixture.clickOnGridColumnRow("RemovableRowsGrid", "[3]", "[2]");
        assertEquals((long) 1, (long) fixture.countGridRows("RemovableRowsGrid"));
        assertTrue(fixture.verifyGridColumnRowContains("RemovableRowsGrid", "[1]", "[1]", new String[] {"Item 1"}));

        fixture.clickOnGridColumnRow("RemovableRowsGrid", "[3]", "[]");
        assertEquals((long) 0, (long) fixture.countGridRows("RemovableRowsGrid"));
    }

    @Test
    public void testGridNavigation() throws Exception {
        assertTrue(
                fixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] {"Value 1"}));
        fixture.clickOnGridNavigation("PagingGrid", "next");
        assertTrue(
                fixture.verifyGridColumnRowContains("PagingGrid", "Column Label 1", "[1]", new String[] {"Value 6"}));
        fixture.clickOnGridNavigation("PagingGrid[1]", "PREVIOUS");

        assertTrue(fixture.verifyGridColumnRowContains("PagingGrid[1]", "Column Label 1", "[1]",
                new String[] {"Value 1"}));
        fixture.clickOnGridNavigation("PagingGrid[1]", "last");
        assertTrue(fixture.verifyGridColumnRowContains("[6]", "Column Label 1", "[1]", new String[] {"Value 16"}));
        fixture.clickOnGridNavigation("[6]", "FIRST");
        assertTrue(fixture.verifyGridColumnRowContains("[6]", "Column Label 1", "[1]", new String[] {"Value 1"}));

        try {
            fixture.clickOnGridNavigation("[6]", "Invalid");
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }
    }

    @Test
    public void testGridRowSelect() throws Exception {
        assertFalse(fixture.verifyGridRowIsSelected("EditableGrid", "[1]"));
        fixture.selectGridRow("EditableGrid", "[1]");
        assertTrue(fixture.verifyGridRowIsSelected("EditableGrid", "[1]"));

        assertFalse(fixture.verifyGridRowIsSelected("PagingGrid[1]", "[4]"));
        fixture.selectGridRow("PagingGrid[1]", "[4]");
        assertTrue(fixture.verifyGridRowIsSelected("PagingGrid[1]", "[4]"));

        assertFalse(fixture.verifyGridRowIsSelected("[6]", "[3]"));
        fixture.selectGridRow("[6]", "[3]");
        assertTrue(fixture.verifyGridRowIsSelected("[6]", "[3]"));

        assertFalse(fixture.verifyGridRowIsSelected("RowHighlightGrid", "[2]"));
        fixture.selectGridRow("RowHighlightGrid", "[2]");
        assertTrue(fixture.verifyGridRowIsSelected("RowHighlightGrid", "[2]"));
    }

    @Test
    public void testGridAddRow() throws Exception {
        fixture.clickOnGridAddRowLink("Editable");
        fixture.populateGridColumnRowWith("EditableGrid", "TextField", "[2]", new String[] {"Row 2"});
        assertTrue(fixture.verifyGridColumnRowContains("EditableGrid", "TextField", "[2]", new String[] {"Row 2"}));
        fixture.clickOnGridAddRowLink("EditableGrid[1]");
        fixture.clickOnGridAddRowLink("[1]");
    }

    @Test
    public void testGridSelectAll() throws Exception {
        fixture.selectAllRowsInGrid("PagingGrid");
        assertTrue(fixture.verifyGridRowIsSelected("PagingGrid", "[1]"));
        fixture.selectAllRowsInGrid("PagingGrid[1]");
        assertFalse(fixture.verifyGridRowIsSelected("PagingGrid", "[2]"));
        fixture.selectAllRowsInGrid("[6]");
    }

    @Test
    public void testGridRowCount() throws Exception {
        Integer count = fixture.countGridRows("EditableGrid");

        fixture.clickOnGridAddRowLink("EditableGrid");
        assertEquals((long) count + 1, (long) fixture.countGridRows("EditableGrid"));
    }

    @Test
    public void testGridGetTotalCount() throws Exception {
        assertEquals(fixture.getGridTotalCount("PagingGrid"), 100);
    }

    @Test
    public void testGridGetRowCount() throws Exception {
        assertEquals(fixture.getGridRowCount("PagingGrid"), 5);
    }

    @Test
    public void testGridGetRowCountOnePage() throws Exception {
        assertEquals(fixture.getGridRowCount("RowHighlightGrid"), 5);
    }

    @Test
    public void testGridGetRowTotalOnePage() throws Exception {
        assertEquals(fixture.getGridTotalCount("RowHighlightGrid"), 5);
    }

    @Test
    public void testFindGridIndex() throws Exception {
        assertEquals("[6]", fixture.getGridIndex("*;Column Label 1"));
    }

    @Test
    public void testFindGridIndexWithException() throws Exception {
        assertThrows(GenericTestException.class, () -> {
            assertEquals("1", fixture.getGridIndex("*"));
        });
    }

    @Test
    public void testFindGridIndexWithTimeout() throws Exception {
        assertThrows(TimeoutException.class, () -> {
            assertEquals("1", fixture.getGridIndex("qwerty"));
        });
    }

    @Test
    public void testGridRegex() throws Exception {
        // Regex
        assertEquals(fixture.getRegexGroupFromGridColumnRowValue("([a-zA-Z0-9]{5})", 1, "PagingGrid", "Column Label 1",
                "[2]"), "Value");
    }

    @Test
    public void testGetGridStandardContents() throws Exception {
        // Grid with normal labels
        JSONObject expectedGrid = new JSONObject();
        JSONArray expectedContents = new JSONArray();
        for (int i = 1; i <= 2; i++) {
            JSONObject expectedRow = new JSONObject();
            expectedRow.put("id", String.valueOf(i));
            expectedRow.put("value", "Value " + i);
            expectedRow.put("description", "Description " + i);
            expectedContents.put(i - 1, expectedRow);
        }
        expectedGrid.put("Standard Grid", expectedContents);
        JSONObject actualGrid = fixture.getGridContents("Standard Grid");
        assertTrue(expectedGrid.similar(actualGrid));
    }

    @Test
    public void testGetGridAbnormalContents() throws Exception {
        // Grid with duplicate and blank labels
        JSONObject expectedGrid = new JSONObject();
        JSONArray expectedContents = new JSONArray();
        for (int i = 1; i <= 2; i++) {
            JSONObject expectedRow = new JSONObject();
            expectedRow.put("id", String.valueOf(i));
            expectedRow.put("[2]", "Value " + i);
            expectedRow.put("id[2]", "Description " + i);
            expectedContents.put(i - 1, expectedRow);
        }
        expectedGrid.put("Abnormal Labels", expectedContents);
        JSONObject actualGrid = fixture.getGridContents("Abnormal Labels");
        assertTrue(expectedGrid.similar(actualGrid));
    }

    /**** Button ****/

    @Test
    public void testButton() throws Exception {
        assertTrue(fixture.verifyButtonIsPresent("Cancel"));
        assertTrue(fixture.verifyButtonIsNotPresent("FakeButton"));
        assertFalse(fixture.verifyButtonIsNotPresent("Cancel"));

        // Side By Side Field
        assertTrue(fixture.verifyButtonIsPresent("Button"));
        assertTrue(fixture.verifyFieldContains("Button Clicked", new String[] {"Not clicked"}));
        fixture.clickOnButton("Button");
        assertTrue(fixture.verifyFieldContains("Button Clicked", new String[] {"Clicked"}));
    }

    @Test
    public void testButtonWithToolTip() {
        fixture.clickOnButtonWithTooltip("my button tooltip");
        fixture.verifyFieldContains("buttonWithTooltipClicked", new String[] {"clicked"});
    }

    @Test
    public void testButtonWithLoadingIndicator() {
        Assumptions.assumeTrue(atLeastVersion(21.2));
        fixture.clickOnButton("BtnWithLoadingIndicator");
        fixture.verifyFieldContains("buttonWithLoadingIndicatorClicked", new String[] {"clicked"});
    }

    @Test
    public void testButtonIsEnabled() throws Exception {
        assertTrue(fixture.verifyButtonIsEnabled("Enabled"));
        assertTrue(fixture.verifyButtonIsDisabled("Disabled"));
    }

    @Test
    public void testButtonInsideGrid() {
        assertEquals("BTN INSIDE GRID",
                fixture.getGridColumnRowValue("Grid with a button", "Column One", "[1]"));
    }

    /**** Video ****/

    @Test
    public void testVideoPresence() throws Exception {
        assertTrue(fixture.verifyVideoIsPresent("testSource"));
        assertTrue(fixture.verifyVideoIsPresent("testSource[1]"));
        assertTrue(fixture.verifyVideoIsNotPresent("testSource2"));
    }

    /**** Web Content ****/

    @Test
    public void testWebContentPresence() throws Exception {
        assertTrue(fixture.verifyWebContentIsPresent("google.com"));
        assertTrue(fixture.verifyWebContentIsPresent("google.com[1]"));
        assertTrue(fixture.verifyWebContentIsNotPresent("testSource"));
    }

    /**** Section ****/

    @Test
    public void testSectionPopulate() throws Exception {
        fixture.populateFieldInSectionWith("[1]", "Edge Cases", new String[] {"noLabel"});
        assertTrue(fixture.verifyFieldInSectionContains("[1]", "Edge Cases", new String[] {"noLabel"}));
        assertEquals("noLabel", fixture.getFieldInSectionValue("[1]", "Edge Cases"));

        fixture.populateFieldInSectionWith("TextField", "Edge Cases", new String[] {"textFieldInSection"});
        assertTrue(
                fixture.verifyFieldInSectionContains("TextField", "Edge Cases", new String[] {"textFieldInSection"}));
        assertEquals("textFieldInSection", fixture.getFieldInSectionValue("TextField", "Edge Cases"));

        // Regex
        assertEquals("text",
                fixture.getRegexGroupFromFieldInSectionValue("([a-zA-Z0-9]{4})", 1, "TextField", "Edge Cases"));
    }

    @Test
    public void testSectionIsPresent() throws Exception {
        assertTrue(fixture.verifySectionIsPresent("Basic Fields"));
        assertTrue(fixture.verifySectionIsNotPresent("Fake Fields"));
    }

    @Test
    public void testSectionToggle() throws Exception {
        assertTrue(fixture.verifySectionIsPresent("Basic Fields"));
        assertTrue(fixture.verifySectionIsNotPresent("Fake Fields"));
        fixture.toggleSectionVisibility("Basic Fields");
        assertTrue(fixture.verifyFieldIsNotPresent("ROTextField"));
        fixture.toggleSectionVisibility("Basic Fields");
        assertTrue(fixture.verifyFieldIsPresent("ROTextField"));

        //sections with labels of different sizes, icon and labelHeadingTag
        Assumptions.assumeTrue(atLeastVersion(21.2));
        String[] sectionNames = {"section with extra small label and icon", "section with large label and icon",
                "section with labelHeadingTag h4"};
        for (String sectionName : sectionNames) {
            fixture.toggleSectionVisibility(sectionName);
            assertTrue(fixture.verifyFieldIsNotPresent("a field in " + sectionName));
            fixture.toggleSectionVisibility(sectionName);
            assertTrue(fixture.verifyFieldIsPresent("a field in " + sectionName));
        }
    }

    @Test
    public void testSectionValidation() throws Exception {
        fixture.clickOnButton("Submit");

        assertTrue(
                fixture.verifySectionContainsValidationMessage("Validations", new String[] {"RequiredField is required",
                        "ValidationField is invalid"}));
        assertEquals("RequiredField is required,ValidationField is invalid",
                fixture.getSectionValidationMessage("Validation"));
    }

    /**** Signature field ****/

    @Test
    public void testSignatureField() throws Exception {
        Assumptions.assumeTrue(atLeastVersion(20.4));
        assertTrue(fixture.verifySignatureFieldIsPresent("my signature label"));
        assertTrue(fixture.verifySignatureFieldIsPresent("[1]"));
        assertTrue(fixture.verifySignatureFieldIsPresent("my signature label[1]"));
        fixture.clickOnSignatureField("my signature label");
        fixture.drawSignature();
    }


    /**** Validation ****/

    @Test
    public void testFieldValidations() throws Exception {
        fixture.clickOnButton("Submit");

        assertTrue(fixture.verifyFieldContainsValidationMessage("RequiredField", new String[] {"A value is required"}));
        assertTrue(
                fixture.verifyFieldContainsValidationMessage("RequiredField[1]", new String[] {"A value is required"}));
        assertTrue(fixture.verifyFieldContainsValidationMessage("RequiredField", new String[] {"A value is required"}));
        assertEquals("A value is required", fixture.getFieldValidationMessage("RequiredField"));

        fixture.populateFieldWith("ValidationField", new String[] {"-1"});
        assertTrue(fixture.verifyFieldContainsValidationMessage("ValidationField",
                new String[] {"Value must be greater than 0",
                        "Value must be even"}));
        assertEquals("Value must be greater than 0,Value must be even",
                fixture.getFieldValidationMessage("ValidationField"));
    }

    @Test
    public void testFieldPresence() throws Exception {
        assertTrue(fixture.verifyFieldIsPresent("TextField"));
        assertTrue(fixture.verifyFieldIsPresent("TextField[1]"));
        assertTrue(fixture.verifyFieldIsPresent("[1]"));

        assertTrue(fixture.verifyFieldIsNotPresent("TestField"));
        assertTrue(fixture.verifyFieldIsNotPresent("TextField[10]"));
        assertTrue(fixture.verifyFieldIsNotPresent("[1000]"));
    }

    @Test
    public void testSaveChanges() throws Exception {
        fixture.clickOnSaveChanges();
        if (fixture.errorIsPresent()) {
            fixture.clickOnButton("OK");
        }
    }

    @Test
    public void testDownloadFile() throws Exception {
        String downloadedPng = "iconIndicatorFaceHappy.png";
        File downloadedFile = new File(
                PropertiesUtilities.getProps().getProperty(Constants.DOWNLOAD_DIRECTORY) + "/" + downloadedPng);

        fixture.clickOnLink("Document Download");
        assertTrue(downloadedFile.exists()); // Verify file in on file system

        fixture.deleteFile(downloadedPng);
        assertFalse(downloadedFile.exists()); // Verify file has been deleted

        String fitnesseProperties = "../somefile.txt";
        File systemFile = new File(
                PropertiesUtilities.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) + "/somefile.txt");
        systemFile.createNewFile();
        assertTrue(systemFile.exists()); // Verify file is on file system
        fixture.deleteFile(fitnesseProperties);
        assertTrue(systemFile.exists()); // Verify file has not been deleted
        systemFile.delete();
    }

    @Test
    public void testDocumentViewerPresence() throws Exception {
        assertTrue(fixture.verifyFieldIsPresent("DocumentViewerField"));
    }
}
