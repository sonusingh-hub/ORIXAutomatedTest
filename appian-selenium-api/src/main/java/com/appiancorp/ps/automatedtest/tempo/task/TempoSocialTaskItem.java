package com.appiancorp.ps.automatedtest.tempo.task;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Refreshable;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoSocialTaskItem extends AppianObject implements Refreshable, RegexCaptureable {

    private static final Logger LOG = LogManager.getLogger(TempoSocialTaskItem.class);
    protected static final String XPATH_ABSOLUTE_SOCIAL_TASK_ITEM =
            Settings.getByConstant("xpathAbsoluteSocialTaskItem");

    public static TempoSocialTaskItem getInstance(Settings settings) {
        return new TempoSocialTaskItem(settings);
    }

    protected TempoSocialTaskItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String taskText = getParam(0, params);
        return xpathFormat(XPATH_ABSOLUTE_SOCIAL_TASK_ITEM, taskText);
    }

    @Override
    public void waitFor(String... params) {
        String taskText = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + taskText + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Social Task Item", taskText);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String taskText = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR REFRESH [" + taskText + "]");
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
            throw ExceptionBuilder.build(e, settings, "Social Task Item", taskText);
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
                settings.getDriver().navigate().refresh();
            } else {
                waitFor(params);
            }
            i++;
        }
    }

    @Override
    public String regexCapture(String regex, Integer group, String... params) {
        String taskText = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("SOCIAL TASK ITEM [" + taskText + "] REGEX [" + regex + "]");
        }

        try {
            String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
            return getRegexResults(regex, group, text);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Social Task Item regex", taskText, regex);
        }
    }
}
