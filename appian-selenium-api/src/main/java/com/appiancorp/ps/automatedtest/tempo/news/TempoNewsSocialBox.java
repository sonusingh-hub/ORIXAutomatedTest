package com.appiancorp.ps.automatedtest.tempo.news;

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

public class TempoNewsSocialBox extends AppianObject implements Refreshable, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsSocialBox.class);

    public static TempoNewsSocialBox getInstance(Settings settings) {
        return new TempoNewsSocialBox(settings);
    }

    public TempoNewsSocialBox(Settings settings) {
        super(settings);
    }

    @Override
    public void waitFor(String... params) {
        String xpath = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR OBJECT AT [" + xpath + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Xpath", xpath);
        }
    }

    @Override
    public String getXpath(String... params) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void refreshAndWaitFor(String... params) {
        for (int i = 0; i < settings.getRefreshTimes(); i++) {
            // If it is not the last refresh attempt don't throw error
            if (i < settings.getRefreshTimes() - 1) {
                if (waitForReturn(true, params)) {
                    break;
                }
                settings.getDriver().navigate().refresh();
            } else {
                waitFor(params);
            }
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String xpath = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR REFRESH [" + xpath + "]");
        }

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Item", xpath);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void click(String... params) {

        String xpath = getParam(0, params);
        TempoNewsSocialBox.getInstance(settings).waitFor(xpath);

        try {
            WebElement post = settings.getDriver().findElement(By.xpath(xpath));
            clickElement(post, false);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Social Box Item At", xpath);
        }
    }
}
