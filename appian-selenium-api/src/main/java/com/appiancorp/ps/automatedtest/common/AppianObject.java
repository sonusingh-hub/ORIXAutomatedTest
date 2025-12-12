package com.appiancorp.ps.automatedtest.common;

import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.exception.WaitForProgressBarTestException;
import com.appiancorp.ps.automatedtest.exception.WaitForWorkingTestException;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppianObject {

    private static final Logger LOG = LogManager.getLogger(AppianObject.class);

    protected Settings settings;
    public static final String[] DATE_ENTRY_FORMATS = {
            "yyyy-MM-dd",
            "MM/dd/yyyy"
    };
    public static final String TIME_ENTRY_FORMAT = "HH:mm";

    private static final String XPATH_WORKING = Settings.getByConstant("xpathAbsoluteWorking");
    private static final String XPATH_PROGRESS_BAR = Settings.getByConstant("xpathAbsoluteProgressBar");
    private static final String XPATH_WORKING_INDICATOR_HIDDEN =
            Settings.getByConstant("xpathAbsoluteWorkingIndicator");

    private static final Pattern INDEX_PATTERN = Pattern.compile("(.*)?\\[([0-9]+)\\]");
    private static final String DATETIME_REGEX = "(([0-9]{4}-[0-9]{2}-[0-9]{2}){0,1}(\\s)*([0-9]{2}:[0-9]{2}){0,1})";
    private static final String DATETIME_CALC_REGEX =
            DATETIME_REGEX + "((\\s)*[+-](\\s)*[0-9]+(\\s)*(minute|hour|day|month|year)(s){0,1})*";
    private static final String TEST_VARIABLE_PREFIX = "tv!";
    private static final String TEST_VARIABLE_REGEX = "^" + TEST_VARIABLE_PREFIX + ".*";

    private static final int DATETIME_STRING_LENGTH = 16;
    private static final int DATE_STRING_LENGTH = 10;
    private static final int TIME_STRING_LENGTH = 5;

    public static AppianObject getInstance(Settings settings) {
        return new AppianObject(settings);
    }

    public AppianObject(Settings settings) {
        this.settings = settings;
    }

    public static boolean isDatetime(String dateTimeString) {
        return dateTimeString.matches(DATETIME_REGEX);
    }

    public static boolean isTestVariable(String variable) {
        return variable.matches(TEST_VARIABLE_REGEX);
    }

    public String formatDatetime(String dateTimeString) {
        dateTimeString = dateTimeString.trim();
        Date d;
        try {
            d = parseDate(dateTimeString);
        } catch (ParseException e) {
            d = settings.getStartDatetime();
        }

        return new SimpleDateFormat(settings.getDatetimeDisplayFormat()).format(d);
    }

    public static boolean isDatetimeCalculation(String dateTimeString) {
        dateTimeString = dateTimeString.trim();
        boolean isADatetimeCalculation;
        if (StringUtils.isBlank(dateTimeString)) {
            isADatetimeCalculation = false;
        } else {
            isADatetimeCalculation = dateTimeString.matches(DATETIME_CALC_REGEX);
        }
        return isADatetimeCalculation;
    }

    private final class DateTimeAdjustment {
        private String originalDateTimeString;
        private Date movingDateTimeTarget;
        private String reducingDateTimeString;
    }

    public String formatDatetimeCalculation(String dateTimeString) {
        return formatDatetimeCalculation(dateTimeString, settings.getStartDatetime());
    }

    public String formatDatetimeCalculation(String dateTimeString, Date currentDate) {
        if (StringUtils.isBlank(dateTimeString)) {
            dateTimeString = "";
            return dateTimeString;
        } else {
            DateTimeAdjustment dta = new DateTimeAdjustment();
            dta.originalDateTimeString = dateTimeString;
            dta.reducingDateTimeString = dateTimeString.trim();

            try {


                if ((dateTimeString.length() >= DATETIME_STRING_LENGTH) &&
                        isDatetime(dateTimeString.substring(0, DATETIME_STRING_LENGTH))) {
                    // Check if it has a size of a potential Date + time String && it's an instance of DateTime
                    dta.movingDateTimeTarget = parseDate(dateTimeString.substring(0, DATETIME_STRING_LENGTH));
                    dta.reducingDateTimeString = dateTimeString.substring(DATETIME_STRING_LENGTH);
                } else if ((dateTimeString.length() >= DATE_STRING_LENGTH) &&
                        isDatetime(dateTimeString.substring(0, DATE_STRING_LENGTH))) {
                    // Check if it has a size of a potential Date String && it's an instance of DateTime
                    dta.movingDateTimeTarget = parseDate(dateTimeString.substring(0, DATE_STRING_LENGTH));
                    dta.reducingDateTimeString = dateTimeString.substring(DATE_STRING_LENGTH);
                } else if ((dateTimeString.length() >= TIME_STRING_LENGTH) &&
                        isDatetime(dateTimeString.substring(0, TIME_STRING_LENGTH))) {
                    // Check if it has a size of a potential Time String && it's an instance of DateTime
                    String today = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
                    dateTimeString = dateTimeString.substring(0, TIME_STRING_LENGTH);
                    String targetTimeToday = today + " " + dateTimeString;
                    dta.movingDateTimeTarget = parseDate(targetTimeToday);
                    if (dateTimeString.length() > TIME_STRING_LENGTH) {
                        dta.reducingDateTimeString = dateTimeString.substring(6);
                    } else {
                        dta.reducingDateTimeString = "";
                    }
                } else {
                    // In this case it's only an offset, so currentDate will be used as starting DateTime
                    dta.movingDateTimeTarget = currentDate;
                }

            } catch (Exception e) {
                throw ExceptionBuilder.build(e, settings, "Format Datetime Calculation");

            }

            dta.reducingDateTimeString = dta.reducingDateTimeString.trim();
            boolean needsFormatting = isDatetimeCalculation(dta.reducingDateTimeString);
            while (needsFormatting) {
                dta = formatDatetimeCalculationAdjustDate(dta);
                needsFormatting = (isDatetimeCalculation(dta.reducingDateTimeString));
            }
            return new SimpleDateFormat(settings.getDatetimeDisplayFormat()).format(dta.movingDateTimeTarget);

        }

    }

    private DateTimeAdjustment formatDatetimeCalculationAdjustDate(DateTimeAdjustment dta) {

        String firstCharacter = dta.reducingDateTimeString.substring(0, 1);
        int sign;
        if (firstCharacter.equals("+")) {
            sign = 1;
        } else if (firstCharacter.equals("-")) {
            sign = -1;
        } else {
            throw ExceptionBuilder.build(
                    new Exception(
                            "Expected first character of '" + firstCharacter + "' from '" + dta.originalDateTimeString +
                                    "' to be a '+' or '-'"),
                    settings, "Format Datetime Calculation Date Adjustment");
        }
        dta.reducingDateTimeString = dta.reducingDateTimeString.substring(1).trim();
        int spacePosition = dta.reducingDateTimeString.indexOf(" ");
        int iOffset;
        if (spacePosition > 0) {
            String stringCapturedBeforeSpace = dta.reducingDateTimeString.substring(0, spacePosition);
            iOffset = Integer.parseInt(stringCapturedBeforeSpace);
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring(spacePosition + 1);
        } else {
            throw ExceptionBuilder.build(
                    new Exception(
                            "Expecting to find a ' ' character in the string '" + dta.reducingDateTimeString + "'"),
                    settings, "Format Datetime Calculation Date Adjustment");

        }

        if (dta.reducingDateTimeString.startsWith("minute")) {
            dta.movingDateTimeTarget = DateUtils.addMinutes(dta.movingDateTimeTarget, iOffset * sign);
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring("minute".length());
        } else if (dta.reducingDateTimeString.startsWith("hour")) {
            dta.movingDateTimeTarget = DateUtils.addHours(dta.movingDateTimeTarget, iOffset * sign);
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring("hour".length());
        } else if (dta.reducingDateTimeString.startsWith("day")) {
            dta.movingDateTimeTarget = DateUtils.addDays(dta.movingDateTimeTarget, iOffset * sign);
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring("day".length());
        } else if (dta.reducingDateTimeString.startsWith("month")) {
            dta.movingDateTimeTarget = DateUtils.addMonths(dta.movingDateTimeTarget, iOffset * sign);
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring("month".length());
        } else if (dta.reducingDateTimeString.startsWith("year")) {
            dta.movingDateTimeTarget = DateUtils.addYears(dta.movingDateTimeTarget, iOffset * sign);
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring("year".length());
        }
        // "s" corresponds to the unit of time being plural
        if (dta.reducingDateTimeString.startsWith("s")) {
            dta.reducingDateTimeString = dta.reducingDateTimeString.substring("s".length());
        }
        dta.reducingDateTimeString = dta.reducingDateTimeString.trim();
        return dta;

    }

    public Date parseDate(String datetimeString) throws ParseException {
        List<String> patterns = new ArrayList<>(List.of(
                settings.getDateFormat(),
                settings.getDateDisplayFormat(),
                settings.getDatetimeFormat(),
                settings.getDatetimeDisplayFormat()
        ));
        for (String dateFormat : DATE_ENTRY_FORMATS) {
            patterns.add(dateFormat);
            patterns.add(dateFormat + " " + TIME_ENTRY_FORMAT);
        }
        return DateUtils.parseDateStrictly(datetimeString.strip(), patterns.toArray(new String[0]));
    }

    public String substituteSpecialCharacters(String variable) {
        variable = variable.replaceAll("(\\r(\\n)?|\\n(\\r)?)", "\n");
        return variable;
    }

    public String parseVariable(String variable) {

        if (StringUtils.isBlank(variable)) {
            return variable;
        } else {
            variable = substituteSpecialCharacters(variable);

            if (isDatetimeCalculation(variable)) {
                return formatDatetimeCalculation(variable);
            } else if (isDatetime(variable)) {
                return formatDatetime(variable);
            } else if (isTestVariable(variable)) {
                return settings.getTestVariable(variable.replace(TEST_VARIABLE_PREFIX, ""));
            } else {
                return variable;
            }
        }
    }

    public Boolean compareStrings(String testString, String compareString) {
        LOG
                .debug("STRING COMPARISON: Field value [" + compareString + "] compared to Test value [" + testString +
                        "]");
        try {
            Date testDate = parseDate(testString);
            Date fieldDate = parseDate(compareString);

            LOG.debug("DATE COMPARISON: Field value [" + fieldDate.toString() + "] compared to Test value [" +
                    testDate.toString() + "]");

            if (AppianObject.isDatetime(compareString)) {
                return DateUtils.isSameInstant(testDate, fieldDate);
            } else {
                return DateUtils.isSameDay(testDate, fieldDate);
            }
        } catch (ParseException e) {
            return !Strings.isNullOrEmpty(testString) && compareString.contains(testString);
        }
    }

    public String getParam(int index, String... params) {
        return parseVariable(params[index]);
    }

    public static String escapeForXpath(String variable) {
        variable = variable.toLowerCase();
        if (variable.contains("'") || variable.contains("\"")) {
            return "concat('" + variable.replace("'", "', \"'\", '") + "', '')";
        } else {
            return "'" + variable + "'";
        }
    }

    public static String xpathFormat(String template, Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof String) {
                params[i] = escapeForXpath((String) params[i]);
            }
        }

        return String.format(template, params);
    }

    public String runExpression(String expression) {
        try {
            String servletUrl =
                    settings.getUrl() + "/plugins/servlet/appianautomatedtest?operation=runExpression&expression=" +
                            URLEncoder.encode(expression, "UTF-8");

            String returnVal = "";

            // Open new tab
            ((JavascriptExecutor) settings.getDriver()).executeScript("window.open('" + servletUrl + "','_blank');");

            // Switch to tab
            Set<String> handles = settings.getDriver().getWindowHandles();
            String popupHandle = "";
            for (String handle : handles) {
                if (!handle.equals(settings.getMasterWindowHandle())) {
                    popupHandle = handle;
                }
            }
            settings.getDriver().switchTo().window(popupHandle);

            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//pre")));
            returnVal = settings.getDriver().findElement(By.xpath("//pre")).getText();

            LOG.debug("'" + expression + "' equals '" + returnVal + "'");

            // Close tab
            settings.getDriver().close();

            Thread.sleep(500);
            settings.getDriver().switchTo().window(settings.getMasterWindowHandle());
            settings.getDriver().switchTo().defaultContent();

            return returnVal;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public void waitForWorking(int timeout) {
        try {
            Thread.sleep(550);
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                    ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPATH_WORKING)));
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Working");
        } catch (TimeoutException e) {
            throw new WaitForWorkingTestException(e);
        }
    }

    public void waitForWorking() {
        waitForWorking(settings.getTimeoutSeconds());
    }

    public void waitForProgressBar(int timeout) {
        try {
            Thread.sleep(200);
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(ExpectedConditions.not(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(XPATH_WORKING_INDICATOR_HIDDEN))));
            Thread.sleep(200);

        } catch (InterruptedException e) {
            throw ExceptionBuilder.build(e, settings, "Wait for progress bar");
        } catch (TimeoutException e) {
            throw new WaitForProgressBarTestException(e);
        }
    }

    public void waitForProgressBar() {
        waitForProgressBar(settings.getTimeoutSeconds());
    }

    public void clickElement(WebElement element) {
        clickElement(element, true);
    }

    private void performClick(WebElement element, Boolean unfocus) {
        scrollIntoView(element);
        element.click();
        if (unfocus) {
            unfocus();
        }
    }

    public void clickElement(WebElement element, Boolean unfocus) {
        try {
            performClick(element, unfocus);
        } catch (ElementNotInteractableException e) {
            // ElementClickInterceptedException is subclass of ElementNotInteractableException
            // so added one but the goal is to capture these 2 exceptions
            try {
                LOG.warn("First attempt to click failed due to " + e.getClass().getSimpleName() + ". Retrying...");
                Thread.sleep(1000);  // Wait for 1 second before retrying
                performClick(element, unfocus);
            } catch (InterruptedException e1) {
                throw ExceptionBuilder.build(e1, settings, "clickElement");
            }
        }
    }

    public void scrollIntoView(WebElement webElement, Boolean alignToTop) {
        // Have to manually scroll element into view because Tempo header covers the action link for long action lists
        ((JavascriptExecutor) settings.getDriver()).executeScript(
                "arguments[0].scrollIntoView(" + alignToTop.toString() + ");", webElement);
    }

    public void scrollIntoView(WebElement webElement) {
        scrollIntoView(webElement, false);
    }

    public void unfocus() {
        unfocus(settings.getTimeoutSeconds());
    }

    public void scrollDown() {
        Actions scrollAction = new Actions(settings.getDriver());
        scrollAction.sendKeys(Keys.PAGE_DOWN).perform();
    }

    public void unfocus(Integer timeout) {
        ((JavascriptExecutor) settings.getDriver()).executeScript(
                "!!document.activeElement ? document.activeElement.blur() : 0");

        waitForProgressBar(timeout);

    }

    public static boolean isFieldIndex(String fieldNameIndex) {
        return INDEX_PATTERN.matcher(fieldNameIndex).matches();
    }

    public static String getFieldFromFieldIndex(String fieldNameIndex) {
        Matcher m = INDEX_PATTERN.matcher(fieldNameIndex);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

    public static int getIndexFromFieldIndex(String fieldNameIndex) {
        Matcher m = INDEX_PATTERN.matcher(fieldNameIndex);
        if (m.find()) {
            return Integer.parseInt(m.group(2));
        } else {
            return 1;
        }
    }

    public static String getXpathLocator(WebElement element) {
        Pattern p = Pattern.compile(".*xpath: (.*)");
        Matcher m = p.matcher(element.toString());

        if (m.find()) {
            return m.group(1).substring(0, m.group(1).length() - 1);
        } else {
            return null;
        }
    }

    public static String getRegexResults(String regex, Integer group, String text) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text.toString());

        if (m.find()) {
            LOG.debug("REGEX [" + regex + "] RESULTS [" + m.group(group) + "]");
            return m.group(group);
        } else {
            return "";
        }
    }

    public boolean atLeastVersion(Double version) {
        return (settings.getVersion().compareTo(new Version(Double.toString(version))) >= 0);
    }

    public boolean atMostVersion(Double version) {
        return (settings.getVersion().compareTo(new Version(Double.toString(version))) <= 0);
    }
}
