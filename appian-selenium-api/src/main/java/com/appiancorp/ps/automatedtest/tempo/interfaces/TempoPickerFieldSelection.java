package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutWaitForReturn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoPickerFieldSelection extends TempoPickerField implements FieldLayoutWaitForReturn {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(TempoPickerFieldSelection.class);
    protected static final String XPATH_RELATIVE_PICKER_SELECTION =
            Settings.getByConstant("xpathRelativePickerSelection");
    protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION =
            Settings.getByConstant("xpathRelativePickerSpecificSelection");

    public static TempoPickerFieldSelection getInstance(Settings settings) {
        return new TempoPickerFieldSelection(settings);
    }

    private TempoPickerFieldSelection(Settings settings) {
        super(settings);
    }

    public String getXpath(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(0, params);

        return xpathFormat(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue);
    }

    @Override
    public void waitFor(WebElement fieldLayout, String... params) {
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(
                        fieldLayout, params))));
    }

    @Override
    public boolean waitForReturn(int timeout, WebElement fieldLayout, String... params) {
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(fieldLayout,
                            params))));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Override
    public boolean waitForReturn(WebElement fieldLayout, String... params) {
        return waitForReturn(settings.getNotPresentTimeoutSeconds(), fieldLayout, params);
    }
}
