package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoSignatureField extends AppianObject implements WaitFor, WaitForReturn, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoSignatureField.class);
    private static final String XPATH_ABSOLUTE_SIGNATURE_BY_LABEL =
            Settings.getByConstant("xpathAbsoluteSignatureField");
    private static final String XPATH_ABSOLUTE_SIGNATURE_BY_INDEX =
            Settings.getByConstant("xpathAbsoluteSignatureFieldIndex");
    private static final String XPATH_ABSOLUTE_SIGNATURE_BY_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_SIGNATURE_BY_LABEL + ")[%2$d]";
    private static final String XPATH_RELATIVE_SIGNATURE_BUTTON =
            Settings.getByConstant("xpathRelativeSignatureButton");
    private static final String XPATH_ABSOLUTE_SIGNATURE_CANVAS =
            Settings.getByConstant("xpathAbsoluteSignatureCanvas");
    private static final String XPATH_ABSOLUTE_SIGNATURE_DONE_BUTTON =
            Settings.getByConstant("xpathAbsoluteSignatureDoneButton");

    public static TempoSignatureField getInstance(Settings settings) {
        return new TempoSignatureField(settings);
    }

    private TempoSignatureField(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String name = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(XPATH_ABSOLUTE_SIGNATURE_BY_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_SIGNATURE_BY_LABEL_INDEX, name, index);
            }

        } else {
            return xpathFormat(XPATH_ABSOLUTE_SIGNATURE_BY_LABEL, fieldName);
        }
    }

    public String getSignatureButtonRelativeXpath() {
        return XPATH_RELATIVE_SIGNATURE_BUTTON;
    }

    private String getSignatureCanvasXpath() {
        return XPATH_ABSOLUTE_SIGNATURE_CANVAS;
    }

    private String getSignatureDoneButtonXpath() {
        return XPATH_ABSOLUTE_SIGNATURE_DONE_BUTTON;
    }

    @Override
    public void waitFor(String... params) {
        String index = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR SIGNATURE FIELD [" + index + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Signature Field", index);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String index = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR SIGNATURE FIELD [" + index + "]");
        }

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(params))));
            }
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Signature Field", index);
        }
        return true;
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void click(String... params) {
        String label = getParam(0, params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK SIGNATURE FIELD BUTTON [" + label + "]");
        }

        try {
            WebElement field = settings.getDriver().findElement(By.xpath(getXpath(params)));
            WebElement button = field.findElement(By.xpath(getSignatureButtonRelativeXpath()));
            clickElement(button, false);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Signature Field Button", label);
        }
    }

    /**
     * There is a single signature panel, it exists once the appropriate signature field is clicked.
     */
    public void drawSignature() {
        waitForSignatureCanvas();
        if (LOG.isDebugEnabled()) {
            LOG.debug("DRAW SIGNATURE");
        }
        WebElement canvasElement = settings.getDriver().findElement(By.xpath(getSignatureCanvasXpath()));
        Actions builder = new Actions(settings.getDriver());
        builder.clickAndHold(canvasElement)
                .moveByOffset(14, 35)
                .moveByOffset(72, 22)
                .moveByOffset(-14, -35)
                .moveByOffset(-72, -22)
                .release()
                .perform();
        clickOnSignatureDone();
    }

    private void waitForSignatureCanvas() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR SIGNATURE CANVAS");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getSignatureCanvasXpath())));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Signature Canvas");
        }
    }

    private void clickOnSignatureDone() {
        WebElement doneButton = settings.getDriver().findElement(By.xpath(getSignatureDoneButtonXpath()));
        clickElement(doneButton);
    }
}
