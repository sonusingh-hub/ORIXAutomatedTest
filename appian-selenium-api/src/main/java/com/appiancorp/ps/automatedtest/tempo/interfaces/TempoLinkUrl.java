package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.Verifiable;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoLinkUrl extends TempoLink implements Verifiable, Captureable {

    private static final Logger LOG = LogManager.getLogger(TempoLinkUrl.class);

    public static TempoLinkUrl getInstance(Settings settings) {
        return new TempoLinkUrl(settings);
    }

    private TempoLinkUrl(Settings settings) {
        super(settings);
    }

    @Override
    public String capture(String... params) {
        String linkName = getParam(0, params);

        WebElement link = settings.getDriver().findElement(By.xpath(getXpath(linkName)));
        String linkURL = link.getAttribute("href");
        return linkURL;
    }

    @Override
    public boolean contains(String... params) {
        String linkName = getParam(0, params);
        String linkURLValue = getParam(1, params);

        String linkURLText = capture(linkName);

        if (LOG.isDebugEnabled()) {
            LOG.debug("READ ONLY FIELD COMPARISON : Link field URL value [" + linkURLText +
                    "] compared to Entered value [" + linkURLValue + "]");
        }

        return (linkURLText.contains(linkURLValue) && !Strings.isNullOrEmpty(linkURLValue));
    }
}
