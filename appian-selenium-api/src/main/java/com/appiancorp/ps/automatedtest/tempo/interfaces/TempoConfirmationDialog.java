package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class TempoConfirmationDialog extends AppianObject implements WaitFor {

    private static final Logger LOG = LogManager.getLogger(TempoConfirmationDialog.class);
    private static final String XPATH_RELATIVE_CONFIRMATION_DIALOG_HEADER = Settings.getByConstant(
            "xpathRelativeConfirmationDialogHeader");
    private static final String XPATH_RELATIVE_CONFIRMATION_DIALOG_MESSAGE = Settings.getByConstant(
            "xpathRelativeConfirmationDialogMessage");
    public static final String TEXT_TYPE = "header";

    public static TempoConfirmationDialog getInstance(Settings settings) {
        return new TempoConfirmationDialog(settings);
    }

    private TempoConfirmationDialog(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String type = getParam(0, params);
        String text = getParam(1, params);

        String xPath = type.equals(TEXT_TYPE) ?
                XPATH_RELATIVE_CONFIRMATION_DIALOG_HEADER :
                XPATH_RELATIVE_CONFIRMATION_DIALOG_MESSAGE;
        return xpathFormat(xPath, text);
    }

    @Override
    public void waitFor(String... params) {
        String type = getParam(0, params);
        String text = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Wait for confirmation dialog");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings,
                    "Wait for confirmation dialog with type " + type + " and text " + text);
        }
    }
}
