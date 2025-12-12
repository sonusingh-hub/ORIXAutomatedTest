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

public final class TempoVideo extends AppianObject implements WaitFor, WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoVideo.class);
    private static final String XPATH_ABSOLUTE_VIDEO = Settings.getByConstant("xpathAbsoluteVideo");
    private static final String XPATH_ABSOLUTE_VIDEO_INDEX = "(" + XPATH_ABSOLUTE_VIDEO + ")[%2$d]";
    public static final String WAIT_FOR_VIDEO = "WAIT FOR VIDEO";

    public static TempoVideo getInstance(Settings settings) {
        return new TempoVideo(settings);
    }

    private TempoVideo(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String videoSource = getParam(0, params);

        if (isFieldIndex(videoSource)) {
            int index = getIndexFromFieldIndex(videoSource);
            String rName = getFieldFromFieldIndex(videoSource);
            return xpathFormat(XPATH_ABSOLUTE_VIDEO_INDEX, rName, index);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_VIDEO, videoSource);
        }
    }

    @Override
    public void waitFor(String... params) {
        String videoSource = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug(WAIT_FOR_VIDEO + " [" + videoSource + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, WAIT_FOR_VIDEO + " called in waitFor ", videoSource);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String videoSource = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug(WAIT_FOR_VIDEO + " [" + videoSource + "]");
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
            throw ExceptionBuilder.build(e, settings, WAIT_FOR_VIDEO + " called in waitForReturn ", videoSource);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
