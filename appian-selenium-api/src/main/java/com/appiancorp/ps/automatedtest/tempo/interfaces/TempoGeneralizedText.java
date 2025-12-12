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
import java.util.List;

public class TempoGeneralizedText extends TempoContainer implements WaitFor, WaitForReturn, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoGeneralizedText.class);
    private static final String XPATH_GENERAL_TEXT = Settings.getByConstant("xpathGeneralText");
    private static final String XPATH_GENERAL_TEXT_INDEX = "(" + Settings.getByConstant("xpathGeneralText") + ")[%2$d]";

    public static TempoGeneralizedText getInstance(Settings settings) {
        return new TempoGeneralizedText(settings);
    }

    protected TempoGeneralizedText(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String text = getParam(0, params);

        if (isFieldIndex(text)) {
            String linkName = getFieldFromFieldIndex(text);
            int index = getIndexFromFieldIndex(text);
            return xpathFormat(XPATH_GENERAL_TEXT_INDEX, linkName, index);
        } else {
            return xpathFormat(XPATH_GENERAL_TEXT, text);
        }
    }

    @Override
    public WebElement getWebElement(String... params) {
        WebElement labelElement = settings.getDriver().findElement(By.xpath(getXpath(params)));

        // For Nested Card Layouts, get the card layout closest to the element
        List<WebElement> cardLayouts = labelElement.findElements(By.xpath("./ancestor::div"));
        int numCardLayouts = cardLayouts.size();
        return cardLayouts.get(numCardLayouts - 1);
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
        String text = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + text + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Card", text);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String text = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR CARD [" + text + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Card", text);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }
}
