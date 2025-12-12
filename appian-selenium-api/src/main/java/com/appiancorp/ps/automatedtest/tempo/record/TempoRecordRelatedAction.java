package com.appiancorp.ps.automatedtest.tempo.record;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Refreshable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoRecordRelatedAction extends AppianObject implements Clickable, Refreshable {

    private static final Logger LOG = LogManager.getLogger(TempoRecordRelatedAction.class);
    private static final String XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK =
            Settings.getByConstant("xpathAbsoluteRecordRelatedActionLink");

    public static TempoRecordRelatedAction getInstance(Settings settings) {
        return new TempoRecordRelatedAction(settings);
    }

    private TempoRecordRelatedAction(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String relatedAction = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction);
    }

    @Override
    public void click(String... params) {
        click(true, params);
    }

    public void click(Boolean unfocus, String... params) {
        String relatedAction = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK ON RELATED ACTION [" + relatedAction + "]");
        }

        try {
            WebElement element = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(element, unfocus);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Record Related Action", relatedAction);
        }
    }

    @Override
    public void waitFor(String... params) {
        String relatedAction = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR RELATED ACTION [" + relatedAction + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Record Related Action", relatedAction);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String relatedAction = getParam(0, params);

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
            throw ExceptionBuilder.build(e, settings, "Record Related Action", relatedAction);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void refreshAndWaitFor(String... params) {
        int i = 0;
        while (i < settings.getRefreshTimes()) {
            // If it is not the last refresh attempt don't throw error
            if (i < settings.getRefreshTimes() - 1) {
                if (waitForReturn(true, params)) {
                    break;
                }
            } else {
                waitFor(params);
            }
            i++;
        }
    }
}
