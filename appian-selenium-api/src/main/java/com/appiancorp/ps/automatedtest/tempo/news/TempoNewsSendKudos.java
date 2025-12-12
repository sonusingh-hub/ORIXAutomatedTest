package com.appiancorp.ps.automatedtest.tempo.news;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class TempoNewsSendKudos extends AppianObject {

    private static final String XPATH_CLICK_TO_POST = Settings.getByConstant("xpathClickToPost");
    private static final String XPATH_CLICK_TO_KUDOS = Settings.getByConstant("xpathClickToKudos");
    private static final String XPATH_CLICK_RECIPIENT = Settings.getByConstant("xpathClickToAddKudosRecipient");
    private static final String XPATH_KUDOS_MESSAGE_BOX = Settings.getByConstant("xpathClickKudosMessageBox");
    private static final String XPATH_SUBMIT_BUTTON = Settings.getByConstant("xpathClickKudosSubmit");
    private static final String XPATH_SOCIAL_BOX = Settings.getByConstant("xpathSocialBoxContainer");

    public static TempoNewsSendKudos getInstance(Settings settings) {
        return new TempoNewsSendKudos(settings);
    }

    protected TempoNewsSendKudos(Settings settings) {
        super(settings);
    }

    public void activateKudos() {
        TempoNewsSocialBox.getInstance(settings).refreshAndWaitFor(XPATH_CLICK_TO_POST);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_TO_POST);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_TO_KUDOS);
    }

    public void addRecipient(String recipient) {
        TempoNewsSocialBox.getInstance(settings).click(XPATH_CLICK_RECIPIENT);
        WebElement recipientField = getElement(XPATH_CLICK_RECIPIENT);
        recipientField.sendKeys(recipient);
        TempoNewsSocialBox.getInstance(settings).waitFor(XPATH_SOCIAL_BOX);
        recipientField.sendKeys(Keys.RETURN);
    }

    public WebElement getElement(String xpath) {
        try {
            return settings.getDriver().findElement(By.xpath(xpath));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Xpath", xpath);
        }
    }

    public void addMessage(String message) {
        TempoNewsSocialBox.getInstance(settings).click(XPATH_KUDOS_MESSAGE_BOX);
        WebElement messageField = getElement(XPATH_KUDOS_MESSAGE_BOX);
        messageField.sendKeys(message);
        TempoNewsSocialBox.getInstance(settings).click(XPATH_SUBMIT_BUTTON);
    }

}
