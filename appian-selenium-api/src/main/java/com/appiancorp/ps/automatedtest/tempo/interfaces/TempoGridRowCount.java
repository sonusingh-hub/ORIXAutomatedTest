package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoGridRowCount extends AppianObject implements Captureable {

    private static final Logger LOG = LogManager.getLogger(TempoGridRowCount.class);
    private static final String XPATH_RELATIVE_GRID_ROW = Settings.getByConstant("xpathRelativeGridRow");

    public static TempoGridRowCount getInstance(Settings settings) {
        return new TempoGridRowCount(settings);
    }

    private TempoGridRowCount(Settings settings) {
        super(settings);
    }

    @Override
    public Integer capture(String... params) {
        String gridName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("GRID [" + gridName + "] ROW COUNT");
        }
        try {
            WebElement grid = TempoGrid.getInstance(settings).getWebElement(gridName);
            return grid.findElements(By.xpath(XPATH_RELATIVE_GRID_ROW)).size();
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Retrieving grid row count", gridName);
        }
    }
}
