package com.appiancorp.ps.automatedtest.tempo.task;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoTaskSort extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoTaskSort.class);
    private static final String XPATH_SORT = Settings.getByConstant("xpathTaskSorting");
    private static final String XPATH_SORT_NEWEST = Settings.getByConstant("xpathTaskNewest");
    private static final String XPATH_SORT_OLDEST = Settings.getByConstant("xpathTaskOldest");

    public static TempoTaskSort getInstance(Settings settings) {
        return new TempoTaskSort(settings);
    }

    private TempoTaskSort(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void waitFor(String... params) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void click(String... params) {
        String xPathToClick = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK XPATH [" + xPathToClick + "]");
        }

        try {
            WebElement xPathElement = settings.getDriver().findElement(By.xpath(xPathToClick));
            clickElement(xPathElement);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "CLICK XPATH", xPathToClick);
        }
    }

    public void sortByNewest() {
        click(XPATH_SORT);
        click(XPATH_SORT_NEWEST);
    }

    public void sortByOldest() {
        click(XPATH_SORT);
        click(XPATH_SORT_OLDEST);
    }

    public String getCurrentSortLabel() {
        try {
            WebElement xPathElement = settings.getDriver().findElement(By.xpath(XPATH_SORT));
            return xPathElement.getAttribute("text");
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "CLICK XPATH", XPATH_SORT);
        }
    }
}
