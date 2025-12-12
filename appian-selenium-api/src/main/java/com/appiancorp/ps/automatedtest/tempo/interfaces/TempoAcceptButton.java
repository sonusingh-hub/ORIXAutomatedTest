package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoAcceptButton extends AppianObject implements WaitFor, WaitForReturn, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoButton.class);
    private static final String XPATH_ABSOLUTE_BUTTON = Settings.getByConstant("xpathAbsoluteAcceptButton");
    private static final String XPATH_ABSOLUTE_BUTTON_INDEX = "(" + XPATH_ABSOLUTE_BUTTON + ")[%2$d]";

    public static TempoAcceptButton getInstance(Settings settings) {
        return new TempoAcceptButton(settings);
    }

    private TempoAcceptButton(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String button = getParam(0, params);

        if (isFieldIndex(button)) {
            int rNum = getIndexFromFieldIndex(button);
            String rName = getFieldFromFieldIndex(button);
            return xpathFormat(XPATH_ABSOLUTE_BUTTON_INDEX, rName, rNum);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_BUTTON, button);
        }
    }

    @Override
    public void waitFor(String... params) {
        String buttonName = settings.getLabel(Constants.ACCEPT_BUTTON_LABEL);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(buttonName))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Button", buttonName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String buttonName = settings.getLabel(Constants.ACCEPT_BUTTON_LABEL);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + buttonName + "] BUTTON");
        }

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(params))));
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Button", buttonName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void click(String... params) {
        String buttonName = settings.getLabel(Constants.ACCEPT_BUTTON_LABEL);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK BUTTON [" + buttonName + "]");
        }

        try {
            WebElement button = settings.getDriver().findElement(By.xpath(getXpath(buttonName)));
            clickElement(button);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Button", buttonName);
        }
    }
}
