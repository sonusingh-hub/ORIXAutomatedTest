package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoNewsFilters extends AppianObject implements WaitFor, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsFilters.class);
    private static final String XPATH_ABSOLUTE_NEWS_FILTER_LINK = Settings.getByConstant(
            "xpathAbsoluteNewsFilterLink");

    public static TempoNewsFilters getInstance(Settings settings) {
        return new TempoNewsFilters(settings);
    }

    public TempoNewsFilters(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String filterName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK [" + filterName + "]");
        }

        try {
            WebElement filter = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(filter);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Filter", filterName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String filterName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + filterName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Filter", filterName);
        }

    }

    @Override
    public String getXpath(String... params) {
        String filterName = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_NEWS_FILTER_LINK, filterName);
    }
}
