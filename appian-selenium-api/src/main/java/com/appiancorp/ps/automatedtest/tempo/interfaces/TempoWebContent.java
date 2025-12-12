package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoWebContent extends AppianObject implements WaitFor, WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoWebContent.class);
    private static final String XPATH_ABSOLUTE_WEB_CONTENT = Settings.getByConstant("xpathAbsoluteWebContent");
    private static final String XPATH_ABSOLUTE_WEB_CONTENT_INDEX = "(" + XPATH_ABSOLUTE_WEB_CONTENT + ")[%2$d]";

    public static TempoWebContent getInstance(Settings settings) {
        return new TempoWebContent(settings);
    }

    private TempoWebContent(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String contentSource = getParam(0, params);

        if (isFieldIndex(contentSource)) {
            String name = getFieldFromFieldIndex(contentSource);
            int index = getIndexFromFieldIndex(contentSource);
            return xpathFormat(XPATH_ABSOLUTE_WEB_CONTENT_INDEX, name, index);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_WEB_CONTENT, contentSource);
        }
    }

    @Override
    public void waitFor(String... params) {
        String contentSource = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR WEB CONTENT [" + contentSource + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for WEB CONTENT", contentSource);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String contentSource = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR WEB CONTENT [" + contentSource + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Web Content", contentSource);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
