package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.test.AbstractLoginTest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TempoFixtureBoxLayoutTest extends AbstractLoginTest<TempoFixture> {

    private String[] startPng = {new File("src/test/resources/images/FileUploadImage.png").getAbsolutePath()};

    public TempoFixtureBoxLayoutTest() {
        super(new TempoFixture());
    }

    @BeforeEach
    public void setUpInterface() throws Exception {
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("Box Layout");
    }

    @AfterEach
    public void tearDownInterface() throws Exception {
        fixture.clickOnButton("Cancel");
    }

    @Test
    public void testTextField() throws Exception {
        fixture.populateFieldWith("TextField", new String[] {"text"});
        assertTrue(fixture.verifyFieldContains("TextField", new String[] {"text"}));
        assertEquals("text", fixture.getFieldValue("TextField"));

        assertTrue(fixture.verifyFieldContains("ROTextField", new String[] {"text"}));
        assertEquals("text", fixture.getFieldValue("ROTextField"));

        fixture.populateFieldWith("StyledText", new String[] {"text **bold**"});
        assertEquals("<p>text <strong>bold</strong></p>", fixture.getFieldValue("StyledText"));
    }

    @Test
    public void testDateField() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(fixture.getSettings().getDateFormat());
        fixture.populateFieldWith("DateField", new String[] {"2025-01-01 +3 days"});
        assertTrue(fixture.verifyFieldContains("DateField", new String[] {"2025-01-01 +3 days"}));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2025);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        assertEquals(df.format(DateUtils.addDays(cal.getTime(), 3)), fixture.getFieldValue("DateField"));
    }

    @Test
    public void testParagraphField() throws Exception {
        fixture.populateFieldWith("[3]", new String[] {"paragraph"});
        assertTrue(fixture.verifyFieldContains("[3]", new String[] {"paragraph"}));
        assertEquals("paragraph", fixture.getFieldValue("[3]"));

        assertTrue(fixture.verifyFieldContains("RoParagraphField", new String[] {"paragraph"}));
        assertEquals("paragraph", fixture.getFieldValue("ROParagraphField"));
    }

    @Test
    public void testDatetimeField() throws Exception {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dtf = new SimpleDateFormat(fixture.getSettings().getDatetimeFormat());
        // Check with Time combination
        fixture.populateFieldWith("DatetimeField", new String[] {"17:25"});
        assertTrue(fixture.verifyFieldContains("DatetimeField", new String[] {
                cal.get(Calendar.YEAR) + "-" + new SimpleDateFormat("M").format(cal.getTime()) + "-" +
                        cal.get(Calendar.DAY_OF_MONTH) + " 17:25"}));
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 25);
        assertEquals(dtf.format(cal.getTime()), fixture.getFieldValue("DatetimeField"));
    }

    @Test
    public void testEncryptedField() throws Exception {
        fixture.populateFieldWithValue("[5]", "encrypted");
        assertTrue(fixture.verifyFieldContainsValue("[5]", "encrypted"));
        assertEquals("encrypted", fixture.getFieldValue("EncryptedTextField"));

        assertTrue(fixture.verifyFieldContains("ROEncryptedTextField", new String[] {"encrypted"}));
        assertEquals("encrypted", fixture.getFieldValue("ROEncryptedTextField"));
    }

    @Test
    public void testDropdownFieldField() throws Exception {
        fixture.populateFieldWithValue("Dropdownfield[1]", "Option 2");
        assertTrue(fixture.verifyFieldContainsValue("DropdownField[1]", "Option 2"));
        assertEquals("Option 2", fixture.getFieldValue("Dropdownfield"));
    }

    @Test
    public void testMultipleDropdownField() throws Exception {
        fixture.populateFieldWith("MultipleDropdownField", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("MultipleDropdownField", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("MultipleDropdownField"));
    }

    @Test
    public void testIntegerField() throws Exception {
        fixture.populateFieldWith("IntegerField", new String[] {"1"});
        assertTrue(fixture.verifyFieldContains("IntegerField", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("IntegerField"));

        assertTrue(fixture.verifyFieldContains("ROIntegerField", new String[] {"1"}));
        assertEquals("1", fixture.getFieldValue("ROIntegerField"));
    }

    @Test
    public void testRadioField() throws Exception {
        fixture.populateFieldWith("RadioField", new String[] {"[1]"});
        assertTrue(fixture.verifyFieldContains("RadioField", new String[] {"[1]"}));
        assertEquals("Option 1", fixture.getFieldValue("RadioField"));
    }

    @Test
    public void testCheckboxField() throws Exception {
        fixture.populateFieldWith("CheckboxField", new String[] {"Option 1", "Option 2"});
        assertTrue(fixture.verifyFieldContains("CheckboxField", new String[] {"Option 1", "Option 2"}));
        assertEquals("Option 1,Option 2", fixture.getFieldValue("CheckboxField"));
        // uncheck checkboxes to prevent failures on other tests that interact with these
        fixture.clickOnCheckboxOption("Option 1");
        fixture.clickOnCheckboxOption("Option 2");
    }

    @Test
    public void testDecimalField() throws Exception {
        fixture.populateFieldWith("DecimalField", new String[] {"2.2"});
        assertTrue(fixture.verifyFieldContains("decimalField", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getFieldValue("DecimalField"));

        assertTrue(fixture.verifyFieldContains("RODecimalField", new String[] {"2.2"}));
        assertEquals("2.2", fixture.getFieldValue("RODecimalField"));
    }

    @Test
    public void testFileUploadField() throws Exception {
        fixture.populateFieldWith("FileUploadField", startPng);
        assertTrue(fixture.verifyFieldContains("FileUploadField", startPng));
        assertEquals("FileUploadImage.png", fixture.getFieldValue("FileUploadField"));
    }

    @Test
    public void testMultipleFileUploadField() throws Exception {
        fixture.populateFieldWith("MultipleFileUploadField", startPng);
        assertTrue(fixture.verifyFieldContains("MultipleFileUploadField", startPng));
        assertEquals("FileUploadImage.png", fixture.getFieldValue("MultipleFileUploadField"));
    }

    @Test
    public void testBarcodeField() throws Exception {
        fixture.populateFieldWith("BarcodeField", new String[] {"barcode123"});
        assertTrue(fixture.verifyFieldContains("BarcodeField", new String[] {"barcode123"}));
        assertEquals("barcode123", fixture.getFieldValue("BarcodeField"));

        assertTrue(fixture.verifyFieldContains("ROBarcodeField", new String[] {"barcode123"}));
        assertEquals("barcode123", fixture.getFieldValue("ROBarcodeField"));
    }

    @Test
    public void testBoxIsPresent() throws Exception {
        assertTrue(fixture.verifyBoxIsPresent("Box"));
        assertTrue(fixture.verifyBoxIsNotPresent("Fake Box"));
    }

    @Test
    public void testToggleCheckboxes() throws Exception {
        Assumptions.assumeTrue(atLeastVersion(23.3));
        fixture.clickOnCheckboxOption("Option 1");
        assertTrue(fixture.verifyFieldContains("CheckboxField", new String[] {"Option 1"}));
        fixture.populateFieldWith("CheckboxField", new String[] {"Option 1"});
        fixture.clickOnCheckboxOption("Option 1");

        fixture.clickOnCheckboxOption("Option 3");
        assertTrue(fixture.verifyFieldContains("CheckboxFieldCardStyle", new String[] {"Option 3"}));
        fixture.populateFieldWith("CheckboxFieldCardStyle", new String[] {"Option 3"});
        assertEquals("", fixture.getFieldValue("CheckboxFieldCardStyle"));
    }

    @Test
    public void testToggleRadioButtons() throws Exception {
        Assumptions.assumeTrue(atLeastVersion(23.3));
        fixture.clickOnRadioOption("Option 1");
        assertTrue(fixture.verifyFieldContains("RadioField", new String[] {"Option 1"}));

        fixture.clickOnRadioOption("Option 3");
        assertTrue(fixture.verifyFieldContains("RadioFieldCardStyle", new String[] {"Option 3"}));
    }

    @Test
    public void testBoxToggle() throws Exception {
        fixture.toggleBoxVisibility("Box");
        assertTrue(fixture.verifyFieldIsNotPresent("ROTextField"));
        fixture.toggleBoxVisibility("Box");
        assertTrue(fixture.verifyFieldIsPresent("ROTextField"));

        fixture.toggleBoxVisibility("Box[1]");
        assertTrue(fixture.verifyFieldIsNotPresent("ROTextField"));
        fixture.toggleBoxVisibility("Box[1]");
        assertTrue(fixture.verifyFieldIsPresent("ROTextField"));

        fixture.toggleBoxVisibility("[1]");
        assertTrue(fixture.verifyFieldIsNotPresent("ROTextField"));
        fixture.toggleBoxVisibility("[1]");
        assertTrue(fixture.verifyFieldIsPresent("ROTextField"));
    }

    @Test
    public void testBoxToggleTwoBoxesWithSameName() {
        //Adding a test for the case where there are two boxes with the same name
        fixture.toggleBoxVisibility("Box[2]");
        assertTrue(fixture.verifyFieldIsNotPresent("BoxTwoROTextField"));
        fixture.toggleBoxVisibility("Box[2]");
        assertTrue(fixture.verifyFieldIsPresent("BoxTwoROTextField"));

        fixture.toggleBoxVisibility("[2]");
        assertTrue(fixture.verifyFieldIsNotPresent("BoxTwoROTextField"));
        fixture.toggleBoxVisibility("[2]");
        assertTrue(fixture.verifyFieldIsPresent("BoxTwoROTextField"));
    }

    @Test
    public void testConfirmationDialogHeader() {
        fixture.clickOnButton("Submit");
        assertTrue(fixture.verifyConfirmationDialogHeaderIsPresent("Confirmation Header"));
        fixture.clickOnButton("No");
    }

    @Test
    public void testConfirmationDialogMessage() {
        fixture.clickOnButton("Submit");
        assertTrue(fixture.verifyConfirmationDialogMessageIsPresent("Confirmation message"));
        fixture.clickOnButton("No");
    }
}
