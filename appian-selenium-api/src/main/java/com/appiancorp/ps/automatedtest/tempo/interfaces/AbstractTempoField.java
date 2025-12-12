package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutCaptureable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutClearable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutPopulateable;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutVerifiable;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractTempoField extends AppianObject
        implements Container, FieldLayoutPopulateable, FieldLayoutVerifiable,
        FieldLayoutCaptureable, FieldLayoutClearable, WaitForReturn {

    public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL =
            Settings.getByConstant("xpathAbsoluteFieldLayoutLabel");
    public static final String XPATH_ABSOLUTE_FILE_UPLOAD_LABEL =
            Settings.getByConstant("xpathAbsoluteFileUploadLabel");
    public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_PLACEHOLDER =
            Settings.getByConstant("xpathAbsoluteFieldLayoutPlaceholder");
    public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_INSTRUCTIONS =
            Settings.getByConstant("xpathAbsoluteFieldLayoutInstructions");
    public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_TOOLTIP =
            Settings.getByConstant("xpathAbsoluteFieldLayoutTooltip");
    public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX =
            Settings.getByConstant("xpathAbsoluteFieldLayoutIndex");
    public static final String XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL + ")[%2$d]";
    public static final String XPATH_ABSOLUTE_FILE_UPLOAD_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_FILE_UPLOAD_LABEL + ")[%2$d]";

    protected AbstractTempoField(Settings settings) {
        super(settings);
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        // Use locator, if relevant
        if (fieldName.startsWith(Constants.FIELD_LOCATOR_PLACEHOLDER)) {
            return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_PLACEHOLDER,
                    fieldName.replace(Constants.FIELD_LOCATOR_PLACEHOLDER, ""));
        } else if (fieldName.startsWith(Constants.FIELD_LOCATOR_INSTRUCTIONS)) {
            return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_INSTRUCTIONS,
                    fieldName.replace(Constants.FIELD_LOCATOR_INSTRUCTIONS, ""));
        } else if (fieldName.startsWith(Constants.FIELD_LOCATOR_TOOLTIP)) {
            return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_TOOLTIP,
                    fieldName.replace(Constants.FIELD_LOCATOR_TOOLTIP, ""));
        } else {
            if (isFieldIndex(fieldName)) {
                int index = getIndexFromFieldIndex(fieldName);
                String name = getFieldFromFieldIndex(fieldName);
                if (StringUtils.isBlank(name)) {
                    return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, index);
                } else {
                    return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL_INDEX, name, index);
                }

            } else {
                return xpathFormat(XPATH_ABSOLUTE_FIELD_LAYOUT_LABEL, fieldName);
            }

        }
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String fieldName = getParam(0, params);

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(params))));
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
