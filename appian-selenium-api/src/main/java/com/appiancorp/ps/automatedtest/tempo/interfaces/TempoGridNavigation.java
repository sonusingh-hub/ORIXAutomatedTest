package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoGridNavigation extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoGridNavigation.class);
    private static final String XPATH_RELATIVE_GRID_FIRST_PAGE_LINK =
            Settings.getByConstant("xpathRelativeGridFirstPageLink");
    private static final String XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK =
            Settings.getByConstant("xpathRelativeGridPreviousPageLink");
    private static final String XPATH_RELATIVE_GRID_NEXT_PAGE_LINK =
            Settings.getByConstant("xpathRelativeGridNextPageLink");
    private static final String XPATH_RELATIVE_GRID_LAST_PAGE_LINK =
            Settings.getByConstant("xpathRelativeGridLastPageLink");
    protected static final String XPATH_RELATIVE_GRID_PAGING_LABEL =
            Settings.getByConstant("xpathRelativeGridPagingLabel");

    public static TempoGridNavigation getInstance(Settings settings) {
        return new TempoGridNavigation(settings);
    }

    protected TempoGridNavigation(Settings settings) {
        super(settings);
    }

    public void click(String... params) {
        String gridName = getParam(0, params);
        String navOption = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK GRID [" + gridName + "] NAVIGATION [" + navOption + "]");
        }

        try {
            WebElement grid = TempoGrid.getInstance(settings).getWebElement(params);
            WebElement link = grid.findElement(By.xpath(getXpath(params)));
            clickElement(link);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Navigation option", navOption);
        }
    }

    @Override
    public void waitFor(String... params) {

    }

    @Override
    public String getXpath(String... params) {
        String navOption = getParam(1, params);

        navOption = navOption.toLowerCase();

        switch (navOption) {
            case "first":
                return XPATH_RELATIVE_GRID_FIRST_PAGE_LINK;
            case "next":
                return XPATH_RELATIVE_GRID_NEXT_PAGE_LINK;
            case "previous":
                return XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK;
            case "last":
                return XPATH_RELATIVE_GRID_LAST_PAGE_LINK;
            default:
                throw new IllegalArgumentException("Invalid navigation option");
        }
    }
}
