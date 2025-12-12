package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TempoDropdownField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoDropdownField.class);
    private static final String XPATH_RELATIVE_DROPDOWN_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeDropdownFieldInput");
    private static final String XPATH_RELATIVE_DROPDOWN_FIELD_INPUT_HIDDEN =
            Settings.getByConstant("xpathRelativeDropdownFieldInputHidden");
    private static final String XPATH_DROPDOWN_FIELD_MULTIPLE = Settings.getByConstant("xpathDropdownFieldMultiple");
    private static final String XPATH_DROPDOWN_OPTION_SELECTED = Settings.getByConstant("xpathDropdownOptionSelected");
    private static final String XPATH_DROPDOWN_OPTION_CHECKED = Settings.getByConstant("xpathDropdownOptionChecked");
    private static final String XPATH_HIDDEN_DROPDOWN_LIST = Settings.getByConstant("xpathAbsoluteDropdownOptionList");

    public static TempoDropdownField getInstance(Settings settings) {
        return new TempoDropdownField(settings);
    }

    TempoDropdownField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));
        selectField.sendKeys(Keys.ENTER);
        selectField.sendKeys(Keys.HOME);

        WebElement option = getOption(selectField, fieldValue, getOptionIndex(selectField, fieldValue));
        if (option == null) {
            throw new IllegalArgumentException(fieldValue);
        } else {
            clickElement(option);
            unfocus();
        }
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        List<String> values = new ArrayList<String>();
        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));
        boolean isMultipleDropdown = isMultipleDropdown(selectField);

        if (!isMultipleDropdown) {
            selectField = selectField.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT_HIDDEN));
            return selectField.getAttribute("innerText");
        } else {
            boolean reachedBottom = false;
            List<WebElement> selectedItems = getHiddenDropdownListItems(selectField);
            while (!reachedBottom) {
                for (WebElement selectedItem : selectedItems) {
                    boolean isSelected =
                            Boolean.parseBoolean(selectedItem.getAttribute(XPATH_DROPDOWN_OPTION_SELECTED));

                    if (isSelected && !values.contains(selectedItem.getText())) {
                        values.add(selectedItem.getText());
                    }
                }

                //Scroll down to see if there is more selected fields
                scrollDown();
                List<WebElement> newListItems = getHiddenDropdownListItems(selectField);
                if (selectedItems.contains(newListItems.get(newListItems.size() - 1))) {
                    //We have reached the bottom
                    reachedBottom = true;
                } else {
                    selectedItems = newListItems;
                }
            }

            // Ensure the dropdown is closed. Selecting the items from the hidden list seems to popup the dropdown.
            unfocus();
            return String.join(",", values);
        }
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(0, params);

        // For read-only
        try {
            return TempoFieldFactory.getInstance(settings).contains(fieldLayout, fieldValue);
        } catch (Exception e) {
        }

        String val = capture(fieldLayout, params);

        // For editable
        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));
        selectField.sendKeys(Keys.ENTER);
        selectField.sendKeys(Keys.HOME);

        WebElement selectedItem = getOption(selectField, fieldValue, getOptionIndex(selectField, fieldValue));
        boolean selected = val.contains(selectedItem.getText());

        // Ensure the dropdown is closed. Selecting the items from the hidden list seems to popup the dropdown.
        unfocus();

        if (LOG.isDebugEnabled()) {
            LOG.debug("SELECT FIELD COMPARISON : Field value [" + fieldValue + "] was selected [" + selected + "]");
        }

        return selected;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("isNotBlank doesn't work for Dropdown field since when it's empty it returns the place holder");
        }
        return true;
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("CLEAR [" + fieldLayout.getText() + "]");
        }

        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));

        selectField.click();

        List<WebElement> listItems = getHiddenDropdownListItems(selectField);

        if (!isMultipleDropdown(selectField)) {
            listItems.get(0).click();
        } else {
            boolean reachedBottom = false;
            while (!reachedBottom) {
                for (Iterator<WebElement> iterator = listItems.iterator(); iterator.hasNext();) {
                    WebElement next = iterator.next();
                    boolean isSelected = Boolean.parseBoolean(next.getAttribute(XPATH_DROPDOWN_OPTION_SELECTED));
                    if (isSelected) {
                        WebDriverWait webDriverWait = new WebDriverWait(settings.getDriver(),
                                Duration.ofSeconds(settings.getTimeoutSeconds()));
                        webDriverWait.until(ExpectedConditions.elementToBeClickable(next));
                        next.click();
                    }
                }

                //Scroll down to see if there is more selected fields
                scrollDown();
                List<WebElement> newListItems = getHiddenDropdownListItems(selectField);
                if (listItems.contains(newListItems.get(newListItems.size() - 1))) {
                    //We have reached the bottom
                    reachedBottom = true;
                } else {
                    listItems = newListItems;
                }
            }
        }
        unfocus();
    }

    public void clearOf(WebElement fieldLayout, String[] fieldValues) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("MULTIPLE DROPDOWN FIELD CLEAR OF : " + String.join(", ", fieldValues));
        }

        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));
        
        if (!isMultipleDropdown(selectField)) {
            throw new IllegalArgumentException("clearOf is only supported for Multiple Dropdown Fields");
        }

        WebDriverWait webDriverWait = new WebDriverWait(settings.getDriver(),
            Duration.ofSeconds(settings.getTimeoutSeconds()));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(selectField));
        selectField.click();
        List<WebElement> listItems = getHiddenDropdownListItems(selectField);

        boolean reachedBottom = false;
        while (!reachedBottom) {
            for (Iterator<WebElement> iterator = listItems.iterator(); iterator.hasNext();) {
                WebElement next = iterator.next();
                boolean isSelected = Boolean.parseBoolean(next.getAttribute(XPATH_DROPDOWN_OPTION_SELECTED));
                
                if (isSelected) {
                    String itemText = next.getText();
                    for (String fieldValue : fieldValues) {
                        if (itemText.equals(fieldValue)) {
                            webDriverWait.until(ExpectedConditions.elementToBeClickable(next));
                            next.click();
                            break;
                        }
                    }
                }
            }

            scrollDown();
            List<WebElement> newListItems = getHiddenDropdownListItems(selectField);
            if (listItems.contains(newListItems.get(newListItems.size() - 1))) {
                reachedBottom = true;
            } else {
                listItems = newListItems;
            }
        }
        unfocus();
    }

    private boolean isMultipleDropdown(WebElement selectField) {
        return selectField.getAttribute("class").contains(XPATH_DROPDOWN_FIELD_MULTIPLE);
    }

    private List<WebElement> getHiddenDropdownListItems(WebElement selectField) {
        WebElement list;
        try {
            list = settings.getDriver().findElement(By.xpath(XPATH_HIDDEN_DROPDOWN_LIST));
        } catch (Exception e) {
            selectField.click();
            list = settings.getDriver().findElement(By.xpath(XPATH_HIDDEN_DROPDOWN_LIST));
        }
        return list.findElements(By.xpath(".//li[not(contains(@class, 'DropdownWidget---placeholder'))]"));
    }

    WebElement getOption(WebElement selectField, String fieldValue, Integer currentIndex) {
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
                if (next.getText().equals(fieldValue)) {
                    return next;
                }
            }
        }

        scrollDown();
        //After scrolling in the dropdown field, we should check the offset of the new first choice in the original
        //option list to see how much has it scrolled. And subtract that from the currentIndex to get the new index.
        List<WebElement> newList = getHiddenDropdownListItems(selectField);
        int offset = listItems.indexOf(newList.get(0));
        if (offset == 0) {
            LOG.warn("Reached the end of the dropdown list, can not find value: " + fieldValue);
            return null;
        }

        Integer newCurrentIndex = currentIndex == null ? null : currentIndex - offset;
        return getOption(selectField, fieldValue, newCurrentIndex);
    }

    Integer getOptionIndex(WebElement selectField, String fieldValue) {
        if (isFieldIndex(fieldValue)) {
            return getIndexFromFieldIndex(fieldValue) - 1;
        } else {
            return null;
        }
    }

    private List<String> getOptionIDs(List<WebElement> listItems) {
        List<String> dropdownIDs = new ArrayList<>();
        for (WebElement item : listItems) {
            String id = ((RemoteWebElement) item).getId();
            dropdownIDs.add(id);
        }
        return dropdownIDs;
    }
    
    @Override
    public void scrollDown() {
        // Using the pagedown key to scroll down in dropdown menus can create issues when a searchbar is present
        WebElement dropdownList = settings.getDriver().findElement(By.xpath(XPATH_HIDDEN_DROPDOWN_LIST));
        ((org.openqa.selenium.JavascriptExecutor) settings.getDriver())
            .executeScript("arguments[0].scrollTop += 250;", dropdownList);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Ignore interruptions
        }
    }
}
