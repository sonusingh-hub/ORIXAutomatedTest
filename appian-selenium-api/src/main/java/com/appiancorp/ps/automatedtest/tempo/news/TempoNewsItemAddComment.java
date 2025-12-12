package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoNewsItemAddComment extends TempoNewsItem implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsItemAddComment.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_ADD_COMMENT =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsAddComment");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_ADD_COMMENT_BOX =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsAddCommentBox");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_SUBMIT_COMMENT_BUTTON =
            XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsSubmitCommentButton");

    public static TempoNewsItemAddComment getInstance(Settings settings) {
        return new TempoNewsItemAddComment(settings);
    }

    protected TempoNewsItemAddComment(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String xpathToFormat = getParam(0, params);
        String newsText = getParam(1, params);
        return xpathFormat(xpathToFormat, newsText);
    }

    @Override
    public void click(String... params) {
        String newsText = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("INTERACT WITH COMMENT ON POST [" + newsText + "]");
        }
        waitFor(params);
        WebElement element = getElement(params);
        clickElement(element);
    }

    @Override
    public void waitFor(String... params) {
        String newsText = getParam(1, params);
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.elementToBeClickable(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Text", newsText);
        }
    }

    public WebElement getElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    public void activateComment(String newsText) {
        click(XPATH_ABSOLUTE_NEWS_ITEM_ADD_COMMENT, newsText);
    }

    public void addComment(String comment, String newsText) {
        click(XPATH_ABSOLUTE_NEWS_ITEM_ADD_COMMENT_BOX, newsText);
        WebElement commentBox = getElement(XPATH_ABSOLUTE_NEWS_ITEM_ADD_COMMENT_BOX, newsText);
        commentBox.sendKeys(comment);
        click(XPATH_ABSOLUTE_NEWS_ITEM_SUBMIT_COMMENT_BUTTON, newsText);
    }
}
