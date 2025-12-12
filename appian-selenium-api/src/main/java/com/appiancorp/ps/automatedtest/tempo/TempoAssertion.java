package com.appiancorp.ps.automatedtest.tempo;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoAssertion extends AppianObject implements WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoAssertion.class);

    public static TempoAssertion getInstance(Settings settings) {
        return new TempoAssertion(settings);
    }

    public TempoAssertion(Settings settings) {
        super(settings);
    }

    private final static String PAGE_CONTAINS_TEXT_FORMAT = Settings.getByConstant("xpathPageContainsText");

    @Override
    public String getXpath(String... params) {
        String text = getParam(0, params);

        return xpathFormat(PAGE_CONTAINS_TEXT_FORMAT, text);
    }

    @Override
    public void waitFor(String... params) {
        String text = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR TEXT [" + text + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Tempo Assertion");
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String text = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR TEXT [" + text + "]");
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
            throw ExceptionBuilder.build(e, settings, "Tempo Assertion", text);
        }
    }
}
