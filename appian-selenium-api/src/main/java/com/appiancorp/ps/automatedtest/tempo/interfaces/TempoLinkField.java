package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoLinkField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoLinkField.class);
    private static final String XPATH_ABSOLUTE_LINK_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteLinkFieldLabel");
    private static final String XPATH_ABSOLUTE_LINK_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteLinkFieldIndex");
    private static final String XPATH_ABSOLUTE_LINK_FIELD_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_LINK_FIELD_LABEL + ")[%2$d]";
    private static final String XPATH_RELATIVE_LINK_FIELD_LINK = Settings.getByConstant("xpathRelativeLinkFieldLink");

    public static TempoLinkField getInstance(Settings settings) {
        return new TempoLinkField(settings);
    }

    protected TempoLinkField(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String name = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(
                        XPATH_ABSOLUTE_LINK_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
            } else {
                return xpathFormat(
                        XPATH_ABSOLUTE_LINK_FIELD_LABEL_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                        name,
                        index);
            }

        } else {
            return xpathFormat(XPATH_ABSOLUTE_LINK_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                    fieldName);
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws Exception {
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws Exception {
        String fieldValue = getParam(0, params);

        String compareString = capture(fieldLayout);
        if (LOG.isDebugEnabled()) {
            LOG.debug("LINK FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" +
                    compareString + "]");
        }

        return compareString.contains(fieldValue);
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_LINK_FIELD_LINK)).getText();
        if (LOG.isDebugEnabled()) {
            LOG.debug("LINK FIELD VALUE : " + value);
        }

        return value;
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_LINK_FIELD_LINK));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
