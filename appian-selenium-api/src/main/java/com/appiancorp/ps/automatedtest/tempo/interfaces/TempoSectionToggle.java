package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoSectionToggle extends TempoSection implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoSectionToggle.class);

    protected static final String XPATH_RELATIVE_SECTION_TOGGLE = Settings.getByConstant("xpathRelativeSectionToggle");

    public static TempoSectionToggle getInstance(Settings settings) {
        return new TempoSectionToggle(settings);
    }

    protected TempoSectionToggle(Settings settings) {
        super(settings);
    }

    public void click(String... params) {
        String sectionName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("EXPAND SECTION [" + sectionName + "]");
        }

        try {
            WebElement section = getWebElement(sectionName);
            WebElement expand = section.findElement(By.xpath(XPATH_RELATIVE_SECTION_TOGGLE));
            clickElement(expand);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Expand Section", sectionName);
        }
    }
}
