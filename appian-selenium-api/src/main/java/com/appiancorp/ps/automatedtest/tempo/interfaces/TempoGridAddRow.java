package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoGridAddRow extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoGridAddRow.class);

    private static final String XPATH_RELATIVE_GRID_ADD_ROW_LINK =
            Settings.getByConstant("xpathRelativeGridAddRowLink");

    public static TempoGridAddRow getInstance(Settings settings) {
        return new TempoGridAddRow(settings);
    }

    private TempoGridAddRow(Settings settings) {
        super(settings);
    }

    public void click(String... params) {
        String gridName = getParam(0, params);

        try {
            WebElement grid = TempoGrid.getInstance(settings).getWebElement(gridName);
            WebElement link = grid.findElement(By.xpath(getXpath(params)));
            clickElement(link);
        } catch (Exception e) {
            LOG.error("Click Add Row", e);
            throw ExceptionBuilder.build(e, settings, "Click Add Row", gridName);
        }
    }

    @Override
    public void waitFor(String... params) {

    }

    @Override
    public String getXpath(String... params) {
        return XPATH_RELATIVE_GRID_ADD_ROW_LINK;
    }

}
