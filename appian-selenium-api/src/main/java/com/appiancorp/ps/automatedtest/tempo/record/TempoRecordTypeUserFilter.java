package com.appiancorp.ps.automatedtest.tempo.record;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.common.Version;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

public final class TempoRecordTypeUserFilter extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoRecordTypeUserFilter.class);
    public static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_DROPDOWN = Settings
            .getByConstant("xpathAbsoluteRecordTypeUserFilterDropdown");
    private static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LABEL = Settings
            .getByConstant("xpathAbsoluteRecordTypeUserFilterLabel");
    public static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_VALUE = Settings
            .getByConstant("xpathAbsoluteRecordTypeUserFilterValue");
    public static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_CLEAR = Settings
            .getByConstant("xpathAbsoluteRecordTypeUserFilterClear");
    public static final String XPATH_ABSOLUTE_RECORD_TYPE_DATE_RANGE = Settings
            .getByConstant("xpathAbsoluteRecordTypeDateRange");
    public static final String XPATH_ABSOLUTE_RECORD_TYPE_DATE_RANGE_START_DATE = Settings
            .getByConstant("xpathAbsoluteRecordTypeDateRangeStartDate");
    public static final String XPATH_ABSOLUTE_RECORD_TYPE_DATE_RANGE_END_DATE = Settings
            .getByConstant("xpathAbsoluteRecordTypeDateRangeEndDate");

    public static TempoRecordTypeUserFilter getInstance(Settings settings) {
        return new TempoRecordTypeUserFilter(settings);
    }

    private TempoRecordTypeUserFilter(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String userFilterName = getParam(0, params);
        return xpathFormat(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LABEL, userFilterName);
    }

    public String getXpathValue(String... params) {
        String userFilterValue = getParam(0, params);
        return xpathFormat(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_VALUE, userFilterValue);
    }

    @Override
    public void click(String... params) {
        String userFilter = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK ON RECORD TYPE USER FILTER [" + userFilter + "]");
        }

        try {
            if (Settings.getVersion().compareTo(new Version(17, 1)) >= 0) {
                clickElementInDropdown(params);
            } else {
                WebElement filter = settings.getDriver().findElement(By.xpath(getXpath(params)));
                clickElement(filter);
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Record Type User Filter", userFilter);
        }
    }

    public void populate(String... params) {
        String userFilterName = getParam(0, params);
        String userFilterValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATE RECORD TYPE USER FILTER [" + userFilterName + "] WITH [" + userFilterValue + "]");
        }

        try {
            WebElement filter = settings.getDriver().findElement(By.xpath(getXpath(userFilterName)));
            clickElement(filter, false);

            waitForDropdownValue(userFilterValue);
            WebElement value = settings.getDriver().findElement(By.xpath(getXpathValue(userFilterValue)));
            clickElement(value);
        } catch (Exception e) {
            throw ExceptionBuilder.build(new Exception("Dropdown value not found."), settings,
                    "Record Type User Filter",
                    userFilterName, userFilterValue);
        }
    }

    public void populateDateRange(String... params) {
        String userFilterName = getParam(0, params);
        String userFilterStartDate = getParam(1, params);
        String userFilterEndDate = getParam(2, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATE RECORD TYPE DATE RANGE USER FILTER [" + userFilterName +
                    "] WITH [" + userFilterStartDate + " - " + userFilterEndDate + "]");
        }

        try {
            WebElement filter = settings.getDriver().findElement(By.xpath(getXpath(userFilterName)));
            clickElement(filter, false);

            // Wait for Date Range selector to appear
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(XPATH_ABSOLUTE_RECORD_TYPE_DATE_RANGE)));

            // Populate Start Date
            WebElement startDateInput =
                    settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_RECORD_TYPE_DATE_RANGE_START_DATE));
            startDateInput.click();
            startDateInput.sendKeys(userFilterStartDate);

            // Populate End Date
            WebElement endDateInput =
                    settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_RECORD_TYPE_DATE_RANGE_END_DATE));
            endDateInput.click();
            endDateInput.sendKeys(userFilterEndDate);

            clickElement(filter, true);
        } catch (Exception e) {
            throw ExceptionBuilder.build(new Exception("Unable to select dates in date range."), settings,
                    "Record Type Date Range User Filter", userFilterName, userFilterStartDate, userFilterEndDate);
        }
    }

    public void clear(String... params) {
        String userFilterName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLEAR RECORD TYPE USER FILTER [" + userFilterName + "]");
        }

        try {
            WebElement clear;
            if (Settings.getVersion().compareTo(new Version(17, 1)) < 0) {
                clear = settings.getDriver()
                        .findElement(By.xpath(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_CLEAR));
            } else {
                clear = settings.getDriver()
                        .findElement(
                                By.xpath(xpathFormat(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_CLEAR, userFilterName)));
            }
            clickElement(clear, false);
            waitForProgressBar();
            // Newer versions of chrome require two clicks of the element to trigger the clear action, whereas
            // older versions require 1 click which results in a StaleElement when trying to click it again
            try {
                clear.isEnabled();
                clickElement(clear);
            } catch (StaleElementReferenceException e) {
                unfocus();
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(new Exception("Record type clear"), settings, "Record Type User Filter",
                    userFilterName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String userFilter = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR USER FILTER [" + userFilter + "]");
        }

        try {
            if (Settings.getVersion().compareTo(new Version(17, 1)) >= 0) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
            } else {
                waitForLink(params);
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "User Filter", userFilter);
        }
    }

    public void waitForLink(String... params) {
        String userFilter = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR RECORD TYPE USER FILTER [" + userFilter + "]");
        }

        try {
            if (Settings.getVersion().compareTo(new Version(17, 1)) >= 0) {
                checkDropdownValues(params);
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "User Filter", userFilter);
        }
    }

    public void waitForDropdownValue(String... params) {
        String userFilterValue = getParam(0, params);

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpathValue(userFilterValue))));
    }

    private void clickElementInDropdown(String... params) {
        String userFilter = getParam(0, params);
        List<WebElement> dropdowns =
                settings.getDriver().findElements(By.xpath(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_DROPDOWN));

        if (dropdowns.isEmpty()) {
            throw ExceptionBuilder.build(new Exception("No filter dropdowns were found."), settings, "User Filter",
                    userFilter);
        }

        for (Iterator<WebElement> webElementIterator = dropdowns.iterator(); webElementIterator.hasNext();) {
            WebElement next = webElementIterator.next();
            next.click();
            try {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpathValue(userFilter))));
                clickElement(settings.getDriver().findElement(By.xpath(getXpathValue(userFilter))));
                return;
            } catch (Exception e) {
                unfocus();
                continue;
            }
        }

        throw ExceptionBuilder.build(new Exception("Searched for value not found in any filter dropdowns."), settings,
                "User Filter",
                userFilter);
    }

    private void checkDropdownValues(String... params) throws Exception {
        String userFilter = getParam(0, params);
        List<WebElement> dropdowns =
                settings.getDriver().findElements(By.xpath(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_DROPDOWN));

        if (dropdowns.isEmpty()) {
            throw ExceptionBuilder.build(new Exception("No filter dropdowns were found."), settings, "User Filter",
                    userFilter);
        }

        for (Iterator<WebElement> webElementIterator = dropdowns.iterator(); webElementIterator.hasNext();) {
            WebElement next = webElementIterator.next();
            next.click();
            try {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpathValue(userFilter))));
                return;
            } catch (Exception e) {
                unfocus();
                continue;
            } finally {
                next.click();
            }
        }

        throw ExceptionBuilder.build(new Exception("Searched for value not found in any filter dropdowns."), settings,
                "User Filter",
                userFilter);
    }
}
