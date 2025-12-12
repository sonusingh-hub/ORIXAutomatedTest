package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.appiancorp.ps.automatedtest.common.WebElementUtilities.xpathExistsInField;

public class TempoGaugeField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoGaugeField.class);
    private static final String XPATH_RELATIVE_GAUGE_FIELD = Settings.getByConstant("xpathRelativeGaugeField");
    private static final String XPATH_RELATIVE_GAUGE_FIELD_PERCENTAGE =
            Settings.getByConstant("xpathRelativeGaugeFieldPercentage");
    private static final String GAUGE_FIELD_PERCENTAGE_ATTRIBUTE = "stroke-dasharray";

    public static TempoGaugeField getInstance(Settings settings) {
        return new TempoGaugeField(settings);
    }

    protected TempoGaugeField(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
    /*
    This method currently isn't used at all since there's no way for the user to interact with
    the gauge field from the UI. If this changes in the future, this method should be able to
    handle both indices and names just as others do (ex: TempoFileUploadField, TempoMilestoneField)
    using absolute XPaths.
     */
        return XPATH_RELATIVE_GAUGE_FIELD;
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR GAUGE FIELD [" + fieldName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Gauge Field", fieldName);
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        // NOOP
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        return getGaugePercentage(fieldLayout);
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(0, params);

        String compareString = getGaugePercentage(fieldLayout);

        if (LOG.isDebugEnabled()) {
            LOG.debug("GAUGE FIELD COMPARISON : Step [" + fieldValue + "] compared to current step [" + compareString +
                    "]");
        }

        return fieldValue.equals(compareString);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(getGaugePercentage(fieldLayout));
    }

    private String getGaugePercentage(WebElement fieldLayout) {
        WebElement gaugeField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_GAUGE_FIELD_PERCENTAGE));
        String percentageAttribute = gaugeField.getAttribute(GAUGE_FIELD_PERCENTAGE_ATTRIBUTE);
        return percentageAttribute.split(" ")[0];
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {

    }

    public static boolean isType(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_GAUGE_FIELD, fieldLayout);
    }
}
