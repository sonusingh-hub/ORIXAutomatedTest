package com.appiancorp.ps.cucumber.fixtures;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.fixture.BaseFixture;
import com.appiancorp.ps.cucumber.utils.TestDataManager;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.After;
import com.appiancorp.ps.automatedtest.common.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.util.Properties;

public class CucumberBaseFixture {
    private static BaseFixture fixture = new BaseFixture();

    @Given("^I setup login with username field \"([^\"]*)\" and password field \"([^\"]*)\" and login button \"([^\"]*)\"$")
    public void setupLoginWithUsernameFieldAndPasswordFieldAndLoginButton(String username, String password,
                                                                          String loginButton) {
        fixture.setupLoginWithUsernameFieldAndPasswordFieldAndLoginButton(username, password, loginButton);
    }

    @Given("^I setup with \"([^\"]*)\" browser$")
    public void setupWithBrowser(String browser) {
        fixture.setupWithBrowser(browser);
    }

    @Given("^I setup browser$")
    public void setupBrowser() {

        // Load properties from user.properties
        Properties props = PropertiesUtilities.getProps();

        // Read browser value directly from properties
        String browser = props.getProperty("browser", "").trim();

        if (browser.isEmpty()) {
            throw new RuntimeException("Browser is not set in user.properties (expected key: browser)");
        }

        // Call existing setup logic in BaseFixture
        fixture.setupWithBrowser(browser);
    }

    public void deleteFile(String filename) {
        fixture.deleteFile(filename);
    }

    @Given("^I set appian URL to \"([^\"]*)\"$")
    public void setAppianUrlTo(String url) {
        fixture.setAppianUrlTo(url);
    }

    @Given("^I setup appian URL to \"([^\"]*)\"$")
    public void setupAppianUrlTo(String keyOrUrl) {

        // Load properties only once
        java.util.Properties props = PropertiesUtilities.getProps();

        // Step 1: get first-level value
        String value = props.getProperty(keyOrUrl, keyOrUrl);

        // Step 2: replace ${env}
        String env = props.getProperty("env", "").trim();
        value = value.replace("${env}", env);

        // Step 3: resolve second-level key (PREPROD.appian.url → actual URL)
        String resolved = props.getProperty(value);
        if (resolved != null) {
            value = resolved;
        }

        // Set final URL
        fixture.setAppianUrlTo(value);
    }

    @Given("^I set appian version to \"([^\"]*)\"$")
    public void setAppianVersionTo(String version) {
        fixture.setAppianVersionTo(version);
    }

    @Given("^I setup appian version$")
    public void setupAppianVersion() {

        // Load user.properties
        Properties props = PropertiesUtilities.getProps();

        // Read version directly from property
        String version = props.getProperty("appian.version", "").trim();

        if (version.isEmpty()) {
            throw new RuntimeException("Appian version is not set in user.properties (expected key: appian.version)");
        }

        // Call original fixture method
        fixture.setAppianVersionTo(version);
    }


    @Given("^I set appian locale to \"([^\"]*)\"$")
    public void setAppianLocaleTo(String locale) {
        fixture.setAppianLocaleTo(locale);
    }

    @Given("^I setup appian locale$")
    public void setupAppianLocale() {

        // Load from user.properties
        Properties props = PropertiesUtilities.getProps();

        // Read the configured locale
        String locale = props.getProperty("appian.locale", "").trim();

        if (locale.isEmpty()) {
            throw new RuntimeException("Appian locale is not set in user.properties (expected key: appian.locale)");
        }

        // Apply locale
        fixture.setAppianLocaleTo(locale);
    }


    @Given("^I set start datetime$")
    public void setStartDatetime() {
        fixture.setStartDatetime();
    }

    @Given("^I set timeout seconds to \"([^\"]*)\"$")
    public void setTimeoutSecondsTo(String ts) {
        fixture.setTimeoutSecondsTo(Integer.parseInt(ts));
    }

//    @Given("^I set screenshot path to \"([^\"]*)\"$")
//    public void setScreenshotPathTo(String path) {
//        fixture.setScreenshotPathTo(path);
//    }

    @Given("^I set screenshot path to \"([^\"]*)\"$")
    public void setScreenshotPathTo(String keyOrPath) {

        Properties props = PropertiesUtilities.getProps();

        String value = props.getProperty(keyOrPath, keyOrPath);

        // Resolve ${env}
        String env = props.getProperty("env", "").trim();
        value = value.replace("${env}", env);

        fixture.setScreenshotPathTo(value);
    }



//    @Given("^I set stop on error to \"([^\"]*)\"$")
//    public void setStopOnErrorTo(String bool) {
//        fixture.setStopOnErrorTo(Boolean.parseBoolean(bool));
//    }

    @Given("^I set stop on error to \"([^\"]*)\"$")
    public void setStopOnErrorTo(String keyOrBool) {

        Properties props = PropertiesUtilities.getProps();

        String value = props.getProperty(keyOrBool, keyOrBool);

        fixture.setStopOnErrorTo(
                Boolean.parseBoolean(value)
        );
    }


//    @Given("^I set take error screenshots to \"([^\"]*)\"$")
//    public void setTakeErrorScreenshotsTo(String bool) {
//        fixture.setTakeErrorScreenshotsTo(Boolean.parseBoolean(bool));
//    }

    @Given("^I set take error screenshots to \"([^\"]*)\"$")
    public void setTakeErrorScreenshotsTo(String keyOrBool) {

        Properties props = PropertiesUtilities.getProps();

        String value = props.getProperty(keyOrBool, keyOrBool);

        fixture.setTakeErrorScreenshotsTo(
                Boolean.parseBoolean(value)
        );
    }


    @Given("^I open \"([^\"]*)\"$")
    public void open(String url) {
        fixture.open(url);
    }

    @Given("^I resize window width \"([^\"]*)\" height \"([^\"]*)\"$")
    public void resizeWindowWidthHeight(String width, String height) {
        fixture.resizeWindowWidthHeight(Integer.parseInt(width), Integer.parseInt(height));
    }

    @Given("^I click on \"([^\"]*)\" and \"([^\"]*)\" coordinates on the monitor$")
    public void clickOnXAndYCoordinatesOnMonitor(String x, String y) {
        fixture.clickOnXAndYCoordinatesOnMonitor(Integer.parseInt(x), Integer.parseInt(y));
    }

    @Given("^I take screenshot \"([^\"]*)\"$")
    public void takeScreenshot(String fileName) {
        fixture.takeScreenshot(fileName);
    }

//    public void loginIntoWithUsernameAndPassword(String url, String userName, String password) {
//        fixture.loginIntoWithUsernameAndPassword(url, userName, password);
//    }

    @Given("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginWithUsernameAndPassword(String userName, String password) {
        fixture.loginWithUsernameAndPassword(userName, password);
    }

    @Given("^I login to url \"([^\"]*)\" with username \"([^\"]*)\"$")
    public void loginIntoWithUsername(String url, String username) {
        fixture.loginIntoWithUsername(url, username);
    }

    @Given("^I login with username \"([^\"]*)\"$")
    public void loginWithUsername(String username) {
        fixture.loginWithUsername(username);
    }

    @Given("^I login to url \"([^\"]*)\" with role \"([^\"]*)\"$")
    public void loginIntoWithRole(String url, String role) {
        fixture.loginIntoWithRole(url, role);
    }

    @Given("^I login with role \"([^\"]*)\"$")
    public void loginWithRole(String role) {
        fixture.loginWithRole(role);
    }

    @Given("^I setup environment and login with role \"([^\"]*)\"$")
    public void setupEnvironmentAndLoginWithRole(String role) {
        fixture.loginWithRole(role);
    }

    @Given("^I load test data for \"([^\"]*)\" from \"([^\"]*)\"$")
    public void loadTestData(String scenarioId, String excelName) {
        TestDataManager.loadScenario(scenarioId, excelName);
    }

    @Given("^I login with terms with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginWithTermsWithUsernameAndPassword(String userName, String password) {
        fixture.loginWithTermsWithUsernameAndPassword(userName, password);
    }

    @Given("^I login with terms with username \"([^\"]*)\"$")
    public void loginWithTermsWithUsername(String userName) {
        fixture.loginWithTermsWithUsername(userName);
    }

    @Given("^I login with terms with role \"([^\"]*)\"$")
    public void loginWithTermsWithRole(String role) {
        fixture.loginWithTermsWithRole(role);
    }

    @Given("^I wait for \"([^\"]*)\"$")
    public void waitFor(String period) {
        fixture.waitFor(period);
    }

    @Given("^I wait for \"([^\"]*)\" seconds$")
    public void waitForSeconds(String period) {
        fixture.waitForSeconds(Integer.parseInt(period));
    }

    @Given("^I wait for \"([^\"]*)\" minutes$")
    public void waitForMinutes(String period) {
        fixture.waitForMinutes(Integer.parseInt(period));
    }

    public void waitForHours(String period) {
        fixture.waitForHours(Integer.parseInt(period));
    }

    @Given("^I wait for working$")
    public void waitForWorking() {
        fixture.waitForWorking();
    }

    @Given("^I wait for progress bar$")
    public void waitForProgressBar() {
        fixture.waitForProgressBar();
    }

    @Given("^I wait until \"([^\"]*)\"$")
    public void waitUntil(String datetime) {
        fixture.waitUntil(datetime);
    }

    @Given("^I get web api \"([^\"]*)\" with username \"([^\"]*)\"$")
    public String getWebApiWithUsername(String webApiEndpoint, String username) {
        return fixture.getWebApiWithUsername(webApiEndpoint, username);
    }

    @Given("^I get web api \"([^\"]*)\" with role \"([^\"]*)\"$")
    public String getWebApiWithRole(String webApiEndpoint, String role) {
        return fixture.getWebApiWithRole(webApiEndpoint, role);
    }

    @Given("^I post web api \"([^\"]*)\" with body \"([^\"]*)\" with username \"([^\"]*)\"$")
    public String postWebApiWithBodyWithUsername(String webApiEndpoint, String body, String username) {
        return fixture.postWebApiWithBodyWithUsername(webApiEndpoint, body, username);
    }

    @Given("^I post web api \"([^\"]*)\" with body \"([^\"]*)\" with role \"([^\"]*)\"$")
    public String postWebApiWithBodyWithRole(String webApiEndpoint, String body, String role) {
        return fixture.postWebApiWithBodyWithRole(webApiEndpoint, body, role);
    }

    @Given("^I set test variable \"([^\"]*)\" with \"([^\"]*)\"$")
    public void setTestVariableWith(String key, String val) {
        fixture.setTestVariableWith(key, val);
    }

    @Given("^I get test variable \"([^\"]*)\"$")
    public String getTestVariable(String variableName) {
        return fixture.getTestVariable(variableName);
    }

    @Given("^I refresh$")
    public void refresh() {
        fixture.refresh();
    }

    @Given("^I tear down$")
    public void tearDown() {
        fixture.tearDown();
    }

    @Given("^I get random string \"([^\"]*)\"$")
    public String getRandomString(String length) {
        return fixture.getRandomString(Integer.parseInt(length));
    }

    @Given("^I get random alphabet string \"([^\"]*)\"$")
    public String getRandomAlphabetString(String length) {
        return fixture.getRandomAlphabetString(Integer.parseInt(length));
    }

    @Given("^I get random integer from \"([^\"]*)\" to \"([^\"]*)\"$")
    public int getRandomIntegerFromTo(String min, String max) {
        return fixture.getRandomIntegerFromTo(Integer.parseInt(min), Integer.parseInt(max));
    }

    @Given("^I get random decimal from \"([^\"]*)\" to \"([^\"]*)\"$")
    public double getRandomDecimalFromTo(String min, String max) {
        return fixture.getRandomDecimalFromTo(Double.parseDouble(min), Double.parseDouble(max));
    }

    @Given("^I get random decimal from \"([^\"]*)\" to \"([^\"]*)\" with \"([^\"]*)\"$")
    public double getRandomDecimalFromToWith(String min, String max, String decimalPlaces) {
        return fixture.getRandomDecimalFromToWith(Double.parseDouble(min), Double.parseDouble(max),
                Integer.parseInt(decimalPlaces));
    }

    public static Settings getSettings() {
        return fixture.getSettings();
    }


    @After(order = 2)
    public void captureScreenshotOnFailure(Scenario scenario) {

        if (!scenario.isFailed()) return;

        if (!fixture.getSettings().isTakeErrorScreenshots()) return;

        if (fixture.getSettings().getDriver() == null) return;

        // Base path from properties
        String basePath =
                fixture.getSettings().getScreenshotPath();

        // Extract feature name
        String featureName =
                new File(scenario.getUri())
                        .getName()
                        .replace(".feature", "");

        // Clean name (remove spaces/special chars)
        featureName =
                featureName.replaceAll("[^a-zA-Z0-9]", "_");

        // Build feature folder path
        String featurePath =
                basePath + File.separator + featureName;

        // Create folder if not exists
        File folder = new File(featurePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Update runtime screenshot path
        fixture.getSettings()
                .setScreenshotPath(featurePath);

        // ===== ADD DATE + TIME HERE =====
        String timestamp =
                java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter
                                .ofPattern("yyyy-MM-dd_HH-mm-ss"));

        // File name = scenario name + timestamp
        String fileName =
                scenario.getName()
                        .replaceAll("[^a-zA-Z0-9]", "_")
                        + "_" + timestamp;

        // Save screenshot
        fixture.takeScreenshot(fileName);

        // Embed in report
        byte[] bytes =
                ((TakesScreenshot) fixture
                        .getSettings()
                        .getDriver())
                        .getScreenshotAs(OutputType.BYTES);

        scenario.attach(bytes, "image/png", fileName);
    }

    @After(order = 1)
    public void tearDownHook() {

        if (fixture.getSettings().getDriver() != null) {
            fixture.getSettings().getDriver().quit();
        }
    }


    private static boolean screenshotsCleaned = false;

    @Before(order = 0)
    public void cleanScreenshotFolderBeforeRun() {

        // Run only once
        if (screenshotsCleaned) return;

        String screenshotPath =
                fixture.getSettings().getScreenshotPath();

        if (screenshotPath == null || screenshotPath.isEmpty()) {
            screenshotPath = "target/screenshots"; // fallback
        }

        File folder = new File(screenshotPath);

        if (folder.exists()) {
            try {
                org.apache.commons.io.FileUtils.cleanDirectory(folder);
                System.out.println("Old screenshots deleted: " + screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            folder.mkdirs();
        }

        screenshotsCleaned = true;
    }

}
