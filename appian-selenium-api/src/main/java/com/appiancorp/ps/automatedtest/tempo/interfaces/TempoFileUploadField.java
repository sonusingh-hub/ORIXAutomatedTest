package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.google.common.base.Strings;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TempoFileUploadField extends AbstractTempoField {

    private static final Logger LOG = LogManager.getLogger(TempoFileUploadField.class);
    private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL =
            Settings.getByConstant("xpathAbsoluteFileUploadFieldLabel");
    private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL_INDEX =
            "(" + XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL + ")[%2$d]";
    private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_INDEX =
            Settings.getByConstant("xpathAbsoluteFileUploadFieldIndex");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT =
            Settings.getByConstant("xpathRelativeFileUploadFieldInput");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE =
            Settings.getByConstant("xpathRelativeFileUploadFieldFile");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_EXT =
            Settings.getByConstant("xpathRelativeFileUploadFieldExtension");
    private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_REMOVE_LINK = Settings
            .getByConstant("xpathRelativeFileUploadFieldRemoveLink");
    private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_WAITING = XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL +
            Settings.getByConstant("xpathRelativeFileUploadFieldWaiting");
    private static final Pattern FILENAME_PATTERN = Pattern.compile("(.*) \\(.*\\)");

    public static TempoFileUploadField getInstance(Settings settings) {
        return new TempoFileUploadField(settings);
    }

    private TempoFileUploadField(Settings settings) {
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
                        XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                        index);
            } else {
                return xpathFormat(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL_INDEX +
                                TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                        name, index);
            }

        } else {
            return xpathFormat(
                    XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
                    fieldName);
        }
    }

    @Override
    public void waitFor(String... params) {
        String fieldName = getParam(0, params);

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
        }
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws InterruptedException {
        String fieldValue = FilenameUtils.separatorsToSystem(getParam(1, params));
        String fieldName = FilenameUtils.separatorsToSystem(getParam(0, params));

        if (LOG.isDebugEnabled()) {
            LOG.debug("POPULATION [" + fieldValue + "]");
        }

        WebElement fileUpload = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
        toggleVisibilityForAppian171(fileUpload);
        TimeUnit.SECONDS.sleep(1);
        fileUpload.sendKeys(fieldValue);
        unfocus(300);
        waitForFileUpload(fieldName, fieldLayout);
    }

    private void toggleVisibilityForAppian171(WebElement fileUpload) {
        if (((String) fileUpload.getAttribute("class")).contains("---ui-inaccessible")) {
            String fileUploadId = fileUpload.getAttribute("id");
            JavascriptExecutor jsExecutor = (JavascriptExecutor) settings.getDriver();
            jsExecutor.executeScript("document.getElementById('" + fileUploadId + "').setAttribute('class', '')");
        }
    }

    public void waitForFileUpload(String fieldName, WebElement fieldLayout) {
        String xpathLocator = getXpathLocator(fieldLayout);
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(xpathFormat("(" + xpathLocator + ")" +
                                XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_WAITING, fieldName))));
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        List<WebElement> webElements =
                fieldLayout.findElements(By.xpath(xpathFormat(XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE)));
        String[] values = new String[webElements.size()];
        Matcher m;

        for (int i = 0; i < webElements.size(); i++) {
            values[i] = webElements.get(i).getText();
            m = FILENAME_PATTERN.matcher(values[i]);

            if (m.find()) {
                values[i] = m.group(1);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("FILE UPLOAD FIELD VALUE : " + values);
        }

        WebElement info = null;
        try {
            // Only multiple file upload
            info = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_FILE_UPLOAD_FIELD_EXT)));
        } catch (Exception ignored) {
        }
        if (info != null) {
            String extension = info.getText().split("\\s")[0];
            for (int i = 0; i < webElements.size(); i++) {
                values[i] += "." + extension.toLowerCase();
            }
        } else {
            for (int i = 0; i < webElements.size(); i++) {
                String filename = values[i].split("\\n")[0];
                String extension = values[i].split("\\n")[1].split("\\s")[0];
                values[i] = filename + "." + extension.toLowerCase();
            }
        }

        String value = "";

        for (int i = 0; i < values.length; i++) {
            value += values[i] + ":";
        }

        if (value.endsWith(":")) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) {
        String fieldValue = FilenameUtils.separatorsToSystem(getParam(0, params));

        // For read-only
        try {
            return TempoFieldFactory.getInstance(settings).contains(fieldLayout, fieldValue);
        } catch (Exception e) {
        }

        fieldValue = Paths.get(fieldValue).getFileName().toString();
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(uploadFieldContainsFile(fieldLayout, fieldValue));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(capture(fieldLayout));
    }

    private ExpectedCondition<Boolean> uploadFieldContainsFile(WebElement uploadField, String expectedFileName) {
        return webDriver -> {
            String compareString = FilenameUtils.separatorsToSystem(capture(uploadField));
            if (LOG.isDebugEnabled()) {
                LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value [" + expectedFileName +
                        "] compared to Entered value [" + compareString + "]");
            }
            return compareString.contains(expectedFileName);
        };
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
        List<WebElement> removeLinkElements =
                fieldLayout.findElements(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_REMOVE_LINK));

        if (removeLinkElements.size() > 0) {
            WebElement removeLink = removeLinkElements.get(0);
            for (Iterator<WebElement> iterator = removeLinkElements.iterator(); iterator.hasNext();) {
                removeLink.click();
                // Sometimes the first click fails to properly activate and remove the selected document.
                // We have to try again but catch the StaleElementReferenceException in case the first click worked.
                // In 16.3, we also have to catch the ElementNotInteractableException for the same reason.
                try {
                    removeLink.click();
                } catch (ElementNotInteractableException | StaleElementReferenceException e) {
                    //Swallow exception
                }

                iterator.next();

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    //Swallow exception
                }
            }
        }
    }

    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
            return true;
        } catch (Exception e) {
            try {
                fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE));
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }
}
