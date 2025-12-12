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

public class TempoIconLink extends AppianObject implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoIconLink.class);
    private static final String XPATH_ABSOLUTE_ICON_LINK_FIELD_ALTTEXT =
            Settings.getByConstant("xpathAbsoluteIconLinkFieldAltText");
    private static final String XPATH_ABSOLUTE_ICON_LINK_FIELD_ALTTEXT_INDEX =
            "(" + XPATH_ABSOLUTE_ICON_LINK_FIELD_ALTTEXT + ")[%2$d]";
    private static final String XPATH_ABSOLUTE_ICON_LINK_FIELD = Settings.getByConstant("xpathAbsoluteIconLinkField");
    private static final String XPATH_ABSOLUTE_ICON_LINK_FIELD_INDEX = "(" + XPATH_ABSOLUTE_ICON_LINK_FIELD + ")[%1$d]";


    public static TempoIconLink getInstance(Settings settings) {
        return new TempoIconLink(settings);
    }

    public TempoIconLink(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String altText = getAltText(params);
        int indexNum = getIndexNumber(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK ICON LINK [" + altText + " " + indexNum + "]");
        }

        try {
            WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(link);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Icon Link", altText, String.valueOf(indexNum));
        }
    }

    @Override
    public void waitFor(String... params) {
        String altText = getAltText(params);
        int indexNum = getIndexNumber(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR ICON LINK [" + altText + " " + indexNum + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Icon Link", altText, String.valueOf(indexNum));
        }
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);
        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String altText = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(altText)) {
                return xpathFormat(XPATH_ABSOLUTE_ICON_LINK_FIELD_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_ICON_LINK_FIELD_ALTTEXT_INDEX, altText, index);
            }
        } else {
            return xpathFormat(XPATH_ABSOLUTE_ICON_LINK_FIELD_ALTTEXT, fieldName);
        }
    }

    private int getIndexNumber(String... params) {
        String param = getParam(0, params);
        int indexNum = 1;
        if (isFieldIndex(param)) {
            indexNum = getIndexFromFieldIndex(param);
        }
        return indexNum;
    }

    private String getAltText(String... params) {
        String fieldName = getParam(0, params);
        return fieldName;
    }
}
