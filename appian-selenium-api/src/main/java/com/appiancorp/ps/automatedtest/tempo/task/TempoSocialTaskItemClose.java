package com.appiancorp.ps.automatedtest.tempo.task;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TempoSocialTaskItemClose extends TempoSocialTaskItem implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoSocialTaskItemClose.class);
    private static final String XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE =
            XPATH_ABSOLUTE_SOCIAL_TASK_ITEM + Settings.getByConstant("xpathConcatSocialTaskItemClose");
    private static final String XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE_COMMENT_BOX =
            XPATH_ABSOLUTE_SOCIAL_TASK_ITEM + Settings.getByConstant("xpathConcatSocialTaskItemCloseCommentBox");
    private static final String XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE_COMMENT_BUTTON =
            XPATH_ABSOLUTE_SOCIAL_TASK_ITEM + Settings.getByConstant("xpathConcatSocialTaskItemCloseCommentButton");

    public static TempoSocialTaskItemClose getInstance(Settings settings) {
        return new TempoSocialTaskItemClose(settings);
    }

    protected TempoSocialTaskItemClose(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String xpathToFormat = getParam(0, params);
        String taskText = getParam(1, params);
        return xpathFormat(xpathToFormat, taskText);
    }

    @Override
    public void click(String... params) {
        String taskText = getParam(1, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("INTERACT WITH CLOSE ON TASK [" + taskText + "]");
        }
        waitFor(params);
        WebElement element = getElement(params);
        clickElement(element);
    }

    public WebElement getElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }

    public void activateCloseTask(String taskText) {
        click(XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE, taskText);
    }

    public void addCloseComment(String comment, String taskText) {
        click(XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE_COMMENT_BOX, taskText);

        if (comment != null && !comment.isEmpty()) {
            WebElement commentBox = getElement(XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE_COMMENT_BOX, taskText);
            commentBox.sendKeys(comment);
        }

        click(XPATH_ABSOLUTE_SOCIAL_TASK_ITEM_CLOSE_COMMENT_BUTTON, taskText);
    }
}
