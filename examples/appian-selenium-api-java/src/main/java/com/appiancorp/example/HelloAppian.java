package com.appiancorp.example;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.appiancorp.ps.automatedtest.fixture.SitesFixture;

public class HelloAppian {
    protected static String TEST_BROWSER = "CHROME";
    protected static String TEST_SITE_VERSION = "DEFAULT_APPIAN_VERSION_REPLACEMENT_KEY";
    protected static String TEST_SITE_URL = "https://yourAppianSite.com";
    protected static String TEST_SITE_LOCALE = "en_US";
    protected static String TEST_USERNAME = "admin.user"; //Make sure this username has an entry in users.properties
    protected static Integer TEST_TIMEOUT = 60;

    private static final Logger LOG = LogManager.getLogger(HelloAppian.class);
    public static SitesFixture fixture;

    public static void main(String[] args) {
        LOG.debug("Setting up Tempo Fixture");

        fixture = new SitesFixture();

        fixture.setTakeErrorScreenshotsTo(true);
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.setTimeoutSecondsTo(TEST_TIMEOUT);
        fixture.setAppianVersionTo(TEST_SITE_VERSION);
        fixture.setAppianLocaleTo(TEST_SITE_LOCALE);
        fixture.loginWithUsername(TEST_USERNAME);
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("Create a Case");
        fixture.verifyButtonIsPresent("Submit");
        fixture.tearDown();
    }
}
