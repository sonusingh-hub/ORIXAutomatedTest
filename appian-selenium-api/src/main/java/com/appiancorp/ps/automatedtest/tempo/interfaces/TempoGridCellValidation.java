package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TempoGridCellValidation extends AppianObject implements Captureable {

    private static final String XPATH_GRID_CELL_VALIDATION_MESSAGE = 
        ".//span[@class='EditableGridLayout---accessibilityhidden']";

    public static TempoGridCellValidation getInstance(Settings settings) {
        return new TempoGridCellValidation(settings);
    }

    protected TempoGridCellValidation(Settings settings) {
        super(settings);
    }

    @Override
    public String capture(String... params) {
        String gridName = getParam(0, params);
        String columnName = getParam(1, params);
        String rowNum = getParam(2, params);

        WebElement gridCell = TempoGridCell.getInstance(settings).getWebElement(gridName, columnName, rowNum);
        List<String> values = new ArrayList<>();

        for (WebElement validationElement : gridCell.findElements(By.xpath(XPATH_GRID_CELL_VALIDATION_MESSAGE))) {
            String text = validationElement.getText().trim();
            if (!text.isEmpty()) {
                values.add(text);
            }
        }

        return String.join(",", values);
    }
}
