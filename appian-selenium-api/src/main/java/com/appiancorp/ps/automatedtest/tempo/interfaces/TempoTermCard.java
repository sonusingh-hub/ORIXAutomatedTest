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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoTermCard extends TempoContainer implements WaitFor, WaitForReturn, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoTermCard.class);
    private static final String XPATH_ABSOLUTE_CARD_TERM = Settings.getByConstant("xpathAbsoluteTermCard");
    private static final String XPATH_ABSOLUTE_CARD_TERM_INDEX =
            "(" + Settings.getByConstant("xpathAbsoluteTermCard") + ")[%2$d]";

    public static TempoTermCard getInstance(Settings settings) {
        return new TempoTermCard(settings);
    }

    protected TempoTermCard(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String cardName = getParam(0, params);

        if (isFieldIndex(cardName)) {
            String linkName = getFieldFromFieldIndex(cardName);
            int index = getIndexFromFieldIndex(cardName);
            return xpathFormat(XPATH_ABSOLUTE_CARD_TERM_INDEX, linkName, index);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_CARD_TERM, cardName);
        }
    }

    @Override
    public WebElement getWebElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    private void performClick(String... params) throws Exception {
        WebElement card = getWebElement(params);
        scrollIntoView(card);

        // Wait for animations
        Thread.sleep(300);

        // Try to find a clickable element inside the card first
        try {
            WebElement clickableElement = card.findElement(
                    By.xpath(".//a | .//button | .//*[@role='button']")
            );
            clickElement(clickableElement);
            LOG.debug("Clicked on nested element inside card");
        } catch (NoSuchElementException e) {
            // If no clickable element found, try click on Term card
            try {
                clickElement(card);
                LOG.debug("Clicked on Term card element");
            } catch (Exception e2) {
                // Last resort: JavaScript click
                LOG.debug("Using JavaScript click as fallback");
                ((JavascriptExecutor) settings.getDriver())
                        .executeScript("arguments[0].click();", card);
            }
        }
    }

    @Override
    public void click(String... params) {
        String cardName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK ON Term CARD [" + cardName + "]");
        }

        try {
            performClick(params);
        } catch (ElementNotInteractableException e) {
            LOG.warn("First attempt to click failed due to " + e.getClass().getSimpleName() + ". Retrying...");
            try {
                Thread.sleep(1000);
                performClick(params);
            } catch (Exception e2) {
                throw ExceptionBuilder.build(e2, settings, "Card Term", cardName);
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card Term", cardName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String cardName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD [" + cardName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card Term", cardName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String cardName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD [" + cardName + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Card Term", cardName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}