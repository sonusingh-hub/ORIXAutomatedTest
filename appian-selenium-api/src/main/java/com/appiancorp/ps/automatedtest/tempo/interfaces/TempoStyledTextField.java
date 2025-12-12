package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoStyledTextField extends TempoTextField {

    private static final Logger LOG = LogManager.getLogger(TempoStyledTextField.class);
    private static final String XPATH_RELATIVE_STYLED_TEXT_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeStyledTextFieldInput");

    public static TempoStyledTextField getInstance(Settings settings) {
        return new TempoStyledTextField(settings);
    }

    private TempoStyledTextField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        WebElement textField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STYLED_TEXT_FIELD_INPUT));
        textField.clear();
        textField.click();
        // For some specific browsers ie Firefox, sendKeys doesn't produce the proper formatting in the box
        // **bold** will enter the literal string "**bold**" instead of a bolded word. This seems to fix it
        for (char c : fieldValue.toCharArray()) {
            textField.sendKeys(String.valueOf(c));
        }
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STYLED_TEXT_FIELD_INPUT))
                        .getAttribute("innerHTML");
        if (LOG.isDebugEnabled()) {
            LOG.debug("STYLED TEXT FIELD VALUE : " + value);
        }

        return value;
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        WebElement textField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STYLED_TEXT_FIELD_INPUT));
        textField.clear();
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STYLED_TEXT_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
