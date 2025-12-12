package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoNewsItemMoreInfo extends TempoNewsItem implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsItemMoreInfo.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK = XPATH_ABSOLUTE_NEWS_ITEM +
            Settings.getByConstant("xpathConcatNewsItemMoreInfoLink");

    public static TempoNewsItemMoreInfo getInstance(Settings settings) {
        return new TempoNewsItemMoreInfo(settings);
    }

    protected TempoNewsItemMoreInfo(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String newsText = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK, newsText);
    }

    @Override
    public void click(String... params) {
        String newsText = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("TOGGLE MORE INFO [" + newsText + "]");
        }

        WebElement moreInfoLink = settings.getDriver().findElement(By.xpath(getXpath(params)));
        clickElement(moreInfoLink);
    }
}
