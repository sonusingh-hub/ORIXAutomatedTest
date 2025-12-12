package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.ContainerCaptureable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.appiancorp.ps.automatedtest.common.WebElementUtilities.xpathExistsInField;

public class TempoGrid extends AbstractTempoField implements WaitFor, Container, ContainerCaptureable {

    private static final Logger LOG = LogManager.getLogger(TempoGrid.class);
    protected static final String XPATH_ABSOLUTE_GRID_BY_INDEX = Settings.getByConstant("xpathAbsoluteGridByIndex");
    protected static final String XPATH_ABSOLUTE_GRID_BY_LABEL = Settings.getByConstant("xpathAbsoluteGridByLabel");
    protected static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX = "(" + XPATH_ABSOLUTE_GRID_BY_LABEL + ")[%2$d]";

    private static final String XPATH_RELATIVE_GRID_FIELD = Settings.getByConstant("xpathRelativeGridField");
    protected static final String XPATH_RELATIVE_HEADER_ROW = Settings.getByConstant("xpathRelativeGridHeaderRow");
    protected static final String XPATH_RELATIVE_ROW = Settings.getByConstant("xpathRelativeGridRow");
    protected static final String XPATH_RELATIVE_COLUMN = Settings.getByConstant("xpathRelativeGridColumn");

    public static TempoGrid getInstance(Settings settings) {
        return new TempoGrid(settings);
    }

    protected TempoGrid(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String gridName = getParam(0, params);

        WebElement grid = null;
        if (isFieldIndex(gridName)) {
            int gNum = getIndexFromFieldIndex(gridName);
            String gName = getFieldFromFieldIndex(gridName);
            if (StringUtils.isBlank(gName)) {
                grid = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum)));
            } else {
                grid = settings.getDriver()
                        .findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum)));
            }
        } else {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName))));
            grid = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName)));
        }

        return getXpathLocator(grid);
    }

    @Override
    public void waitFor(String... params) {
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws Exception {
    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws Exception {
        return false;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return !Strings.isNullOrEmpty(getGridContents(fieldLayout).toString());
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        return getGridContents(fieldLayout).toString();
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {

    }

    public JSONObject getContents(String... params) {
        JSONObject jp = new JSONObject();
        WebElement grid = getWebElement(params);
        String gridLabel = TempoFieldFactory.getInstance(settings).getLabel(grid);

        jp.put(gridLabel, getGridContents(grid));
        return jp;
    }

    public JSONObject getContents1(String... params) {
        JSONObject jp = new JSONObject();
        WebElement grid = getWebElement(params);
        String gridLabel = TempoFieldFactory.getInstance(settings).getLabels(grid);

        jp.put(gridLabel, getGridContents(grid));
        return jp;
    }
    public JSONObject getContents2(String... params) {
        JSONObject jp = new JSONObject();
        WebElement grid = getWebElement(params);
        String gridLabel = TempoFieldFactory.getInstance(settings).getLabels2(grid);

        jp.put(gridLabel, getGridContents(grid));
        return jp;
    }

    private JSONArray getGridContents(WebElement grid) {
        List<WebElement> headers =
                grid.findElement(By.xpath(XPATH_RELATIVE_HEADER_ROW)).findElements(By.xpath(XPATH_RELATIVE_COLUMN));
        List<String> labels = new ArrayList<String>();

        for (int i = 1; i <= headers.size(); i++) {
            String headerText = headers.get(i - 1).getText();
            String key;

            try {
                WebElement accessibility = ((RemoteWebElement) headers.get(i - 1)).findElement(
                        By.className("GridHeaderCell---accessibilityhidden"));
                headerText = headerText.replace(accessibility.getText(), "");
                headerText = headerText.replace("\n", "");
            } catch (NoSuchElementException e) {
            }

            if (StringUtils.isEmpty(headerText)) {
                key = "[" + i + "]";
            } else if (labels.contains(headerText)) {
                key = headerText + "[" + countLabels(headerText, labels) + "]";
            } else {
                key = headerText;
            }
            labels.add(key);
        }

        List<WebElement> rows = grid.findElements(By.xpath(XPATH_RELATIVE_ROW));
        JSONArray ja = new JSONArray();
        for (int i = 1; i <= rows.size(); i++) {
            List<WebElement> columns = rows.get(i - 1).findElements(By.xpath(XPATH_RELATIVE_COLUMN));
            JSONObject jo = new JSONObject();
            for (int j = 1; j <= columns.size(); j++) {
                WebElement cell =
                        TempoGridCell.getInstance(settings).getGridWebElement(grid, null, "[" + j + "]", "[" + i + "]");
                try {
                    String value = TempoFieldFactory.getInstance(settings).capture(cell, "");
                    jo.put(labels.get(j - 1), value);
                } catch (IllegalArgumentException e) {
                    LOG.warn("UNABLE TO GET VALUE FOR ROW [" + i + "] COLUMN [" + j + "]");
                }
            }
            ja.put(i - 1, jo);
        }

        return ja;
    }

    public static boolean isType(WebElement fieldLayout) {
        return xpathExistsInField(XPATH_RELATIVE_GRID_FIELD, fieldLayout);
    }

    private int countLabels(String search, List<String> labels) {
        int count = 1;
        for (String label : labels) {
            if (label.startsWith(search)) {
                count += 1;
            }
        }

        return count;
    }
}
