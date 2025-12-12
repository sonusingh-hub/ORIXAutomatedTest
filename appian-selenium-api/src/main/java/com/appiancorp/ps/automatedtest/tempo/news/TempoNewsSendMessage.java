package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TempoNewsSendMessage extends AppianObject {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(TempoNewsSendMessage.class);
    private static final String XPATH_CLICK_TO_POST = Settings.getByConstant("xpathClickToPost");
    private static final String XPATH_CLICK_TO_MESSAGE = Settings.getByConstant("xpathClickToMessage");
    private static final String XPATH_CLICK_RECIPIENTS = Settings.getByConstant(
            "xpathClickToAddMessageRecipients");
    private static final String XPATH_LOCK_MESSAGE = Settings.getByConstant("xpathClickToLockMessage");
    private static final String XPATH_MESSAGE_BOX = Settings.getByConstant("xpathClickMessageBox");
    private static final String XPATH_SUBMIT_BUTTON = Settings.getByConstant("xpathClickMessageSubmit");
    private static final String XPATH_SOCIAL_BOX = Settings.getByConstant("xpathSocialBoxContainer");

    public TempoNewsSendMessage(Settings settings) {
        super(settings);
    }

    public static TempoNewsSendMessage getInstance(Settings settings) {
        return new TempoNewsSendMessage(settings);
    }

    public void activateMessage() {
        TempoNewsSocialBox.getInstance(settings).refreshAndWaitFor(XPATH_CLICK_TO_POST);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_TO_POST);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_TO_MESSAGE);
    }

    public void addRecipients(List<String> recipients, Boolean locked) {
        if (locked) {
            TempoNewsSocialBox.getInstance(settings).click(XPATH_LOCK_MESSAGE);
        }

        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_RECIPIENTS);
        WebElement recipientsField = getElement(XPATH_CLICK_RECIPIENTS);

        for (String recipient : recipients) {
            recipientsField.sendKeys(recipient);
            TempoNewsSocialBox.getInstance(settings).waitFor(XPATH_SOCIAL_BOX);
            recipientsField.sendKeys(Keys.RETURN);
        }
    }

    private WebElement getElement(String xpath) {
        try {
            return settings.getDriver().findElement(By.xpath(xpath));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Xpath", xpath);
        }
    }

    public void addMessage(String message) {
        TempoNewsSocialBox.getInstance(settings).click(XPATH_MESSAGE_BOX);
        WebElement messageField = getElement(XPATH_MESSAGE_BOX);
        messageField.sendKeys(message);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_SUBMIT_BUTTON);
    }

}
