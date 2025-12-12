package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Verifiable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoCardChoiceField extends AppianObject implements Verifiable, WaitFor, WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoCardChoiceField.class);
    private static final String XPATH_ABSOLUTE_CARD_CHOICE_FIELD =
            Settings.getByConstant("xpathAbsoluteCardChoiceField");
    private static final String XPATH_CARD_CHOICE_SELECTED = Settings.getByConstant("xpathCardChoiceSelected");

    public static TempoCardChoiceField getInstance(Settings settings) {
        return new TempoCardChoiceField(settings);
    }

    public TempoCardChoiceField(Settings settings) {
        super(settings);
    }

    @Override
    public void waitFor(String... params) {
        String cardChoiceName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD CHOICE FIELD [" + cardChoiceName + "]");
        }
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params)))
            );
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Card Choice Field", cardChoiceName);
        }
    }

    @Override
    public String getXpath(String... params) {
        String cardChoiceName = getParam(0, params);

        if (isFieldIndex(cardChoiceName)) {
            int index = getIndexFromFieldIndex(cardChoiceName);
            return xpathFormat(XPATH_ABSOLUTE_CARD_CHOICE_FIELD, index);
        } else {
            throw new IllegalArgumentException(cardChoiceName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String cardChoiceName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD CHOICE FIELD [" + cardChoiceName + "]");
        }

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(params))));
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for return Card Choice Field", cardChoiceName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public boolean contains(String... params) {
        String cardName = getParam(0, params);
        String fieldName = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CONTAINS [" + cardName + "]");
        }
        try {
            WebElement card = TempoCardChoice.getInstance(settings).getWebElement(cardName, fieldName);
            return card.getAttribute("class").contains(XPATH_CARD_CHOICE_SELECTED);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card Choice Selected", cardName, fieldName);
        }
    }
}
