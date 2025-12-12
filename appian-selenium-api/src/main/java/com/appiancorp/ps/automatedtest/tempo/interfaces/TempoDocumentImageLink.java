package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoDocumentImageLink extends AppianObject implements Clickable {
    private static final Logger LOG = LogManager.getLogger(TempoDocumentImageLink.class);
    private static final String XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_ALTTEXT =
            Settings.getByConstant("xpathAbsoluteDocumentImageLinkAltText");
    private static final String XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_ALTTEXT_INDEX =
            "(" + XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_ALTTEXT + ")[%2$d]";
    private static final String XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK =
            Settings.getByConstant("xpathAbsoluteDocumentImageLink");
    private static final String XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_INDEX =
            "(" + XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK + ")[%1$d]";

    public static TempoDocumentImageLink getInstance(Settings settings) {
        return new TempoDocumentImageLink(settings);
    }

    public TempoDocumentImageLink(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String altText = getAltText(params);
        int indexNum = getIndexNumber(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK DOCUMENT IMAGE LINK [" + altText + " " + indexNum + "]");
        }

        try {
            WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(link);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Click Document Image Link", altText, String.valueOf(indexNum));
        }
    }

    @Override
    public void waitFor(String... params) {
        String altText = getAltText(params);
        int indexNum = getIndexNumber(params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR DOCUMENT IMAGE LINK [" + altText + " " + indexNum + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Document Image Link", altText,
                    String.valueOf(indexNum));
        }
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);
        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String altText = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(altText)) {
                return xpathFormat(XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_ALTTEXT_INDEX, altText, index);
            }
        } else {
            return xpathFormat(XPATH_ABSOLUTE_DOCUMENT_IMAGE_LINK_ALTTEXT, fieldName);
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
