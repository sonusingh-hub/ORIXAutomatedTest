package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Container;
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

public class TempoBox extends TempoContainer implements Clickable, Container, WaitFor, WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoBox.class);
    private static final String XPATH_ABSOLUTE_BOX = Settings.getByConstant("xpathAbsoluteBox");
    private static final String XPATH_ABSOLUTE_BOX_INDEX = "(" + Settings.getByConstant("xpathAbsoluteBox") + ")[%2$d]";
    private static final String XPATH_RELATIVE_BOX_HEADER = Settings.getByConstant("xpathRelativeBoxHeader");

    public static TempoBox getInstance(Settings settings) {
        return new TempoBox(settings);
    }

    protected TempoBox(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String cardLink = getParam(0, params);

        if (isFieldIndex(cardLink)) {
            String linkName = getFieldFromFieldIndex(cardLink);
            int index = getIndexFromFieldIndex(cardLink);
            return xpathFormat(XPATH_ABSOLUTE_BOX_INDEX, linkName, index);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_BOX, cardLink);
        }
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    @Override
    public void waitFor(String... params) {
        String boxName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR BOX [" + boxName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Box", boxName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String boxName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR BOX [" + boxName + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Box", boxName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void click(String... params) {
        String boxName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("EXPAND/CONTRACT BOX [" + boxName + "]");
        }

        try {
            WebElement boxToggle = getWebElement(params).findElement(By.xpath(XPATH_RELATIVE_BOX_HEADER));
            clickElement(boxToggle);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Expand Box", boxName);
        }
    }
}
