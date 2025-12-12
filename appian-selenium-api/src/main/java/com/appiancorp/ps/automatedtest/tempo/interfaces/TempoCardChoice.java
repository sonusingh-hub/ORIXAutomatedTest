package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoCardChoice extends TempoContainer implements WaitFor, WaitForReturn, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoCardChoice.class);
    private static final String XPATH_ABSOLUTE_CARD_CHOICE = Settings.getByConstant("xpathAbsoluteCardChoice");
    private static final String XPATH_ABSOLUTE_CARD_CHOICE_INDEX =
            "(" + Settings.getByConstant("xpathAbsoluteCardChoice") + ")[%3$d]";

    public static TempoCardChoice getInstance(Settings settings) {
        return new TempoCardChoice(settings);
    }

    protected TempoCardChoice(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String cardName = getParam(0, params);
        String fieldName = getParam(1, params);

        if (isFieldIndex(cardName)) {
            String linkName = getFieldFromFieldIndex(cardName);
            int index = getIndexFromFieldIndex(cardName);
            return xpathFormat(XPATH_ABSOLUTE_CARD_CHOICE_INDEX, linkName, fieldName, index);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_CARD_CHOICE, cardName, fieldName);
        }
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    private void performClick(String... params) throws Exception {
        WebElement card = getWebElement(params);
        scrollIntoView(card);
        int width = card.getSize().getWidth();
        Actions action = new Actions(settings.getDriver());
        action.moveToElement(card).moveByOffset((width / 2) - 2, 0).click().perform();
        unfocus();
    }

    @Override
    public void click(String... params) {
        String cardName = getParam(0, params);
        String fieldName = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK [" + cardName + "]");
        }

        try {
            performClick(params);
        } catch (ElementNotInteractableException e) {
            // ElementClickInterceptedException is subclass of ElementNotInteractableException
            // so added one but the goal is to capture these 2 exceptions
            LOG.warn("First attempt to click failed due to " + e.getClass().getSimpleName() + ". Retrying...");
            try {
                Thread.sleep(1000);  // Wait for 1 second before retrying
                performClick(params);
            } catch (Exception e2) {
                throw ExceptionBuilder.build(e2, settings, "Card Choice", cardName);
            }
        } catch (Exception e3) {
            throw ExceptionBuilder.build(e3, settings, "Card", cardName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String cardName = getParam(0, params);
        String fieldName = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD CHOICE [" + cardName + "] IN CARD CHOICE FIELD [" + fieldName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card Choice", cardName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String cardName = getParam(0, params);
        String fieldName = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD CHOICE [" + cardName + "] IN CARD CHOICE FIELD [" + fieldName + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Card Choice", cardName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
