package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoEncryptedTextField extends TempoTextField {

    private static final Logger LOG = LogManager.getLogger(TempoEncryptedTextField.class);
    private static final String XPATH_RELATIVE_ENCRYPTED_TEXT_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeEncryptedTextFieldInput");

    public static TempoEncryptedTextField getInstance(Settings settings) {
        return new TempoEncryptedTextField(settings);
    }

    private TempoEncryptedTextField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        WebElement textField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_ENCRYPTED_TEXT_FIELD_INPUT));
        textField.clear();
        textField.sendKeys(fieldValue);
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String value =
                fieldLayout.findElement(By.xpath(XPATH_RELATIVE_ENCRYPTED_TEXT_FIELD_INPUT)).getAttribute("value");
        if (LOG.isDebugEnabled()) {
            LOG.debug("TEXT FIELD VALUE : " + value);
        }

        return value;
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        WebElement textField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_ENCRYPTED_TEXT_FIELD_INPUT));
        textField.clear();
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_ENCRYPTED_TEXT_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
