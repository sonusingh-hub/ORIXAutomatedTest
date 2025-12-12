package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Refreshable;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TempoNewsItemComment extends TempoNewsItem implements Refreshable, RegexCaptureable {

    private static final Logger LOG = LogManager.getLogger(TempoNewsItemComment.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_COMMENT = XPATH_ABSOLUTE_NEWS_ITEM +
            Settings.getByConstant("xpathConcatNewsItemComment");

    public static TempoNewsItemComment getInstance(Settings settings) {
        return new TempoNewsItemComment(settings);
    }

    protected TempoNewsItemComment(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String newsText = getParam(0, params);
        String newsComment = getParam(1, params);

        return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment);
    }

    @Override
    public void waitFor(String... params) {
        String newsText = getParam(1, params);
        String newsComment = params[2];

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and COMMENT [" + newsComment + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Item Comment", newsText, newsComment);
        }
    }

    @Override
    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params) {
        String newsText = getParam(0, params);
        String newsComment = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and COMMENT [" + newsComment + "]");
        }

        try {
            if (waitForPresent) {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
            } else {
                (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.xpath(getXpath(params))));
            }
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Item Comment", newsText, newsComment);
        }

        return true;
    }

    @Override
    public String regexCapture(String regex, Integer group, String... params) {
        String newsText = getParam(0, params);
        String newsComment = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("NEWS ITEM [" + newsText + "] COMMENT [" + newsComment + "] REGEX [" + regex + "]");
        }

        try {
            String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
            return getRegexResults(regex, group, text);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "News Item regex", newsText, regex);
        }
    }
}
