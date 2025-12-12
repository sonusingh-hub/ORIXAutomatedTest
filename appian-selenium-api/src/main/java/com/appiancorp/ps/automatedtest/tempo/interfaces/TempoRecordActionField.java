package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

public class TempoRecordActionField extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoRecordActionField.class);
    private static final String XPATH_ABSOLUTE_RECORD_ACTION_FIELD_INDEX_BUTTON =
            Settings.getByConstant("xpathAbsoluteRecordActionFieldIndexButton");
    private static final String XPATH_HIDDEN_RECORD_ACTION_LIST = Settings.getByConstant("xpathHiddenRecordActionList");

    public static TempoRecordActionField getInstance(Settings settings) {
        return new TempoRecordActionField(settings);
    }

    public TempoRecordActionField(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("GET RECORD ACTION FIELD [" + getParam(0, params) + "]");
        }

        WebElement recordActionComponentElement = settings.getDriver().findElement(By.xpath(getXpath(params)));
        // Clicking on this element with Selenium has inconsistent behavior across browsers. In Chromium, the element is
        // "clicked", but does not trigger the list of actions to appear. So Javascript is used instead.
        ((JavascriptExecutor) settings.getDriver()).executeScript("arguments[0].click();",
                recordActionComponentElement);

        waitForDropdown();

        String actionValue = getParam(1, params);
        WebElement option = getOption(recordActionComponentElement, actionValue, getActionIndex(actionValue));
        if (option == null) {
            throw new IllegalArgumentException(actionValue);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CLICK ACTION [" + actionValue + "]");
            }
            clickElement(option);
            unfocus();
        }
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR RECORD ACTION FIELD [" + getParam(0, params) + "]");
        }
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Record Action Field", fieldName);
        }
    }

    @Override
    public String getXpath(String... params) {
        String recordActionComponentParameter = getParam(0, params);

        if (isFieldIndex(recordActionComponentParameter)) {
            int index = getIndexFromFieldIndex(recordActionComponentParameter);
            return xpathFormat(XPATH_ABSOLUTE_RECORD_ACTION_FIELD_INDEX_BUTTON, index);
        } else {
            throw new IllegalArgumentException(recordActionComponentParameter);
        }
    }

    private void waitForDropdown() {
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_HIDDEN_RECORD_ACTION_LIST)));
    }

    /* Taken from TempoDropdownField.java */
    private WebElement getOption(WebElement selectField, String actionValue, Integer currentIndex) {
        List<WebElement> listItems = getHiddenDropdownListItems(selectField);

        if (currentIndex != null) {
            // If using an index to select
            if (currentIndex >= 0 && currentIndex < listItems.size()) {
                return listItems.get(currentIndex);
            }
        } else {
            // Using a value to select
            for (Iterator<WebElement> iterator = listItems.iterator(); iterator.hasNext();) {
                WebElement next = iterator.next();
                if (next.findElement(By.xpath(".//span[contains(@class, 'MenuItem---primary_text')]")).getText()
                        .equals(actionValue)) {
                    return next;
                }
            }
        }

        scrollDown();
        //After scrolling in the action dropdown, we should check the offset of the new first choice in the original
        //option list to see how much has it scrolled. And subtract that from the currentIndex to get the new index.
        List<WebElement> newList = getHiddenDropdownListItems(selectField);
        int offset = listItems.indexOf(newList.get(0));
        if (offset == 0) {
            LOG.warn("Reached the end of the dropdown list, can not find value: " + actionValue);
            return null;
        }

        Integer newCurrentIndex = currentIndex == null ? null : currentIndex - offset;
        return getOption(selectField, actionValue, newCurrentIndex);
    }

    private List<WebElement> getHiddenDropdownListItems(WebElement selectField) {
        WebElement list;
        try {
            list = settings.getDriver().findElement(By.xpath(XPATH_HIDDEN_RECORD_ACTION_LIST));
        } catch (Exception e) {
            selectField.click();
            list = settings.getDriver().findElement(By.xpath(XPATH_HIDDEN_RECORD_ACTION_LIST));
        }
        return list.findElements(By.xpath(".//li"));
    }

    private Integer getActionIndex(String fieldValue) {
        if (isFieldIndex(fieldValue)) {
            return getIndexFromFieldIndex(fieldValue) - 1;
        } else {
            return null;
        }
    }
}
