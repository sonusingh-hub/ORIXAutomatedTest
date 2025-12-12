package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoFieldsFactory extends AppianObject implements
        FieldLayoutVerifiable, VerifiableMultiple,
        FieldLayoutClearable, Clearable,
        FieldLayoutPopulateable, PopulateableMultiple,
        FieldLayoutCaptureable, Captureable,
        FieldLayoutRegexCaptureable, RegexCaptureable, VerifiableNotBlank {

    private static final Logger LOG = LogManager.getLogger(TempoFieldsFactory.class);
//    public static final String XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT =
//            Settings.getByConstant("xpathConcatAncestorFieldLayout");
//    public static final String XPATH_RELATIVE_FIELD_LAYOUT_LABEL =
//            Settings.getByConstant("xpathRelativeFieldLayoutLabel");
    public static final String XPATH_ABSOLUTE_FILE_UPLOAD_LABEL =
            Settings.getByConstant("xpathAbsoluteFileUploadLabel");
    public static TempoFieldsFactory getInstance(Settings settings) {
        return new TempoFieldsFactory(settings);
    }

    protected TempoFieldsFactory(Settings settings) {
        super(settings);
    }

    public void populateMultiple(String[] fieldValues, String... params) {
        populateMultiple(fieldValues, false, params);
    }

    public void populateMultiple(String[] fieldValues, boolean isPickerFieldValuePartialMatch, String... params) {
    /*
      The calling method must call this overloaded version of the method if populating a picker field
      where the suggestions only partially match the input
     */
        if (params.length == 1) {
            String fieldName = getParam(0, params);

            WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
            for (String fieldValue : fieldValues) {
                populate(fieldLayout, isPickerFieldValuePartialMatch, fieldName, fieldValue);
            }
        } else {
            String fieldType = getParam(0, params);
            String fieldName = getParam(1, params);

            AbstractTempoField tempoField = getFieldTypeFromString(fieldType);
            WebElement fieldLayout = tempoField.getWebElement(fieldName);
            for (String fieldValue : fieldValues) {
                populate(fieldLayout, isPickerFieldValuePartialMatch, fieldName, fieldValue);
            }
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        populate(fieldLayout, false, params);
    }

    public void populate(WebElement fieldLayout, boolean isPickerFieldValuePartialMatch, String... params) {
     /*
      The calling method must call this overloaded version of the method if populating a picker field
      where the suggestions only partially match the input
     */
        String fieldName = getParam(0, params);
        String fieldValue = getParam(1, params);

        try {
            AbstractTempoField tempoField = getFieldType(fieldLayout);
            scrollIntoView(fieldLayout);
            if (tempoField instanceof TempoPickerField) {
                tempoField.populate(fieldLayout, fieldName, fieldValue, String.valueOf(isPickerFieldValuePartialMatch));
            } else {
                tempoField.populate(fieldLayout, fieldName, fieldValue);
            }
            unfocus();
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Populate Field", fieldName, fieldValue);
        }
    }

    public void waitFor(String... params) {
        if (params.length == 1) {
            TempoField.getInstance(settings).waitFor(params);
        } else {
            String fieldType = getParam(0, params);
            String fieldName = getParam(1, params);

            try {
                AbstractTempoField tempoField = getFieldTypeFromString(fieldType);

                tempoField.waitFor(fieldName);
            } catch (Exception e) {
                throw ExceptionBuilder.build(e, settings, "Wait for Field Type", fieldType, fieldName);
            }
        }
    }

    @Override
    public void clear(String... params) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
        clear(fieldLayout, params);
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        String fieldName = getParam(0, params);

        try {
            AbstractTempoField tempoField = getFieldType(fieldLayout);

            scrollIntoView(fieldLayout);
            tempoField.clear(fieldLayout);
            unfocus();
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Clear Field", fieldName);
        }
    }

    public void clearOf(String[] fieldValues, String... params) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
        clearOf(fieldLayout, fieldValues);
    }

    public void clearOf(WebElement fieldLayout, String[] fieldValues) {
        try {
            AbstractTempoField tempoField = getFieldType(fieldLayout);

            if (tempoField instanceof TempoPickerField) {
                scrollIntoView(fieldLayout);
                ((TempoPickerField) tempoField).clearOf(fieldLayout, fieldValues);
            } else if (tempoField instanceof TempoDropdownField) {
                scrollIntoView(fieldLayout);
                ((TempoDropdownField) tempoField).clearOf(fieldLayout, fieldValues);
            } else {
                throw new IllegalArgumentException("A Picker field or Multiple Dropdown field are the only valid " +
                        "options for 'clear of'");
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Clear Field of");
        }
    }

    @Override
    public String capture(String... params) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);

        return capture(fieldLayout, params);
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String fieldName = getParam(0, params);

        try {
            AbstractTempoField tempoField = getFieldType(fieldLayout);
            return tempoField.capture(fieldLayout);

        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Get field value", fieldName);
        }
    }

    @Override
    public String regexCapture(String regex, Integer group, String... params) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);

        return regexCapture(fieldLayout, regex, group, params);
    }

    @Override
    public String regexCapture(WebElement fieldLayout, String regex, Integer group, String... params) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("REGEX FOR FIELD VALUE [" + regex + "]");
        }

        try {
            AbstractTempoField tempoField = getFieldType(fieldLayout);
            String text = tempoField.capture(fieldLayout);
            if (LOG.isDebugEnabled()) {
                LOG.debug("FIELD VALUE [" + text + "]");
            }
            return getRegexResults(regex, group, text);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Field value regex", regex);
        }
    }

    public boolean contains(String... params) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);

        return contains(fieldLayout, params);
    }

    @Override
    public boolean isNotBlank(String... params) {
        WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
        return isNotBlank(fieldLayout);
    }

    @Override
    public boolean containsMultiple(String[] fieldValues, String... params) {
        String fieldName = getParam(0, params);

        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = TempoField.getInstance(settings).getWebElement(params);
            if (!contains(fieldLayout, fieldName, fieldValue)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldName = getParam(0, params);
        String fieldValue = getParam(1, params);

        try {
            AbstractTempoField tempoField = getFieldType(fieldLayout);
            return tempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Field contains", fieldName, fieldValue);
        }
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        AbstractTempoField tempoField = getFieldType(fieldLayout);
        return tempoField.isNotBlank(fieldLayout);
    }

    public AbstractTempoField getFieldType(WebElement fieldLayout) {
        if (TempoGrid.isType(fieldLayout)) {
            return TempoGrid.getInstance(settings);
        } else if (TempoTextField.isType(fieldLayout)) {
            return TempoTextField.getInstance(settings);
        } else if (TempoEncryptedTextField.isType(fieldLayout)) {
            return TempoEncryptedTextField.getInstance(settings);
        } else if (TempoStyledTextField.isType(fieldLayout)) {
            return TempoStyledTextField.getInstance(settings);
        } else if (TempoParagraphField.isType(fieldLayout)) {
            return TempoParagraphField.getInstance(settings);
        } else if (TempoDropdownField.isType(fieldLayout)) {
            return TempoDropdownField.getInstance(settings);
        } else if (TempoRadioField.isType(fieldLayout)) {
            return TempoRadioField.getInstance(settings);
        } else if (TempoCheckboxField.isType(fieldLayout)) {
            return TempoCheckboxField.getInstance(settings);
        } else if (TempoFileUploadField.isType(fieldLayout)) {
            return TempoFileUploadField.getInstance(settings);
        } else if (TempoDatetimeField.isType(fieldLayout)) {
            // Datetime must be before Date
            return TempoDatetimeField.getInstance(settings);
        } else if (TempoDateField.isType(fieldLayout)) {
            return TempoDateField.getInstance(settings);
        } else if (TempoImageField.isType(fieldLayout)) {
            return TempoImageField.getInstance(settings);
        } else if (TempoPickerField.isType(fieldLayout)) {
            return TempoPickerField.getInstance(settings);
        } else if (TempoMilestoneField.isType(fieldLayout)) {
            return TempoMilestoneField.getInstance(settings, fieldLayout);
        } else if (TempoLinkField.isType(fieldLayout)) {
            return TempoLinkField.getInstance(settings);
        } else if (TempoBarcodeField.isType(fieldLayout)) {
            return TempoBarcodeField.getInstance(settings, fieldLayout);
        } else if (TempoGaugeField.isType(fieldLayout)) {
            return TempoGaugeField.getInstance(settings);
        } else if (TempoButton.isType(fieldLayout)) {
            return TempoButton.getInstance(settings);
        } else if (TempoTagField.isType(fieldLayout)) {
            return TempoTagField.getInstance(settings);
        } else if (TempoReadOnlyField.isType(fieldLayout)) {
            return TempoReadOnlyField.getInstance(settings);
        }
        throw new IllegalArgumentException("Unrecognized field type");
    }

    public AbstractTempoField getFieldTypeFromString(String fieldType) {
        fieldType = fieldType.toUpperCase();
        if (fieldType.equals("TEXT")) {
            return TempoTextField.getInstance(settings);
        } else if (fieldType.equals("PARAGRAPH")) {
            return TempoParagraphField.getInstance(settings);
        } else if (fieldType.equals("FILE_UPLOAD")) {
            return TempoFileUploadField.getInstance(settings);
        } else if (fieldType.equals("MILESTONE")) {
            return TempoMilestoneField.getInstance(settings);
        } else if (fieldType.equals("GAUGE")) {
            return TempoGaugeField.getInstance(settings);
        } else if (fieldType.equals("PICKER")) {
            return TempoPickerField.getInstance(settings);
        }
        throw new IllegalArgumentException("Unrecognized field type");
    }

//    public String getLabel(WebElement fieldLayout) {
//        return fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FIELD_LAYOUT_LABEL)).getText();
//    }
    public String getLabels(WebElement fieldLayout) {
        return fieldLayout.findElement(By.xpath(XPATH_ABSOLUTE_FILE_UPLOAD_LABEL)).getText();
    }
}
