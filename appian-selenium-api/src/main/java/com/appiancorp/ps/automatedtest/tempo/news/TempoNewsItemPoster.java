package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoNewsItemPoster extends TempoNewsItem implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsItemPoster.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_POSTER_CIRCLE =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsPostedByCircle");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_POSTER_LINK =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsPostedByLink");
    private static final String XPATH_ABSOLUTE_USER_PROFILE_HOVER_CARD_USER_NAME = Settings.getByConstant(
            "xpathUserProfileHoverCardUserName");

    public static TempoNewsItemPoster getInstance(Settings settings) {
        return new TempoNewsItemPoster(settings);
    }

    protected TempoNewsItemPoster(Settings settings) {
        super(settings);
    }

    @Override
    public void click(String... params) {
        String newsText = getParam(1, params);

        waitFor(params);

        try {
            WebElement userProfileCircle = getElement(getXpath(params));
            clickElement(userProfileCircle);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News poster profile circle on post containing", newsText);
        }
    }

    @Override
    public String getXpath(String... params) {
        String xpathToFormat = getParam(0, params);
        String stringToReplace = getParam(1, params);

        return xpathFormat(xpathToFormat, stringToReplace);
    }

    @Override
    public void waitFor(String... params) {
        String newsText = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + newsText + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Item", newsText);
        }
    }

    public boolean hoverOnPosterCircle(String newsText) {
        return hoverOnProfile(XPATH_ABSOLUTE_NEWS_ITEM_POSTER_CIRCLE, newsText);
    }

    public boolean hoverOnPosterLink(String newsText) {
        return hoverOnProfile(XPATH_ABSOLUTE_NEWS_ITEM_POSTER_LINK, newsText);
    }

    public boolean hoverOnProfile(String... params) {
        String newsText = getParam(1, params);
        String xpathToHover = getXpath(getParam(0, params), newsText);

        waitFor(params);

        String newsPosterName = getNewsPosterName(newsText);

        if (LOG.isDebugEnabled()) {
            LOG.debug(
                    "HOVER ON USER PROFILE OF POST WITH TEXT [" + newsText + "] POSTED BY [" + newsPosterName + "]");
        }

        WebElement newsPosterCircle = getElement(xpathToHover);
        Actions builder = new Actions(settings.getDriver());
        builder.moveToElement(newsPosterCircle).perform();

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(getXpath(XPATH_ABSOLUTE_USER_PROFILE_HOVER_CARD_USER_NAME, newsPosterName))));
            return true;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Hover Card", newsText);
        }
    }

    public String getNewsPosterName(String newsText) {
        WebElement newsPoster = getElement(getXpath(XPATH_ABSOLUTE_NEWS_ITEM_POSTER_LINK, newsText));
        return newsPoster.getAttribute("text");
    }

    public void clickUserProfileCircle(String newsText) {
        click(XPATH_ABSOLUTE_NEWS_ITEM_POSTER_CIRCLE, newsText);
    }

    public void clickUserProfileLink(String newsText) {
        click(XPATH_ABSOLUTE_NEWS_ITEM_POSTER_LINK, newsText);
    }

    public WebElement getElement(String xpathToFind) {
        return settings.getDriver().findElement(By.xpath(xpathToFind));
    }

}
