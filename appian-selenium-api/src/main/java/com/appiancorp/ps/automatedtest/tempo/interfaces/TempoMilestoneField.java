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

public class TempoMilestoneField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoMilestoneField.class);

    private static final String XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL =
            Settings.getByConstant("xpathAbsoluteMilestoneFieldLabel");
    private static final String XPATH_ABSOLUTE_MILESTONE_FIELD_INDEX =
            Settings.getByConstant("xpathAbsoluteMilestoneFieldIndex");
    // Absolute Milestone works for both regular milestone and vertical milestone
    private static final String XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL + ")[%2$d]";
    private static final String XPATH_RELATIVE_STEP = Settings.getByConstant("xpathRelativeMilestoneStepGeneral");
    private static final String XPATH_RELATIVE_STEP_VERTICAL =
            Settings.getByConstant("xpathRelativeMilestoneStepVerticalGeneral");
    private static final String XPATH_RELATIVE_STEP_SELECTED =
            Settings.getByConstant("xpathRelativeMilestoneStepSelected");
    private static final String XPATH_RELATIVE_STEP_SELECTED_VERTICAL =
            Settings.getByConstant("xpathRelativeMilestoneStepVerticalSelected");
    private static final String XPATH_CONCAT_PARENT_FIELD_LAYOUT =
            Settings.getByConstant("xpathConcatParentFieldLayout");
    private boolean isVertical;


    public static TempoMilestoneField getInstance(Settings settings, WebElement milestoneElement) {
        return new TempoMilestoneField(settings, milestoneElement);
    }

    public static TempoMilestoneField getInstance(Settings settings) {
        return new TempoMilestoneField(settings);
    }

    protected TempoMilestoneField(Settings settings, WebElement milestoneElement) {
        super(settings);
        if (isVertical(milestoneElement)) {
            this.isVertical = true;
        }
    }

    protected TempoMilestoneField(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        int index = getIndexFromFieldIndex(fieldName);
        String name = getFieldFromFieldIndex(fieldName);

        if (isFieldIndex(fieldName)) {
            if (StringUtils.isBlank(name)) {
                String xpathFieldIndex = XPATH_ABSOLUTE_MILESTONE_FIELD_INDEX + XPATH_CONCAT_PARENT_FIELD_LAYOUT;
                return xpathFormat(xpathFieldIndex, index);
            } else {
                String xPathFieldLabelIndex = getXPathFieldLabelIndex();
                return xpathFormat(xPathFieldLabelIndex, name, index);
            }
        } else {
            String xPathFieldLabelIndex = getXPathFieldLabelIndex();
            return xpathFormat(xPathFieldLabelIndex, fieldName, 1);
        }
    }

    private String getXPathFieldLabelIndex() {
        return XPATH_ABSOLUTE_MILESTONE_FIELD_LABEL_INDEX;
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR MILESTONE [" + fieldName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Milestone Field", fieldName);
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws Exception {
        // NOOP
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        WebElement selectedStep = getMilestoneStepField(fieldLayout);
        String currentStep = selectedStep.getText().replace("Current step: ", "").replace("\nCurrent Step", "");
        if (LOG.isDebugEnabled()) {
            LOG.debug("MILESTONE FIELD VALUE [" + currentStep + "]");
        }

        return currentStep;
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws Exception {
        String fieldValue = getParam(0, params);

        // This is fragile because there is a delay between the time when the milestone step is clicked
        // and the time Current Step elements is updated, so add this wait for Current Step to be rendered
        // as expected will fix the fragility
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(webDriver -> {
            WebElement selectedStep = getMilestoneStepField(fieldLayout);
            return selectedStep.getText().replace("Current Step", "").trim().equals(fieldValue);
        });

        WebElement selectedStep = getMilestoneStepField(fieldLayout);
        String compareString = selectedStep.getText().replace("Current Step", "").trim();
        if (LOG.isDebugEnabled()) {
            LOG.debug("MILESTONE FIELD COMPARISON : Step [" + fieldValue + "] compared to current step [" +
                    compareString + "]");
        }

        return fieldValue.equals(compareString);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {

    }

    public static boolean isType(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_STEP, fieldLayout) ||
                xpathExistsInField(XPATH_RELATIVE_STEP_VERTICAL, fieldLayout);
    }

    private boolean isVertical(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_STEP_VERTICAL, fieldLayout);
    }

    private WebElement getMilestoneStepField(WebElement fieldLayout) {
        if (isVertical) {
            return fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STEP_SELECTED_VERTICAL));
        } else {
            return fieldLayout.findElement(By.xpath(XPATH_RELATIVE_STEP_SELECTED));
        }
    }
}
