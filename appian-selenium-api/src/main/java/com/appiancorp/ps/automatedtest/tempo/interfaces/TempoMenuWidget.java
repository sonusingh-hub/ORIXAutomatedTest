package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoMenuWidget extends AppianObject implements Clickable {
    private static final Logger LOG = LogManager.getLogger(TempoMenuWidget.class);
    private static final String XPATH_ABSOLUTE_MENU_WIDGET_ITEM_TEXT =
            Settings.getByConstant("xpathAbsoluteMenuWidgetItemText");
    private static final String XPATH_ABSOLUTE_MENU_WIDGET_ITEM_TEXT_INDEX =
            "(" + XPATH_ABSOLUTE_MENU_WIDGET_ITEM_TEXT + ")[%2$d]";
    private static final String XPATH_ABSOLUTE_MENU_WIDGET_ITEM = Settings.getByConstant("xpathAbsoluteMenuWidgetItem");
    private static final String XPATH_ABSOLUTE_MENU_WIDGET_ITEM_INDEX =
            "(" + XPATH_ABSOLUTE_MENU_WIDGET_ITEM + ")[%1$d]";

    public static TempoMenuWidget getInstance(Settings settings) {
        return new TempoMenuWidget(settings);
    }

    public TempoMenuWidget(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String menuText = getParam(0, params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Click on tempo menu widget [" + menuText + "]");
        }

        try {
            WebElement menuWidget = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(menuWidget);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click on tempo menu widget", menuText);
        }
    }

    @Override
    public void waitFor(String... params) {
        String menuText = getParam(0, params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR tempo menu widget [" + menuText + "]");
        }
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "WAIT FOR tempo menu widget", menuText);
        }
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);
        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String menuText = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(menuText)) {
                return xpathFormat(XPATH_ABSOLUTE_MENU_WIDGET_ITEM_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_MENU_WIDGET_ITEM_TEXT_INDEX, menuText, index);
            }
        } else {
            return xpathFormat(XPATH_ABSOLUTE_MENU_WIDGET_ITEM_TEXT, fieldName);
        }
    }
}
