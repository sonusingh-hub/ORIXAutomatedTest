package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutVerifiable;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoStampField extends AppianObject implements Container, WaitForReturn, FieldLayoutVerifiable {

    private static final String XPATH_ABSOLUTE_STAMP_FIELD_LABEL =
            Settings.getByConstant("xpathAbsoluteStampFieldLabel");
    private static final String XPATH_ABSOLUTE_STAMP_FIELD_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_STAMP_FIELD_LABEL + ")[%2$d]";
    private static final String XPATH_ABSOLUTE_STAMP_FIELD_INDEX =
            Settings.getByConstant("xpathAbsoluteStampFieldIndex");
    private static final String XPATH_RELATIVE_STAMP_TEXT = Settings.getByConstant("xpathRelativeStampFieldText");

    private static final Logger LOG = LogManager.getLogger(TempoStampField.class);

    public static TempoStampField getInstance(Settings settings) {
        return new TempoStampField(settings);
    }

    protected TempoStampField(Settings settings) {
        super(settings);
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
            throw ExceptionBuilder.build(e, settings, "Wait for Stamp Field", fieldName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR STAMP FIELD [" + fieldName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Text Field", fieldName);
        }
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String name = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(XPATH_ABSOLUTE_STAMP_FIELD_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_STAMP_FIELD_LABEL_INDEX, name, index);
            }

        } else {
            return xpathFormat(XPATH_ABSOLUTE_STAMP_FIELD_LABEL, fieldName);
        }
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {

        String expectedText = getParam(0, params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("STAMP FIELD COMPARISON : Looking for text [" + expectedText + "]");
        }
        try {
            WebElement element =
                    fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_STAMP_TEXT, expectedText)));
        } catch (Exception e) {
            // this means the component couldn't be found aka that text doesn't exist
            return false;
        }
        return true;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        // not supported
        return false;
    }
}
