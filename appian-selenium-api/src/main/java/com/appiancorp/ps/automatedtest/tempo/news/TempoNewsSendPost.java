package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TempoNewsSendPost extends AppianObject {

    private static final String XPATH_CLICK_TO_POST = Settings.getByConstant("xpathClickToPost");
    private static final String XPATH_CLICK_TOGGLE_PARTICIPANTS = Settings.getByConstant(
            "xpathTogglePostParticipants");
    private static final String XPATH_CLICK_ADD_PARTICIPANTS = Settings.getByConstant(
            "xpathAddPostParticipants");
    private static final String XPATH_MESSAGE_BOX = Settings.getByConstant("xpathClickPostMessageBox");
    private static final String XPATH_SOCIAL_BOX = Settings.getByConstant("xpathSocialBoxContainer");
    private static final String XPATH_SUBMIT_BUTTON = Settings.getByConstant("xpathClickPostSubmit");

    public static TempoNewsSendPost getInstance(Settings settings) {
        return new TempoNewsSendPost(settings);
    }

    public TempoNewsSendPost(Settings settings) {
        super(settings);
    }

    public void activatePost() {
        TempoNewsSocialBox.getInstance(settings).refreshAndWaitFor(XPATH_CLICK_TO_POST);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_TO_POST);
    }

    public void addParticipants(List<String> participants) {
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_TOGGLE_PARTICIPANTS);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_ADD_PARTICIPANTS);
        WebElement participantsField = getElement(XPATH_CLICK_ADD_PARTICIPANTS);

        for (String participant : participants) {
            participantsField.sendKeys(participant);
            TempoNewsSocialBox.getInstance(settings).waitFor(XPATH_SOCIAL_BOX);
            participantsField.sendKeys(Keys.RETURN);
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
