package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoGridHeaderIndex extends AppianObject implements WaitFor, Container {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(TempoGridHeaderIndex.class);
    private static final String XPATH_GRID_HEADER_SEARCH = "//table//thead";
    private static final String GRID_HEADER_SEPARATOR = ";";
    private static final String GRID_TABLE_HEADER_CELL = "//th";

    public static TempoGridHeaderIndex getInstance(Settings settings) {
        return new TempoGridHeaderIndex(settings);
    }

    private TempoGridHeaderIndex(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String headers = getParam(0, params);
        String[] headersArray = headers.split(GRID_HEADER_SEPARATOR);
        int numberOfHeadersToMatch = headersArray.length;
        StringBuilder xpathBuilder = new StringBuilder();
        xpathBuilder.append(XPATH_GRID_HEADER_SEARCH);
        for (int i = 1; i <= numberOfHeadersToMatch; i++) {
            String currentTargetHeader = headersArray[i - 1];
            xpathBuilder.append(String.format("%s[%d]", GRID_TABLE_HEADER_CELL, i));
            if (!"*".equalsIgnoreCase(currentTargetHeader)) {
                xpathBuilder.append("//text()[.='");
                xpathBuilder.append(currentTargetHeader);
                xpathBuilder.append("']");
            }
            // Add tag for Header
            xpathBuilder.append("//ancestor::thead");
        }
        // Add tag for table
        xpathBuilder.append("//ancestor::table");
        return xpathBuilder.toString();
    }

    public void waitFor(String... params) {
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    }

    public String findGridIndex(String headers) {

        // Check that we get a single table match
        String xpath = getXpath(headers);
        int numberOfMatchingTables = settings.getDriver().findElements(By.xpath(xpath)).size();

        // Check if no grids or multiple grids are matched and throw exception eventually
        if (numberOfMatchingTables != 1) {
            throw buildException(numberOfMatchingTables, headers);
        }

        // Check number of preceding tables
        xpath = xpath + "//preceding::table";
        int numberOfAllPrecedingTables = settings.getDriver().findElements(By.xpath(xpath)).size();
        int tableNumber = numberOfAllPrecedingTables + 1;
        return "[" + tableNumber + "]";

    }

    private RuntimeException buildException(int numberOfMatchingTables, String headers) {
        StringBuilder exceptionMessageBuilder = new StringBuilder();
        exceptionMessageBuilder.append(String.format("The table headers '%s' does not match any grid ", headers));
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("Find Grid Index - The table headers '%s' ", headers));
        if (numberOfMatchingTables == 0) {
            exceptionMessageBuilder.append("' does not match any grid");
            messageBuilder.append("does not match any grid");
        } else {
            exceptionMessageBuilder.append("matched multiple grids");
            messageBuilder.append("matched multiple grids");
        }
        return ExceptionBuilder.build(new Exception(exceptionMessageBuilder.toString()), settings,
                messageBuilder.toString());
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }
}
