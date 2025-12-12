package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TempoTagField extends AbstractTempoField implements WaitForReturn {

    private static final Logger LOG = LogManager.getLogger(TempoTagField.class);
    protected static final String XPATH_ABSOLUTE_TAG_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteTagFieldLabel");
    protected static final String XPATH_ABSOLUTE_TAG_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteTagFieldIndex");
    private static final String XPATH_ABSOLUTE_TAG_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_TAG_FIELD_LABEL + ")[%2$d]";
    protected static final String XPATH_RELATIVE_TAG_GROUP = Settings.getByConstant("xpathRelativeTagGroup");
    protected static final String XPATH_RELATIVE_TAG_ITEM = Settings.getByConstant("xpathRelativeTagItem");
    protected static final String XPATH_RELATIVE_TAG_ITEM_LABEL = Settings.getByConstant("xpathRelativeTagItemLabel");
    protected static final String XPATH_RELATIVE_TAG_ITEM_INDEX =
            "(" + Settings.getByConstant("xpathRelativeTagItem") + ")[%2$d]";
    protected static final String XPATH_RELATIVE_TAG_ITEM_LABEL_INDEX = "(" + XPATH_RELATIVE_TAG_ITEM_LABEL + ")[%2$d]";
    protected static final String XPATH_ABSOLUTE_TAG_ITEM_LABEL = Settings.getByConstant("xpathAbsoluteTagItemLabel");
    protected static final String XPATH_ABSOLUTE_TAG_ITEM_INDEX = Settings.getByConstant("xpathAbsoluteTagItemIndex");
    protected static final String XPATH_ABSOLUTE_TAG_ITEM_LABEL_INDEX = "(" + XPATH_ABSOLUTE_TAG_ITEM_LABEL + ")[%2$d]";


    public static TempoTagField getInstance(Settings settings) {
        return new TempoTagField(settings);
    }

    TempoTagField(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String fieldName = getParam(0, params);

        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            String name = getFieldFromFieldIndex(fieldName);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(XPATH_ABSOLUTE_TAG_FIELD_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_TAG_FIELD_LABEL_INDEX, name, index);
            }

        } else {
            return xpathFormat(XPATH_ABSOLUTE_TAG_FIELD_LABEL, fieldName);
        }
    }

    public void clickOnTagFieldTagItem(String tagField, String tagItem) {
        try {
            WebElement tagFieldWebElement = settings.getDriver().findElement(By.xpath(getXpath(tagField)));
            String relativeTagItemXpath = getTagItemRelativeXpath(tagItem);
            WebElement tagItemWebElement = tagFieldWebElement.findElement(By.xpath(relativeTagItemXpath));
            clickElement(tagItemWebElement);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Unable to click tag field: ", tagField, " tagItem: ", tagItem);
        }
    }

    public void clickOnTagItem(String tagItem) {
        try {
            WebElement tagItemWebElement = settings.getDriver().findElement(By.xpath(getTagItemAbsoluteXpath(tagItem)));
            clickElement(tagItemWebElement);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Unable to click absolute tagItem: ", tagItem);
        }
    }

    private String getTagItemRelativeXpath(String tagItem) {
        if (isFieldIndex(tagItem)) {
            int index = getIndexFromFieldIndex(tagItem);
            String name = getFieldFromFieldIndex(tagItem);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(XPATH_RELATIVE_TAG_ITEM_INDEX, index);
            } else {
                return xpathFormat(XPATH_RELATIVE_TAG_ITEM_LABEL_INDEX, name, index);
            }
        } else {
            return xpathFormat(XPATH_RELATIVE_TAG_ITEM_LABEL, tagItem);
        }
    }

    private String getTagItemAbsoluteXpath(String tagItem) {
        if (isFieldIndex(tagItem)) {
            int index = getIndexFromFieldIndex(tagItem);
            String name = getFieldFromFieldIndex(tagItem);
            if (StringUtils.isBlank(name)) {
                return xpathFormat(XPATH_ABSOLUTE_TAG_ITEM_INDEX, index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_TAG_ITEM_LABEL_INDEX, name, index);
            }
        } else {
            return xpathFormat(XPATH_ABSOLUTE_TAG_ITEM_LABEL, tagItem);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String fieldName = getParam(0, params);

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(params))));
            }
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Tag Field", fieldName);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, String... params) {
        return waitForReturn(waitForPresent, settings.getTimeoutSeconds(), params);
    }

    public boolean waitForTagItemReturn(String tagItem) {
        String tagItemAbsoluteXpath = getTagItemAbsoluteXpath(tagItem);
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(tagItemAbsoluteXpath)));
            return true;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Tag Item", tagItem);
        }
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR TAG FIELD [" + fieldName + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Tag Field", fieldName);
        }
    }

    public void waitForAbsoluteTagItem(String tagItem) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR TAG ITEM [" + tagItem + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getTagItemAbsoluteXpath(tagItem))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Tag Item", tagItem);
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) {
        //NOOP
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        List<String> tagItemNames = new ArrayList<>();
        List<WebElement> tagItems = ((RemoteWebElement) fieldLayout).findElements(By.xpath(XPATH_RELATIVE_TAG_ITEM));
        for (WebElement tagItem : tagItems) {
            tagItemNames.add(tagItem.getText());
        }
        return String.join(",", tagItemNames);
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldValue = getParam(0, params);
        List<WebElement> tagItems = ((RemoteWebElement) fieldLayout).findElements(By.xpath(XPATH_RELATIVE_TAG_ITEM));
        for (WebElement tagItem : tagItems) {
            if (tagItem.getText().contains(fieldValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        //NOOP
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_TAG_GROUP));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
