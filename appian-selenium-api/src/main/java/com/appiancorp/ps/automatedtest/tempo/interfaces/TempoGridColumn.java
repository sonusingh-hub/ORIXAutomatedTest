package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoGridColumn extends TempoGrid implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoGridColumn.class);
    private static final String XPATH_RELATIVE_GRID_COLUMN_LINK = Settings.getByConstant("xpathRelativeGridColumnLink");

    public static TempoGridColumn getInstance(Settings settings) {
        return new TempoGridColumn(settings);
    }

    private TempoGridColumn(Settings settings) {
        super(settings);
    }

    public void click(String... params) {
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("SORT GRID [" + gridName + "] BY [" + columnName + "]");
        }

        try {
            WebElement grid = settings.getDriver().findElement(By.xpath(getXpath(params)));
            WebElement column = grid.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_GRID_COLUMN_LINK, columnName)));
            clickElement(column);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Sort Grid", columnName);
        }
    }
}
