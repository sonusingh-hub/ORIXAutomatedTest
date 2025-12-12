package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoGridTotalCount extends TempoGridNavigation implements Captureable {

    private static final Logger LOG = LogManager.getLogger(TempoGridTotalCount.class);
    private static final String XPATH_RELATIVE_GRID_ROW = Settings.getByConstant("xpathRelativeGridRow");

    public static TempoGridTotalCount getInstance(Settings settings) {
        return new TempoGridTotalCount(settings);
    }

    private TempoGridTotalCount(Settings settings) {
        super(settings);
    }

    @Override
    public Integer capture(String... params) {
        String gridName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("GRID [" + gridName + "] TOTAL COUNT");
        }

        WebElement grid = null;
        try {
            grid = TempoGrid.getInstance(settings).getWebElement(gridName);
            WebElement pagingLabel = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PAGING_LABEL));
            String pagingLabelText = pagingLabel.getText();
            String totalCountStr = pagingLabelText.split("of", 2)[1];
            int totalCount = Integer.parseInt(totalCountStr.trim().replaceAll(",", ""));
            return totalCount;
        } catch (Exception e) {
            try {
                if (grid != null) {
                    return grid.findElements(By.xpath(XPATH_RELATIVE_GRID_ROW)).size();
                }
            } catch (Exception e1) {
                throw ExceptionBuilder.build(e1, settings, "Retrieving grid total count", gridName);
            }
            throw ExceptionBuilder.build(e, settings, "Retrieving grid total count", gridName);
        }
    }

}
