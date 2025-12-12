package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TempoPickerField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoPickerField.class);
    protected static final String XPATH_ABSOLUTE_PICKER_LABEL = Settings.getByConstant("xpathAbsolutePickerLabel");
    protected static final String XPATH_RELATIVE_PICKER_INPUT = Settings.getByConstant("xpathRelativePickerInput");
    protected static final String XPATH_ABSOLUTE_PICKER_SUGGESTION =
            Settings.getByConstant("xpathAbsolutePickerSuggestion");
    protected static final String XPATH_ABSOLUTE_PICKER_SUGGESTION_CONTAINS =
            Settings.getByConstant("xpathAbsolutePickerSuggestionContains");
    protected static final String XPATH_RELATIVE_PICKER_SELECTION =
            Settings.getByConstant("xpathRelativePickerSelection");
    protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION =
            Settings.getByConstant("xpathRelativePickerSpecificSelection");
    protected static final String XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK = Settings
            .getByConstant("xpathRelativePickerSelectionRemoveLink");
    protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK = Settings
            .getByConstant("xpathRelativePickerSpecificSelectionRemoveLink");
    protected static final String XPATH_RELATIVE_PICKER_SUGGEST_BOX =
            Settings.getByConstant("xpathRelativePickerSuggestBox");
    protected static final String XPATH_RELATIVE_INPUT_OR_SELECTION = "(" + XPATH_RELATIVE_PICKER_INPUT + " | " +
            XPATH_RELATIVE_PICKER_SELECTION + ")";
    private static final String XPATH_ABSOLUTE_PICKER_SUGGESTION_BOX =
            Settings.getByConstant("xpathAbsolutePickerSuggestionBox");

    public static TempoPickerField getInstance(Settings settings) {
        return new TempoPickerField(settings);
    }

    protected TempoPickerField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        String fieldName = getParam(0, params);
        String fieldValue = getParam(1, params);
        String isFieldValuePartialMatch = getParam(2, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        WebElement pickerField;

        settings.getDriver().switchTo().window("");
        waitForSuggestBox(fieldLayout, fieldName);
        pickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PICKER_INPUT));
        pickerField.click();
        pickerField.sendKeys(fieldValue);

        //wait for the suggestion box to appear

        new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_PICKER_SUGGESTION_BOX)));

        waitForProgressBar();

        // Wait until the suggestions populate
        TempoPickerFieldSuggestion.getInstance(settings).waitFor(fieldValue, isFieldValuePartialMatch);
        // Select the correct suggestion (partial match or exact match)
        selectSuggestion(fieldLayout, fieldValue, isFieldValuePartialMatch);
    }

    public void selectSuggestion(WebElement fieldLayout, String fieldValue, String isFieldValuePartialMatch) {
        String suggestionXpath = ("true".equals(isFieldValuePartialMatch)) ?
                XPATH_ABSOLUTE_PICKER_SUGGESTION_CONTAINS :
                XPATH_ABSOLUTE_PICKER_SUGGESTION;

        WebElement suggestion = new WebDriverWait(settings.getDriver(),
                Duration.ofSeconds(settings.getTimeoutSeconds())).until(
                        ExpectedConditions
                                .elementToBeClickable(By.xpath(xpathFormat(suggestionXpath, fieldValue, fieldValue))));
        clickElement(suggestion);
        TempoPickerFieldSelection.getInstance(settings).waitFor(fieldLayout, fieldValue);
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        List<String> values = new ArrayList<String>();

        for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION))) {
            values.add(a.getText());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("PICKER FIELD VALUE : " + values.toString());
        }

        return String.join(",", values);
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(0, params);

        if (TempoPickerFieldSelection.getInstance(settings).waitForReturn(fieldLayout, fieldValue)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("USER PICKER FIELD COMPARISON : FIELD VALUE [" + fieldValue + "] FOUND");
            }
            return true;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("USER PICKER FIELD COMPARISON : FIELD VALUE [" + fieldValue + "] NOT FOUND");
            }

            return false;
        }
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        while (true) {
            // We can not load all of the choices and then loop through them to click,
            // because once 1st item is clicked, the DOM structure
            // is changed, and therefore it will throw a Stale Element Exception
            List<WebElement> xs = fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK));
            if (xs.size() > 0) {
                clickElement(xs.get(0));
            } else {
                break;
            }
        }
    }

    public void clearOf(WebElement fieldLayout, String... params) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PICKER FIELD CLEAR OF : " + String.join(", ", params));
        }

        for (int i = 0; i < params.length; i++) {
            WebElement x = fieldLayout.findElement(
                    By.xpath(xpathFormat(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK, params[i])));
            clickElement(x);
        }
    }

    private void waitForSuggestBox(WebElement fieldLayout, String fieldValue) {
        String xpathLocator = getXpathLocator(fieldLayout);
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" +
                        xpathLocator + ")" + xpathFormat(XPATH_RELATIVE_PICKER_SUGGEST_BOX, fieldValue))));
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT_OR_SELECTION));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
