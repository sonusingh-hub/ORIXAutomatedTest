package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Populateable;
import com.appiancorp.ps.automatedtest.properties.PopulateableMultiple;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import com.appiancorp.ps.automatedtest.properties.Verifiable;
import com.appiancorp.ps.automatedtest.properties.VerifiableMultiple;
import com.appiancorp.ps.automatedtest.properties.VerifiableNotBlank;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TempoGridCell extends TempoGrid implements
        PopulateableMultiple, Populateable,
        VerifiableMultiple, Verifiable,
        Captureable, RegexCaptureable, WaitFor, Clickable, VerifiableNotBlank {

    private static final String XPATH_RELATIVE_GRID_CELL_LINK = Settings.getByConstant("xpathRelativeGridCellLink");

    private static final Logger LOG = LogManager.getLogger(TempoGridCell.class);
    private static final String XPATH_RELATIVE_GRID_CELL = Settings.getByConstant("xpathRelativeGridCell");
    private static final String XPATH_ABSOLUTE_GRID_COLUMN_LABELS =
            Settings.getByConstant("xpathRelativeColumnLabelCount");

    public static TempoGridCell getInstance(Settings settings) {
        return new TempoGridCell(settings);
    }

    protected TempoGridCell(Settings settings) {
        super(settings);
    }

    @Override
    public WebElement getWebElement(String... params) {
        WebElement grid = TempoGrid.getInstance(settings).getWebElement(getParam(0, params));

        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);

        return getGridWebElement(grid, gridName, columnName, rowNum);
    }

    protected WebElement getGridWebElement(WebElement grid, String... params) {
        WebElement cell = null;

        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR COLUMN [" + columnName + "] ROW [" + rowNum + "]");
        }

        try {
            int rNum = getIndexFromFieldIndex(rowNum);
            int cNum;

            if (isFieldIndex(columnName)) {
                cNum = getIndexFromFieldIndex(columnName);
            } else {
                cNum = getColumnNumber(grid, columnName);
            }
            cell = grid.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_GRID_CELL, rNum, cNum)));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Grid Wait For", columnName, rowNum);
        }
        scrollIntoView(cell);
        return cell;
    }

    @Override
    public String getXpath(String... params) {
        WebElement cell = getWebElement(params);
        return getXpathLocator(cell);
    }

    @Override
    public void populateMultiple(String[] fieldValues, String... params) {
        populateMultiple(fieldValues, false, params);
    }

    public void populateMultiple(String[] fieldValues, boolean isPickerFieldValuePartialMatch, String... params) {
    /*
      The calling method must call this overloaded version of the method if populating a picker field
      where the suggestions only partially match the input
     */
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);

        for (String fieldValue : fieldValues) {
            params = new String[] {gridName, columnName, rowNum, fieldValue};
            populate(isPickerFieldValuePartialMatch, params);
        }
    }

    @Override
    public void populate(String... params) {
        populate(false, params);
    }

    public void populate(boolean isPickerFieldValuePartialMatch, String... params) {
    /*
      The calling method must call this overloaded version of the method if populating a picker field
      where the suggestions only partially match the input
     */
        String fieldValue = getParam(3, params);

        WebElement cell = getWebElement(params);
        TempoFieldFactory.getInstance(settings).populate(cell, isPickerFieldValuePartialMatch, "", fieldValue);
    }

    @SuppressWarnings("unused")
    @Override
    public void click(String... params) {
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);

        WebElement cell = getWebElement(params);
        WebElement link = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CELL_LINK));
        clickElement(link);
    }

    @Override
    public String capture(String... params) {
        WebElement cell = getWebElement(params);

        String capturedValue = "";
        try {
            capturedValue = TempoFieldFactory.getInstance(settings).capture(cell, "");
        } catch (Exception e) {
            LOG.debug("Unable to capture value from grid cell " + cell.getText() + " \nException: " + e);
        }
        return capturedValue;
    }

    @Override
    public String regexCapture(String regex, Integer group, String... params) {
        WebElement cell = getWebElement(params);

        return TempoFieldFactory.getInstance(settings).regexCapture(cell, regex, group, "");
    }

    @Override
    public boolean containsMultiple(String[] fieldValues, String... params) {
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);

        for (String fieldValue : fieldValues) {
            params = new String[] {gridName, columnName, rowNum, fieldValue};
            if (!contains(params)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(String... params) {
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);
        String fieldValue = getParam(3, params);

        WebElement cell = getWebElement(gridName, columnName, rowNum);
        if (!TempoFieldFactory.getInstance(settings).contains(cell, "", fieldValue)) {
            return false;
        }
        return true;
    }

    public void clear(String... params) {
        WebElement cell = getWebElement(params);
        TempoFieldFactory.getInstance(settings).clear(cell, "");
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "wait for grid cell ", fieldName);
        }
    }

    private int getColumnNumber(WebElement grid, String columnName) {
        List<WebElement> headers = grid.findElements(By.xpath(xpathFormat(
                XPATH_ABSOLUTE_GRID_COLUMN_LABELS, columnName)));

        return headers.size() + 1;
    }

    @Override
    public boolean isNotBlank(String... params) {
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);
        WebElement cell = getWebElement(gridName, columnName, rowNum);
        return TempoFieldFactory.getInstance(settings).isNotBlank(cell);
    }
}
