package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoNewsItemStarPost extends TempoNewsItem implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsItemStarPost.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_STAR =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemStar");
    private static final String STAR_STATUS_VALUE = "Starred";

    public static TempoNewsItemStarPost getInstance(Settings settings) {
        return new TempoNewsItemStarPost(settings);
    }

    public TempoNewsItemStarPost(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String newsText = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_STAR, newsText);
    }

    @Override
    public void click(String... params) {
        TempoNewsItem.getInstance(settings).refreshAndWaitFor(params);
        String newsText = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("TOGGLE STAR [" + newsText + "]");
        }

        WebElement star = getElement(params);
        clickElement(star);
    }

    public WebElement getElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    public boolean isPostStarred(String... params) {
        WebElement star = getElement(params);
        String alt = star.getAttribute("alt");
        return STAR_STATUS_VALUE.equals(alt);
    }
}
