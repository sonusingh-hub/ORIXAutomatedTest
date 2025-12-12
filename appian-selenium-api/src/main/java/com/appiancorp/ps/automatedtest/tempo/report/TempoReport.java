package com.appiancorp.ps.automatedtest.tempo.report;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoReport extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoReport.class);
    private static final String XPATH_ABSOLUTE_REPORT_LINK = Settings.getByConstant("xpathAbsoluteReportLink");
    private static final String XPATH_ABSOLUTE_REPORT_LINK_INDEX = "(" + XPATH_ABSOLUTE_REPORT_LINK + ")[%2$d]";

    public static TempoReport getInstance(Settings settings) {
        return new TempoReport(settings);
    }

    private TempoReport(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String report = getParam(0, params);

        if (isFieldIndex(report)) {
            int rNum = getIndexFromFieldIndex(report);
            String rName = getFieldFromFieldIndex(report);
            return xpathFormat(XPATH_ABSOLUTE_REPORT_LINK_INDEX, rName, rNum);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_REPORT_LINK, report);
        }
    }

    @Override
    public void click(String... params) {
        String reportName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK REPORT [" + reportName + "]");
        }

        try {
            WebElement report = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(report);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Report", reportName);
        }

    }

    @Override
    public void waitFor(String... params) {
        String reportName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + reportName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Report", reportName);
        }
    }

    public boolean waitForReturn(int timeout, String... params) {
        String reportName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + reportName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Report", reportName);
        }
    }

    public boolean waitForReturn(String... params) {
        return waitForReturn(settings.getTimeoutSeconds(), params);
    }
}
