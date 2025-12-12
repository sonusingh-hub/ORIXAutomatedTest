package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.appiancorp.ps.automatedtest.common.WebElementUtilities.xpathExistsInField;

/* Based off of TempoTextField */
public final class TempoBarcodeField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoBarcodeField.class);
    private static final String XPATH_ABSOLUTE_BARCODE_FIELD_LABEL =
            Settings.getByConstant("xpathAbsoluteBarcodeFieldLabel");
    private static final String XPATH_ABSOLUTE_BARCODE_FIELD_INDEX =
            Settings.getByConstant("xpathAbsoluteBarcodeFieldIndex");
    private static final String XPATH_ABSOLUTE_BARCODE_FIELD_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_BARCODE_FIELD_LABEL + ")[%2$d]";
    private static final String XPATH_RELATIVE_BARCODE_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeBarcodeFieldInput");
    private static final String XPATH_RELATIVE_MASKED_BARCODE_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeMaskedBarcodeFieldInput");
    private boolean isMasked;

    public static TempoBarcodeField getInstance(Settings settings, WebElement barcodeElement) {
        return new TempoBarcodeField(settings, barcodeElement);
    }

    private TempoBarcodeField(Settings settings, WebElement barcodeElement) {
        super(settings);
        if (isMasked(barcodeElement)) {
            this.isMasked = true;
        }
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String name = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(
                        XPATH_ABSOLUTE_BARCODE_FIELD_INDEX +
                                TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
            } else {
                return xpathFormat(
                        XPATH_ABSOLUTE_BARCODE_FIELD_LABEL_INDEX +
                                TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                        name, index);
            }

        } else {
            return xpathFormat(
                    XPATH_ABSOLUTE_BARCODE_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                    fieldName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR BARCODE FIELD [" + fieldName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Barcode Field", fieldName);
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        WebElement barcodeField = getBarcodeField(fieldLayout);
        barcodeField.clear();
        barcodeField.sendKeys(fieldValue);
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        WebElement barcodeField = getBarcodeField(fieldLayout);
        String value = barcodeField.getAttribute("value");
        if (LOG.isDebugEnabled()) {
            LOG.debug("BARCODE FIELD VALUE : " + value);
        }

        return value;
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(0, params);
        // For read-only
        try {
            return TempoFieldFactory.getInstance(settings).contains(fieldLayout, fieldValue);
        } catch (Exception e) { // Swallowing exception
        }

        // For editable
        String compareString = capture(fieldLayout);
        if (LOG.isDebugEnabled()) {
            LOG.debug("BARCODE FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" +
                    compareString + "]");
        }

        return compareString.contains(fieldValue);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        WebElement barcodeField = getBarcodeField(fieldLayout);
        barcodeField.clear();
    }

    public static boolean isType(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_BARCODE_FIELD_INPUT, fieldLayout) ||
                xpathExistsInField(XPATH_RELATIVE_MASKED_BARCODE_FIELD_INPUT, fieldLayout);
    }

    private boolean isMasked(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_MASKED_BARCODE_FIELD_INPUT, fieldLayout);
    }

    private WebElement getBarcodeField(WebElement fieldLayout) {
        if (isMasked) {
            return fieldLayout.findElement(By.xpath(XPATH_RELATIVE_MASKED_BARCODE_FIELD_INPUT));
        } else {
            return fieldLayout.findElement(By.xpath(XPATH_RELATIVE_BARCODE_FIELD_INPUT));
        }
    }
}
