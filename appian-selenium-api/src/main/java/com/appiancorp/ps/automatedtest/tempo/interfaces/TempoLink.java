package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
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

public class TempoLink extends AppianObject implements Clickable, Captureable, WaitFor, WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoLinkField.class);
    private static final String XPATH_ABSOLUTE_LINK_FIELD = Settings.getByConstant("xpathAbsoluteLink");
    private static final String XPATH_ABSOLUTE_LINK_FIELD_INDEX = "(" + XPATH_ABSOLUTE_LINK_FIELD + ")[%2$d]";

    public static TempoLink getInstance(Settings settings) {
        return new TempoLink(settings);
    }

    protected TempoLink(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String linkName = getParam(0, params);

        if (isFieldIndex(linkName)) {
            int lNum = getIndexFromFieldIndex(linkName);
            String lName = getFieldFromFieldIndex(linkName);
            return xpathFormat(XPATH_ABSOLUTE_LINK_FIELD_INDEX, lName, lNum);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_LINK_FIELD, linkName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String linkName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR LINK [" + linkName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Link", linkName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String linkName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR LINK [" + linkName + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Link", linkName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public String capture(String... params) {
        WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
        String linkURL = link.getAttribute("href");
        return linkURL;
    }

    @Override
    public void click(String... params) {
        String linkName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLINK LINK [" + linkName + "]");
        }

        try {
            WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(link);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Link", linkName);
        }
    }
}
