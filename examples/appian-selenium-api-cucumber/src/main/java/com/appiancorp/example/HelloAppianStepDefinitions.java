package com.appiancorp.example;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.fixture.SitesFixture;

import io.cucumber.java.en.Given;

public class HelloAppianStepDefinitions {
    protected static String TEST_BROWSER = "CHROME";
    protected static String TEST_SITE_VERSION = "DEFAULT_APPIAN_VERSION_REPLACEMENT_KEY";
    protected static String TEST_SITE_URL = "https://yourAppianSite.com";
    protected static String TEST_SITE_LOCALE = "en_US";
    protected static String TEST_USERNAME = "admin.user"; //Make sure this username has an entry in users.properties
    protected static Integer TEST_TIMEOUT = 60;

    private static SitesFixture fixture = new SitesFixture();

    public HelloAppianStepDefinitions() {
        fixture.setSettings(HelloAppianStepDefinitions.getSettings());
    }

    @Given("I login with credentials")
    public void loginWithCredentials() {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.setTimeoutSecondsTo(TEST_TIMEOUT);
        fixture.setAppianVersionTo(TEST_SITE_VERSION);
        fixture.setAppianLocaleTo(TEST_SITE_LOCALE);
        fixture.loginWithUsername(TEST_USERNAME);
    }

    @Given("I click on action {string}")
    public void loginAndClickOnAction(String actionName) {
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction(actionName);
    }

    @Given("I verify fields")
    public void verifyFields() {
        fixture.verifyButtonIsPresent("Submit");
        fixture.verifyFieldIsPresent("Title");
    }

    @Given("I tear down site")
    public void tearDownSite() {
        fixture.tearDown();
    }

    public static Settings getSettings() {
        return fixture.getSettings();
    }
}
