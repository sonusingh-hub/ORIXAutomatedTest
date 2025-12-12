package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutCaptureable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutVerifiable;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoReadOnlyField extends AbstractTempoField
        implements FieldLayoutVerifiable, FieldLayoutCaptureable {

    private static final Logger LOG = LogManager.getLogger(TempoReadOnlyField.class);
    public static final String XPATH_RELATIVE_READ_ONLY_FIELD = Settings.getByConstant("xpathRelativeReadOnlyField");

    public static TempoReadOnlyField getInstance(Settings settings) {
        return new TempoReadOnlyField(settings);
    }

    private TempoReadOnlyField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws Exception {
        throw new IllegalArgumentException("Invalid for a READONLY Field");
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        //Adding this because if the RO field is populated by another field, then we need to wait for
        //working/progressBar to disappear in order to have the RO field populated
        waitForProgressBar();

        String fieldValue = getParam(0, params);
        String compareString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD)).getText();

        if (LOG.isDebugEnabled()) {
            LOG.debug("READ ONLY FIELD COMPARISON : Field value [" + compareString + "] compared to Test value [" +
                    fieldValue + "]");
        }

        return compareStrings(params[0], compareString);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String value = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_READ_ONLY_FIELD))).getText();

        if (LOG.isDebugEnabled()) {
            LOG.debug("READ ONLY FIELD VALUE: " + value);
        }

        return value;
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        throw new IllegalArgumentException("Invalid for a READONLY Field");
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
