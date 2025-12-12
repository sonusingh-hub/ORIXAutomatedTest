package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoNewsItemPosted extends TempoNewsItem implements WaitFor, Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsItem.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemPostedAtLink");
    private static final String HREF_ATTRIBUTE = "href";

    public static TempoNewsItemPosted getInstance(Settings settings) {
        return new TempoNewsItemPosted(settings);
    }

    protected TempoNewsItemPosted(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String newsText = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT, newsText);
    }

    @Override
    public void waitFor(String... params) {
        String newsText = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Item Posted at", newsText);
        }
    }

    @Override
    public void click(String... params) {
        WebElement link = getElement(params);
        clickElement(link);
    }

    public WebElement getElement(String... params) {
        String newsText = getParam(0, params);

        try {
            return settings.getDriver().findElement(By.xpath(getXpath(params)));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Text", newsText);
        }
    }

    public String getPostId(String newsText) {
        TempoNewsItem.getInstance(settings).refreshAndWaitFor(newsText);
        WebElement postedAtLink = getElement(newsText);
        String linkHRef = postedAtLink.getAttribute(HREF_ATTRIBUTE);
        return getPostIdFromURL(linkHRef);
    }

    public String getPostIdFromURL(String url) {
        if (url.isEmpty()) {
            return "";
        } else {
            String[] refPieces = url.split("/");
            return refPieces[refPieces.length - 1];
        }
    }

}
