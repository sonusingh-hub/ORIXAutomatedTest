package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.google.common.base.Strings;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public final class TempoDatetimeField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoDatetimeField.class);
    private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER =
            Settings.getByConstant("xpathRelativeDatetimeFieldDatePlaceholder");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT =
            Settings.getByConstant("xpathRelativeDatetimeFieldDateInput");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER =
            Settings.getByConstant("xpathRelativeDatetimeFieldTimePlaceholder");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT =
            Settings.getByConstant("xpathRelativeDatetimeFieldTimeInput");

    public static TempoDatetimeField getInstance(Settings settings) {
        return new TempoDatetimeField(settings);
    }

    private TempoDatetimeField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws ParseException {
        String fieldValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        Date d = parseDate(fieldValue);

        populateTempoDatetimeFieldWithDate(fieldLayout, d);
        populateTempoDatetimeFieldWithTime(fieldLayout, d);
        unfocus();
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String dateString =
                fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT)).getAttribute("value");
        String timeString =
                fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT)).getAttribute("value");

        String value = dateString + " " + timeString;
        if (LOG.isDebugEnabled()) {
            LOG.debug("DATE FIELD VALUE [" + value + "]");
        }

        return value;
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws ParseException {
        String fieldValue = getParam(0, params);

        String datetimeString = capture(fieldLayout);

        Date compareDate = parseDate(datetimeString);
        Date fieldDate = parseDate(fieldValue);
        LOG.debug("DATETIME FIELD COMPARISON : Field value [" + fieldDate.toString() + "] compared to Entered value [" +
                fieldDate.toString() +
                "]");

        return DateUtils.isSameInstant(compareDate, fieldDate);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    private void populateTempoDatetimeFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(settings.getDateFormat()).format(d);

        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER));
        WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT));

        // Clear out existing values
        if (dateField.isDisplayed()) {
            dateField.click();
            dateField.sendKeys(Strings.repeat(Keys.ARROW_RIGHT.toString(), dateField.getAttribute("value").length()));
            dateField.sendKeys(Strings.repeat(Keys.BACK_SPACE.toString(), dateField.getAttribute("value").length()));
            dateField.sendKeys(dateValue);
        } else {
            datePlaceholder.click();
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOf(dateField));
            dateField.sendKeys(dateValue);
        }
    }

    private void populateTempoDatetimeFieldWithTime(WebElement fieldLayout, Date d) {
        String timeValue = new SimpleDateFormat(settings.getTimeFormat()).format(d);

        WebElement timePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER));
        WebElement timeField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT));

        // Clear out existing values
        if (timeField.isDisplayed()) {
            timeField.click();
            timeField.sendKeys(Strings.repeat(Keys.ARROW_RIGHT.toString(), timeField.getAttribute("value").length()));
            timeField.sendKeys(Strings.repeat(Keys.BACK_SPACE.toString(), timeField.getAttribute("value").length()));
            timeField.click();
            timeField.sendKeys(timeValue);
        } else {
            timePlaceholder.click();
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOf(timeField));
            timeField.sendKeys(timeValue);
        }
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT));
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
