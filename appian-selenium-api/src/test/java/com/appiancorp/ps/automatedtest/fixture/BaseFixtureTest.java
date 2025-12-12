package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.exception.TimeoutTestException;
import com.appiancorp.ps.automatedtest.test.AbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BaseFixtureTest extends AbstractTest {

    private static final Logger LOG = LogManager.getLogger(BaseFixtureTest.class);
    @SuppressWarnings("checkstyle:visibilityModifier")
    public static BaseFixture fixture;

    @BeforeAll
    public static void setUp() throws Exception {
        LOG.debug("Setting up Base Fixture");

        fixture = new BaseFixture();
        fixture.setAppianVersionTo(TEST_SITE_VERSION);
    }

    @BeforeEach
    public void beforeEach() {
        System.clearProperty("fitnesseForAppian.login.username");
        System.clearProperty("fitnesseForAppian.login.password");
        System.clearProperty("fitnesseForAppian.login.loginButton");
        fixture.setAppianLocaleTo("en_US");
    }

    @Test
    public void testSetAppianUrlTo() throws Exception {
        fixture.setAppianUrlTo(TEST_SITE_URL);
        // Test that the trailing forward slash '/' is removed
        assertEquals(fixture.getSettings().getUrl(), TEST_SITE_URL.substring(0, TEST_SITE_URL.length() - 1));
    }

    @Test
    public void testSetStartDatetime() throws Exception {
        fixture.setStartDatetime();
    }

    @Test
    public void testSetAppianLocale() throws Exception {
        assertEquals(fixture.getSettings().getDateFormat(), "MM/dd/yyyy");
        assertEquals(fixture.getSettings().getDateDisplayFormat(), "MMM d, yyyy");
        assertEquals(fixture.getSettings().getTimeFormat(), "h:mm aa");
        assertEquals(fixture.getSettings().getTimeDisplayFormat(), "h:mm aa");
        assertEquals(fixture.getSettings().getDatetimeFormat(), "MM/dd/yyyy h:mm aa");
        assertEquals(fixture.getSettings().getDatetimeDisplayFormat(), "MMM d, yyyy h:mm aa");
        fixture.setAppianLocaleTo("en_GB");
        assertEquals(fixture.getSettings().getDateFormat(), "dd/MM/yyyy");
        assertEquals(fixture.getSettings().getDateDisplayFormat(), "d MMM yyyy");
        assertEquals(fixture.getSettings().getTimeFormat(), "HH:mm");
        assertEquals(fixture.getSettings().getTimeDisplayFormat(), "HH:mm");
        assertEquals(fixture.getSettings().getDatetimeFormat(), "dd/MM/yyyy HH:mm");
        assertEquals(fixture.getSettings().getDatetimeDisplayFormat(), "d MMM yyyy HH:mm");
    }

    @Test
    public void testCreateAppianLocale() throws Exception {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("customLocale.json").getFile();
        fixture.createAppianLocale(filePath);

        assertEquals(fixture.getSettings().getDateFormat(), "dd");
        assertEquals(fixture.getSettings().getDateDisplayFormat(), "d");
        assertEquals(fixture.getSettings().getTimeFormat(), "mm");
        assertEquals(fixture.getSettings().getTimeDisplayFormat(), "mm");
        assertEquals(fixture.getSettings().getDatetimeFormat(), "dd");
        assertEquals(fixture.getSettings().getDatetimeDisplayFormat(), "d");
        assertEquals(fixture.settings.getLocale(), "aj_SILLY");
    }

    @Test
    public void testSetTimeoutSecondsTo() throws Exception {
        fixture.setTimeoutSecondsTo(10);
        assertEquals(fixture.getSettings().getTimeoutSeconds(), 10);
    }

    @Test
    public void testSetScreenshotPathTo() throws Exception {
        fixture.setScreenshotPathTo(
                PropertiesUtilities.getProps().getProperty(Constants.AUTOMATED_TESTING_HOME) + "\\screenshots\\");
    }

    @Test
    public void testOpen() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.open("http://google.com");
        fixture.tearDown();
    }

    @Test
    public void testLoginIntoWithUsername() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.loginIntoWithUsername(TEST_SITE_URL, TEST_USERNAME);
        fixture.tearDown();
    }

    @Test
    public void testLoginIntoWithRole() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.loginIntoWithRole(TEST_SITE_URL, TEST_ROLE);
        fixture.tearDown();
    }

    @Test
    public void testLoginIntoWithUsernameAndPassword() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.loginIntoWithUsernameAndPassword(TEST_SITE_URL, TEST_USERNAME, TEST_PASSWORD);
        fixture.tearDown();
    }

    @Test
    public void testLoginWithUsernameAndPassword() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithUsernameAndPassword(TEST_USERNAME, TEST_PASSWORD);
        fixture.tearDown();
    }

    @Test
    public void testLoginWithUsername() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithUsername(TEST_USERNAME);
        fixture.tearDown();
    }

    @Test
    public void testLoginTwice() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithUsername(TEST_USERNAME);
        TimeUnit.SECONDS.sleep(1);
        fixture.loginWithUsername(TEST_USERNAME);
        fixture.tearDown();
    }

    @Test
    public void testLoginWithRole() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithRole(TEST_ROLE);
        fixture.tearDown();
    }

    @Test
    public void testSiteLoginWithUsername() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithUsername(TEST_SITE_USERNAME);
        fixture.tearDown();
    }

    @Test
    public void testFailedLoginAttempt() throws Exception {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo("http://www.appian.com");

        try {
            fixture.loginWithUsername(TEST_USERNAME);
        } catch (TimeoutTestException tte) {
            // Expected exception
            return;
        } catch (WebDriverException wde) {
            // This exception is for Jenkins, which throws a non 200 HTTP code for http://www.appian.com/logout/
            return;
        } finally {
            fixture.tearDown();
        }

        fail("Expected login attempt to fail.");
    }

    @Test
    // Skipping test for wait for hours because too much overhead on test run time
    public void testWaitForSecondsMinutesHours() {
        int waitForSeconds = 10;
        int waitForMinutes = 1;
        long startTime = 0;
        long endTime = 0;
        long desiredWaitTime = 0;
        long recordedWaitTime = 0;
        long differenceInExpectedAndExecutedTime = 0;
        // Arbitrary threshold of 1 second tolerance between recorded wait time and desired wait time
        final long toleranceThreshold = 1000; // in milliseconds

        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithUsername(TEST_USERNAME);

        startTime = System.currentTimeMillis();
        fixture.waitForSeconds(waitForSeconds);
        endTime = System.currentTimeMillis();

        desiredWaitTime = waitForSeconds * 1000;
        recordedWaitTime = endTime - startTime;
        differenceInExpectedAndExecutedTime = recordedWaitTime - desiredWaitTime;

        assertTrue(differenceInExpectedAndExecutedTime < toleranceThreshold);

        startTime = System.currentTimeMillis();
        fixture.waitForMinutes(waitForMinutes);
        endTime = System.currentTimeMillis();

        desiredWaitTime = waitForMinutes * 60 * 1000;
        recordedWaitTime = endTime - startTime;
        differenceInExpectedAndExecutedTime = recordedWaitTime - desiredWaitTime;

        assertTrue(differenceInExpectedAndExecutedTime < toleranceThreshold);
        fixture.tearDown();
    }

    @Test
    public void testWaitForWorking() {
        Assumptions.assumeFalse(isBrowser(Constants.RemoteDriver.REMOTE_EDGE.name()));
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.loginWithUsername(TEST_USERNAME);
        fixture.waitForWorking();
        fixture.tearDown();
    }

    @Test
    public void testWaitForProgressBar() {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL + "sites/automated-test-site");
        fixture.loginWithUsername(TEST_USERNAME);
        fixture.waitForProgressBar();
        fixture.tearDown();
    }

    @Test
    public void testGetRandomString() {
        assertEquals(fixture.getRandomString(7).length(), 7);
    }

    @Test
    public void testGetRandomAlphabetString() {
        String alphabeticString = fixture.getRandomAlphabetString(9);
        assertEquals(alphabeticString.length(), 9);
        assertTrue(StringUtils.isAlpha(alphabeticString));
    }

    @Test
    public void testGetRandomIntFromTo() {
        int randomInt = fixture.getRandomIntegerFromTo(0, 10);
        assertTrue((randomInt < 10) && (randomInt >= 0));
        try {
            fixture.getRandomIntegerFromTo(10, 0);
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }
    }

    @Test
    public void testGetRandomDecimalFromTo() {
        double randomDec = fixture.getRandomDecimalFromTo(0, 10);
        assertTrue((randomDec < 10) && (randomDec >= 0));
        try {
            fixture.getRandomDecimalFromTo(10, 0);
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }
    }

    @Test
    public void testSetupWithBrowserAtLocation() {
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.open("http://www.google.com");
        fixture.tearDown();
    }

    @Test
    public void testGetRandomDecimalFromToWith() {
        double randomDec = fixture.getRandomDecimalFromToWith(0.1, 10.01, 6);
        assertTrue((randomDec < 10.01) && (randomDec >= 0.1));
        try {
            fixture.getRandomDecimalFromToWith(10, 0, 6);
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }
    }

    @Test
    public void testCallWebApi() {
        fixture.setAppianUrlTo(TEST_SITE_URL);

        assertEquals("test-web-api", fixture.getWebApiWithUsername("test-web-api", TEST_USERNAME));
        assertEquals("test-web-api", fixture.getWebApiWithRole("test-web-api", TEST_ROLE));

        assertEquals("asdf1", fixture.postWebApiWithBodyWithUsername("test-post-web-api", "asdf", TEST_USERNAME));
        assertEquals("asdf1", fixture.postWebApiWithBodyWithRole("test-post-web-api", "asdf", TEST_ROLE));
    }

    @Test
    public void testVariables() {
        fixture.setAppianUrlTo(TEST_SITE_URL);

        fixture.setTestVariableWith("testVars", fixture.getWebApiWithRole("generate-data", TEST_ROLE));
        System.out.println(fixture.getTestVariable("tv!testVars.com"));
    }

    @Test
    public void testWindowResizing() throws Exception {
        //Linux Chrome Driver is in headless mode, there is no active browser window to resize
        Assumptions.assumeFalse(Settings.isLinux() && isBrowser(Constants.RemoteDriver.REMOTE_CHROME.name()));

        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.resizeWindowWidthHeight(600, 700);
        Dimension size = fixture.getSettings().getDriver().manage().window().getSize();
        assertEquals(600, size.width);
        assertEquals(700, size.height);
        fixture.tearDown();
    }


    @AfterAll
    public static void tearDown() throws Exception {

    }
}
