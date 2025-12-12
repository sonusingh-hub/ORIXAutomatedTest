package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoClickableChart extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoClickableChart.class);
    private static final String XPATH_ABSOLUTE_COLUMN_CHART_INDEX =
            Settings.getByConstant("xpathAbsoluteColumnChartIndex");
    private static final String XPATH_ABSOLUTE_BAR_CHART_INDEX = Settings.getByConstant("xpathAbsoluteBarChartIndex");
    private static final String XPATH_ABSOLUTE_PIE_CHART_INDEX = Settings.getByConstant("xpathAbsolutePieChartIndex");
    private static final String XPATH_ABSOLUTE_LINE_CHART_INDEX = Settings.getByConstant("xpathAbsoluteLineChartIndex");

    public static TempoClickableChart getInstance(Settings settings) {
        return new TempoClickableChart(settings);
    }

    public TempoClickableChart(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String chartType = getParam(0, params);
        String chartLabel = getParam(1, params);
        int itemIndexNumber = getIndexNumber(getParam(2, params));

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK " + chartType + " CHART [" + chartLabel + "] PART [" + itemIndexNumber + "]");
        }

        try {
            WebElement chartComponent = settings.getDriver().findElement(By.xpath(getXpath(params)));
            Actions builder = new Actions(settings.getDriver());
            scrollIntoView(chartComponent);
            builder.moveToElement(chartComponent).perform();
            clickElement(chartComponent);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click " + chartType.toLowerCase() + " chart",
                    chartLabel, String.valueOf(itemIndexNumber));
        }
    }

    @Override
    public void waitFor(String... params) {
        String chartType = getParam(0, params);
        String chartLabel = getParam(1, params);
        int itemIndexNumber = getIndexNumber(getParam(2, params));

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR " + chartType + " CHART [" + chartLabel + "] PART [" + itemIndexNumber + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for " + chartType.toLowerCase() + " chart",
                    chartLabel, String.valueOf(itemIndexNumber));
        }
    }

    @Override
    public String getXpath(String... params) {
        String chartType = getParam(0, params);
        String chartLabel = getParam(1, params);
        int chartNumber = getIndexNumber(getParam(1, params));
        if (isFieldIndex(chartLabel)) {
            chartLabel = getFieldFromFieldIndex(chartLabel);
        }
        int itemIndexNumber = getIndexNumber(getParam(2, params));

        switch (chartType.toUpperCase()) {
            case "BAR":
                return xpathFormat(XPATH_ABSOLUTE_BAR_CHART_INDEX, chartLabel, chartNumber, itemIndexNumber);
            case "COLUMN":
                return xpathFormat(XPATH_ABSOLUTE_COLUMN_CHART_INDEX, chartLabel, chartNumber, itemIndexNumber);
            case "PIE":
                return xpathFormat(XPATH_ABSOLUTE_PIE_CHART_INDEX, chartLabel, chartNumber, itemIndexNumber);
            case "LINE":
                return xpathFormat(XPATH_ABSOLUTE_LINE_CHART_INDEX, chartLabel, chartNumber, itemIndexNumber);
            default:
                throw ExceptionBuilder.build(
                        new Exception(
                                "Expected chart type to be one of BAR, COLUMN, PIE, or LINE. Received: " + chartType),
                        settings);
        }
    }

    private int getIndexNumber(String param) {
        int indexNum = 1;
        if (isFieldIndex(param)) {
            indexNum = getIndexFromFieldIndex(param);
        }
        return indexNum;
    }
}
