package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
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

public class TempoSection extends TempoContainer implements
        Container, WaitFor, WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoSection.class);

    protected static final String XPATH_ABSOLUTE_SECTION_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionLayout");

    public static TempoSection getInstance(Settings settings) {
        return new TempoSection(settings);
    }

    protected TempoSection(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String sectionName = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_SECTION_LAYOUT, sectionName);
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    @Override
    public void waitFor(String... params) {
        if (params.length == 1) {
            String sectionName = getParam(0, params);

            try {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
            } catch (Exception e) {
                throw ExceptionBuilder.build(e, settings, "Wait for Section", sectionName);
            }
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String sectionName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR SECTION [" + sectionName + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Section", sectionName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
