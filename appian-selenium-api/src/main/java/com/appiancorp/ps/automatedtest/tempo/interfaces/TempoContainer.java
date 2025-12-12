package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Container;
import com.appiancorp.ps.automatedtest.properties.ContainerCaptureable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TempoContainer extends AppianObject implements Container, ContainerCaptureable, WaitFor {

    private static final Logger LOG = LogManager.getLogger(TempoContainer.class);
    private static final String XPATH_RELATIVE_FIELD_LAYOUT = Settings.getByConstant("xpathRelativeFieldLayout");

    protected TempoContainer(Settings settings) {
        super(settings);
    }

    public JSONObject getContents(String... params) {
        List<WebElement> fieldLayouts = getWebElement(params).findElements(By.xpath(XPATH_RELATIVE_FIELD_LAYOUT));

        JSONObject jo = new JSONObject();
        for (WebElement w : fieldLayouts) {
            String label = TempoFieldFactory.getInstance(settings).getLabel(w);
            if (StringUtils.isNotBlank(label)) {
                try {
                    TempoFieldFactory tf = TempoFieldFactory.getInstance(settings);

                    AbstractTempoField tempoField = tf.getFieldType(w);
                    String value = tf.capture(w, label);
                    if (tempoField instanceof TempoGrid) {
                        jo.put(label, new JSONArray(value));
                    } else {
                        jo.put(label, value);
                    }
                } catch (IllegalArgumentException e) {
                    LOG.warn("UNABLE TO GET VALUE FOR " + label);
                }
            }
        }

        return jo;
    }

    @Override
    public WebElement getWebElement(String... params) {
        return null;
    }

    @Override
    public void waitFor(String... params) {

    }

    @Override
    public String getXpath(String... params) {
        return null;
    }
}
