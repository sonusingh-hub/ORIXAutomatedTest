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

public final class TempoDateField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoDateField.class);
    private static final String XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER =
            Settings.getByConstant("xpathRelativeDateFieldPlaceholder");
    private static final String XPATH_RELATIVE_DATE_FIELD_INPUT = Settings.getByConstant("xpathRelativeDateFieldInput");

    public static TempoDateField getInstance(Settings settings) {
        return new TempoDateField(settings);
    }

    private TempoDateField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws ParseException {
        String fieldValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        Date d = parseDate(fieldValue);

        populateTempoDateFieldWithDate(fieldLayout, d);
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT)).getAttribute("value");

        if (LOG.isDebugEnabled()) {
            LOG.debug("GET VALUE [" + value + "]");
        }

        return value;
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws ParseException {
        String fieldValue = getParam(0, params);

        String dateString = capture(fieldLayout);

        Date compareDate = parseDate(dateString);
        Date fieldDate = parseDate(fieldValue);
        if (LOG.isDebugEnabled()) {
            LOG.debug(
                    "DATE FIELD COMPARISON : Field value [" + compareDate.toString() + "] compared to Entered value [" +
                            fieldDate.toString() +
                            "]");
        }

        return DateUtils.isSameDay(compareDate, fieldDate);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    private void populateTempoDateFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(settings.getDateFormat()).format(d);

        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER));
        WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT));

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

        dateField.sendKeys(Keys.ENTER);
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
