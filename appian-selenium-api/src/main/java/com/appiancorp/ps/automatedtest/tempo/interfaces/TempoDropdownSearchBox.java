package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Populateable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TempoDropdownSearchBox extends TempoDropdownField implements WaitFor, Populateable {

    private static final Logger LOG = LogManager.getLogger(TempoDropdownSearchBox.class);
    private static final String XPATH_ABSOLUTE_DROPDOWN_SEARCH_BOX_FIELD =
            Settings.getByConstant("xpathAbsoluteDropdownSearchBoxField");
    private static final String XPATH_RELATIVE_DROPDOWN_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeDropdownFieldInput");

    public static TempoDropdownSearchBox getInstance(Settings settings) {
        return new TempoDropdownSearchBox(settings);
    }

    public TempoDropdownSearchBox(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(String... params) {
        String dropdownFieldName = getParam(0, params);
        String searchValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("SEARCH DROPDOWN [" + dropdownFieldName + "] FOR [" + searchValue + "]");
        }

        WebElement dropdownField = TempoField.getInstance(settings).getWebElement(dropdownFieldName);
        WebElement selectField = dropdownField.findElement(By.xpath(XPATH_RELATIVE_DROPDOWN_FIELD_INPUT));
        selectField.sendKeys(Keys.ENTER);
        selectField.sendKeys(Keys.HOME);

        WebElement searchBox = getSearchBox(dropdownField);
        searchBox.clear();
        waitForWorking();

        searchBox = getSearchBox(dropdownField);
        searchBox.sendKeys(searchValue);

        waitForWorking();
        searchBox = getSearchBox(dropdownField);
        WebElement option = getOption(searchBox, searchValue, null);
        if (option == null) {
            throw new IllegalArgumentException(searchValue);
        } else {
            clickElement(option);
        }
    }

    private WebElement getSearchBox(WebElement selectField) {
        WebElement searchBox;
        WebElement searchBoxElement;
        try {
            searchBox = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_DROPDOWN_SEARCH_BOX_FIELD));
            scrollIntoView(searchBox);
            searchBoxElement = searchBox.findElement(By.xpath(".//input"));
            scrollIntoView(searchBoxElement);
        } catch (Exception e) {
            try {
                selectField.click();
                searchBox = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_DROPDOWN_SEARCH_BOX_FIELD));
                scrollIntoView(searchBox);
                searchBoxElement = searchBox.findElement(By.xpath(".//input"));
                scrollIntoView(searchBoxElement);
            } catch (Exception e2) {
                throw ExceptionBuilder.build(e2, settings, "Tempo Dropdown Search Box");
            }
        }
        return searchBoxElement;
    }

    @Override
    public void waitFor(String... params) {
        String dropdownFieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR DROPDOWN SEARCH BOX [" + dropdownFieldName + "]");
        }

        TempoField.getInstance(settings).waitFor(dropdownFieldName);
    }

    @Override
    public String getXpath(String... params) {
        return XPATH_ABSOLUTE_DROPDOWN_SEARCH_BOX_FIELD;
    }
}
