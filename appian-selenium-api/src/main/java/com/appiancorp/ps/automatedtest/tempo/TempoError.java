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

public final class TempoError extends AppianObject implements WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoError.class);
    private static final String XPATH_ERROR = Settings.getByConstant("xpathAbsoluteError");

    public static TempoError getInstance(Settings settings) {
        return new TempoError(settings);
    }

    private TempoError(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        return XPATH_ERROR;
    }

    @Override
    public void waitFor(String... params) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR TEMPO ERROR");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Tempo Error");
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR TEMPO ERROR");
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
            throw ExceptionBuilder.build(e, settings, "Tempo Error");
        }
    }
}
