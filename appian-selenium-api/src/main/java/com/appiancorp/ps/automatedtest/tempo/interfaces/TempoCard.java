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

public class TempoCard extends TempoContainer implements WaitFor, WaitForReturn, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoCard.class);
    private static final String XPATH_ABSOLUTE_CARD = Settings.getByConstant("xpathAbsoluteCard");
    private static final String XPATH_ABSOLUTE_CARD_INDEX =
            "(" + Settings.getByConstant("xpathAbsoluteCard") + ")[%2$d]";

    public static TempoCard getInstance(Settings settings) {
        return new TempoCard(settings);
    }

    protected TempoCard(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String cardName = getParam(0, params);

        if (isFieldIndex(cardName)) {
            String linkName = getFieldFromFieldIndex(cardName);
            int index = getIndexFromFieldIndex(cardName);
            return xpathFormat(XPATH_ABSOLUTE_CARD_INDEX, linkName, index);
        } else {
            return xpathFormat(XPATH_ABSOLUTE_CARD, cardName);
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
        action.moveToElement(card).moveByOffset(-(width / 2) + 2, 0).click().perform();
        unfocus();
    }

    @Override
    public void click(String... params) {
        String cardName = getParam(0, params);

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
                throw ExceptionBuilder.build(e2, settings, "Card", cardName);
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card", cardName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String cardName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + cardName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card", cardName);
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
            throw ExceptionBuilder.build(e, settings, "Wait for Card", cardName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
