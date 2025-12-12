package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Countable;
import com.appiancorp.ps.automatedtest.properties.Verifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoGridRow extends TempoGridCell implements Clickable, Verifiable, Countable {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(TempoGridRow.class);
    private static final String XPATH_RELATIVE_GRID_CHECKBOX = Settings.getByConstant("xpathRelativeGridCheckbox");
    private static final String XPATH_RELATIVE_GRID_CHECKBOX_CLICK =
            Settings.getByConstant("xpathRelativeGridCheckboxClick");
    private static final String XPATH_RELATIVE_GRID_ROW = Settings.getByConstant("xpathRelativeGridRow");
    private static final String XPATH_PARENT = Settings.getByConstant("xpathParent");
    private static final String XPATH_ROW_SELECTION = Settings.getByConstant("xpathRowSelection");
    private static final String XPATH_ROW_SELECTED = Settings.getByConstant("xpathRowSelected");

    public static TempoGridRow getInstance(Settings settings) {
        return new TempoGridRow(settings);
    }

    protected TempoGridRow(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String gridName = getParam(0, params);
        String rowNum = getParam(1, params);

        try {
            WebElement cell = TempoGridCell.getInstance(settings).getWebElement(gridName, "[1]", rowNum);
            if (isRowSelectable(cell)) {
                clickElement(cell);
            } else {
                WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX_CLICK));

                clickElement(checkbox);
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Grid Row Selection", gridName, rowNum);
        }
    }

    @Override
    public boolean contains(String... params) {
        String gridName = getParam(0, params);
        String rowNum = getParam(1, params);

        try {
            WebElement cell = TempoGridCell.getInstance(settings).getWebElement(gridName, "[1]", rowNum);
            boolean isRowSelected;

            if (isRowSelectable(cell)) {
                String selectedAttributeValue =
                        cell.findElement(By.xpath(XPATH_PARENT)).getAttribute(XPATH_ROW_SELECTED);
                isRowSelected = Boolean.parseBoolean(selectedAttributeValue);
            } else {
                WebElement selectionElement = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
                isRowSelected = selectionElement.isSelected();
            }

            return isRowSelected;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Grid Row Selected", gridName, rowNum);
        }
    }

    @Override
    public Integer count(String... params) {
        String gridName = getParam(0, params);
        try {
            WebElement grid = TempoGrid.getInstance(settings).getWebElement(gridName);
            Integer numRows = grid.findElements(By.xpath(XPATH_RELATIVE_GRID_ROW)).size();

            return numRows;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Grid Row Count", gridName);
        }
    }

    private boolean isRowSelectable(WebElement cell) {
        return cell.findElement(By.xpath(XPATH_PARENT)).getAttribute("class").contains(XPATH_ROW_SELECTION);
    }
}
