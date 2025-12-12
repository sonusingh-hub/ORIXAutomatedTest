package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.appiancorp.ps.automatedtest.common.WebElementUtilities.xpathExistsInField;

public final class TempoButton extends AbstractTempoField implements WaitFor, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoButton.class);
    private static final String XPATH_ABSOLUTE_BUTTON = Settings.getByConstant("xpathAbsoluteButton");
    private static final String XPATH_ABSOLUTE_BUTTON_INDEX = "(" + XPATH_ABSOLUTE_BUTTON + ")[%2$d]";
    private static final String XPATH_ABSOLUTE_BUTTON_TOOLTIP = Settings.getByConstant("xpathAbsoluteButtonTooltip");
    private static final String XPATH_ABSOLUTE_BUTTON_TOOLTIP_INDEX = "(" + XPATH_ABSOLUTE_BUTTON_TOOLTIP + ")[%2$d]";
    private static final String XPATH_RELATIVE_BUTTON = Settings.getByConstant("xpathRelativeButton");

    public static TempoButton getInstance(Settings settings) {
        return new TempoButton(settings);
    }

    private TempoButton(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String button = getParam(0, params);

        // Use tooltip
        if (isFieldIndex(button)) {
            int buttonIndex = getIndexFromFieldIndex(button);
            String buttonName = getFieldFromFieldIndex(button);
            if (buttonName.contains(Constants.FIELD_LOCATOR_TOOLTIP)) {
                return xpathFormat(XPATH_ABSOLUTE_BUTTON_TOOLTIP_INDEX,
                        buttonName.replace(Constants.FIELD_LOCATOR_TOOLTIP, ""), buttonIndex);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_BUTTON_INDEX, buttonName, buttonIndex);
            }
        } else {
            if (button.contains(Constants.FIELD_LOCATOR_TOOLTIP)) {
                return xpathFormat(XPATH_ABSOLUTE_BUTTON_TOOLTIP, button.replace(Constants.FIELD_LOCATOR_TOOLTIP, ""));
            } else {
                return xpathFormat(XPATH_ABSOLUTE_BUTTON, button);
            }
        }
    }

    @Override
    public void waitFor(String... params) {
        String buttonName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Button", buttonName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String buttonName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");
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
            throw ExceptionBuilder.build(e, settings, "Wait for Button", buttonName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    @Override
    public void click(String... params) {
        String buttonName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK BUTTON [" + buttonName + "]");
        }

        try {
            WebElement button = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(button, !buttonName.startsWith(Constants.FIELD_LOCATOR_TOOLTIP));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Button", buttonName);
        }
    }

    public boolean isEnabled(String... params) {
        String buttonName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CHECK IF BUTTON [" + buttonName + "] IS ENABLED");
        }

        try {
            this.waitFor(params);
            WebElement button = settings.getDriver().findElement(By.xpath(getXpath(params)));
            return button.isEnabled();
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "CHECK BUTTON ENABLED", buttonName);
        }
    }

    public static boolean isType(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_BUTTON, fieldLayout);
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        // Return the label(s) of the button(s)
        List<WebElement> buttons = fieldLayout.findElements(By.xpath(XPATH_RELATIVE_BUTTON));
        List<String> capturedLabels = new ArrayList<>();
        for (WebElement button : buttons) {
            capturedLabels.add(button.getText());
        }
        return String.join(",", capturedLabels);
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        //NOOP
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws Exception {
        //NOOP
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws Exception {
        //NOOP
        return false;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        //NOOP
        return false;
    }
}
