package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.AppianWebApi;
import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import com.appiancorp.ps.automatedtest.common.RemoteWebDriverBuilder;
import com.appiancorp.ps.automatedtest.common.Screenshot;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.tempo.TempoLogin;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * This is the base class for integrating Appian and FitNesse.
 * This class contains fixture commands which are generic to Appian tests.
 */
public class BaseFixture {

    private static final Logger LOG = LogManager.getLogger(BaseFixture.class);
    private Properties props = new Properties();
    protected Settings settings;
    private static boolean urlSent = false;

    public BaseFixture() {
        super();
        settings = Settings.initialize();
        PropertiesUtilities.loadProperties();
        props = PropertiesUtilities.getProps();
    }

    /**
     * Setups login configuration<br>
     * <br>
     * FitNesse Example:
     * <code>|setup login with username field | USERNAME | and password field | PASSWORD | and login button | LOGIN_BUTTON |</code>
     *
     * @param username    ID or NAME of the username field in the login page
     * @param password    ID or NAME of the password field in the login page
     * @param loginButton NAME for the login/submit button in the login page
     *                    (Optional)
     */
    public void setupLoginWithUsernameFieldAndPasswordFieldAndLoginButton(String username, String password,
            String loginButton) {
        System.setProperty("fitnesseForAppian.login.username", username);
        System.setProperty("fitnesseForAppian.login.password", password);
        System.setProperty("fitnesseForAppian.login.loginButton", loginButton);
    }

    /**
     * Provides a hook for external QA frameworks
     *
     * @param webDriver - The web driver defined in the external framework.
     */
    public void setWebDriver(WebDriver webDriver) {
        settings.setDriver(webDriver);
    }

    /**
     * Starts selenium browser<br>
     * <br>
     * FitNesse Example:
     * <code>| setup selenium web driver with | BROWSER | browser |</code>
     *
     * @param browser Browser to test with, currently supports FIREFOX, CHROME, IE,
     *                EDGE
     */
    @Deprecated
    public void setupSeleniumWebDriverWithBrowser(String browser) {
        setupWithBrowser(browser);
    }

    /**
     * Starts selenium browser<br>
     * <br>
     * FitNesse Example: <code>| setup with | BROWSER | browser |</code>
     *
     * @param browser Browser to test with, currently supports FIREFOX, CHROME, IE,
     *                EDGE
     */
    public void setupWithBrowser(String browser) {
        LOG.debug("browser=" + browser);
        if (browser.equals(Constants.Driver.FIREFOX.name())) {
            setupFirefox();
        } else if (browser.equals(Constants.Driver.CHROME.name())) {
            setupChrome();
        } else if (browser.equals(Constants.Driver.EDGE.name())) {
            setupEdge();
        } else if (browser.equals(Constants.RemoteDriver.REMOTE_FIREFOX.name())) {
            WebDriver driver = new RemoteWebDriverBuilder()
                    .browser(Constants.RemoteDriver.REMOTE_FIREFOX)
                    .create(props);
            settings.setDriver(driver);
        } else if (browser.equals(Constants.RemoteDriver.REMOTE_CHROME.name())) {
            WebDriver driver = new RemoteWebDriverBuilder()
                    .browser(Constants.RemoteDriver.REMOTE_CHROME)
                    .create(props);
            settings.setDriver(driver);
        } else if (browser.equals(Constants.RemoteDriver.REMOTE_EDGE.name())) {
            WebDriver driver = new RemoteWebDriverBuilder()
                    .browser(Constants.RemoteDriver.REMOTE_EDGE)
                    .create(props);
            settings.setDriver(driver);
        }

        try {
            settings.getDriver().manage().window().maximize();
        } catch (Exception e) {
            // Firefox v54 and under throw an exception but do properly maximize
            // This is fixed in Firefox v55 but hasn't been released as of this change
        }
    }

    private void setupChrome() {
        String driverHome = determineDriverHome(Constants.Driver.CHROME);

        if (driverHome != null) {
            System.setProperty("webdriver.chrome.driver", driverHome);
        }
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.silentOutput", "true");

        ChromeOptions co = new ChromeOptions();
        // Due to https://github.com/SeleniumHQ/selenium/issues/11750
        co.addArguments("--remote-allow-origins=*");
        co.addArguments("--disable-extensions");
        // Chromedriver v2.28 onwards, when "--disable-extensions" flag is passed,
        // it implicitly passes "disable-extensions-except" flag which loads Chrome
        // automation extension,
        // therefore we need to explicitly set "useAutomationExtension" option to be
        // false
        co.setExperimentalOption("useAutomationExtension", false);

        if (!StringUtils.isBlank(props.getProperty(Constants.DOWNLOAD_DIRECTORY))) {
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory",
                    FilenameUtils.separatorsToSystem(props.getProperty(Constants.DOWNLOAD_DIRECTORY)));
            chromePrefs.put("safebrowsing.enabled", "true");
            co.setExperimentalOption("prefs", chromePrefs);
        }

        if (StringUtils.isNotBlank(props.getProperty(Constants.Driver.CHROME.getBrowserHome()))) {
            co.setBinary(
                    FilenameUtils.separatorsToSystem(props.getProperty(Constants.Driver.CHROME.getBrowserHome())));
            co.addArguments("--disable-extensions");
            co.addArguments("--no-sandbox");
            settings.setDriver(new ChromeDriver(co));
        } else {
            settings.setDriver(new ChromeDriver(co));
        }
    }

    private void setupEdge() {
        String driverHome = determineDriverHome(Constants.Driver.EDGE);

        if (driverHome != null) {
            System.setProperty("webdriver.edge.driver", driverHome);
        }
        System.setProperty("webdriver.edge.args", "--disable-logging");
        System.setProperty("webdriver.edge.silentOutput", "true");

        EdgeOptions eo = new EdgeOptions();
        eo.addArguments("--remote-allow-origins=*");
        eo.addArguments("--disable-extensions");
        eo.setExperimentalOption("useAutomationExtension", false);

        if (!StringUtils.isBlank(props.getProperty(Constants.DOWNLOAD_DIRECTORY))) {
            HashMap<String, Object> edgePrefs = new HashMap<String, Object>();
            edgePrefs.put("profile.default_content_settings.popups", 0);
            edgePrefs.put("download.default_directory",
                    FilenameUtils.separatorsToSystem(props.getProperty(Constants.DOWNLOAD_DIRECTORY)));
            edgePrefs.put("safebrowsing.enabled", "true");
            eo.setExperimentalOption("prefs", edgePrefs);
        }

        if (StringUtils.isNotBlank(props.getProperty(Constants.Driver.EDGE.getBrowserHome()))) {
            eo.setBinary(
                    FilenameUtils.separatorsToSystem(props.getProperty(Constants.Driver.EDGE.getBrowserHome())));
            eo.addArguments("--disable-extensions");
            eo.addArguments("--no-sandbox");
            eo.addArguments("--guest");

            settings.setDriver(new EdgeDriver(eo));
        } else {
            settings.setDriver(new EdgeDriver(eo));
        }
    }

    private void setupFirefox() {
        String driverHome = determineDriverHome(Constants.Driver.FIREFOX);
        if (driverHome != null) {
            System.setProperty("webdriver.gecko.driver", driverHome);
        }
        FirefoxProfile prof = new FirefoxProfile();
        prof.setPreference("dom.file.createInChild", true);
        prof.setPreference("browser.startup.homepage_override.mstone", "ignore");
        prof.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        if (!StringUtils.isBlank(props.getProperty(Constants.DOWNLOAD_DIRECTORY))) {
            prof.setPreference("browser.download.folderList", 2);
            prof.setPreference("browser.download.manager.showWhenStarting", false);
            prof.setPreference("browser.download.dir",
                    FilenameUtils.separatorsToSystem(props.getProperty(Constants.DOWNLOAD_DIRECTORY)));
            prof.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    props.getProperty(Constants.DOWNLOAD_MIME_TYPES));
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(prof);
        if (!StringUtils.isBlank(props.getProperty(Constants.Driver.FIREFOX.getBrowserHome()))) {
            File pathToBinary = new File(
                    FilenameUtils.separatorsToSystem(props.getProperty(Constants.Driver.FIREFOX.getBrowserHome())));
            FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
            firefoxOptions.setBinary(ffBinary);
            settings.setDriver(new FirefoxDriver(firefoxOptions));
        } else {
            settings.setDriver(new FirefoxDriver(firefoxOptions));
        }
    }

    private String determineDriverHome(Constants.Driver driver) {
        if (StringUtils.isNotBlank(props.getProperty(driver.getDriverHome()))) {
            return FilenameUtils.separatorsToSystem(props.getProperty(driver.getDriverHome()));
        } else {
            return null;
        }
    }

    /**
     * Deletes a file from the downloads directory
     * <br>
     * FiNesse Example: <code>| delete file | FILE_NAME |</code>
     *
     * @param filename Filename to delete out of the download directory
     */
    public void deleteFile(String filename) {
        String directory = FilenameUtils.separatorsToSystem(props.getProperty(Constants.DOWNLOAD_DIRECTORY));
        AppianObject appianObject = AppianObject.getInstance(settings);
        String parsedFilename = appianObject.parseVariable(filename);

        Path filePath = Paths.get(directory + "/" + parsedFilename);

        // Ensure no fishy business with relative path names
        try {
            if (filePath.normalize().toFile().getAbsolutePath().startsWith(directory)) {
                filePath.toFile().delete();
            } else {
                LOG.debug("File path not within download directory");
            }
        } catch (Exception e) {
            LOG.error("Error while deleting file: " + filename, e);
        }
    }

    /**
     * Sets the default appian url.<br>
     * <br>
     * FitNesse Example: <code>| set appian url to | APPIAN_URL |</code>
     *
     * @param url Url for Appian site, e.g. https://forum.appian.com/suite
     */
    public void setAppianUrlTo(String url) {
        settings.setUrl(url);
    }

    /**
     * Sets the default appian version.<br>
     * <br>
     * FitNesse Example: <code>| set appian version to | APPIAN_VERSION |</code>
     *
     * @param version Version for Appian site, e.g. 16.1
     */
    public void setAppianVersionTo(String version) {
        // Version is the only non-thread safe settings variable
        Settings.setVersion(version);
    }

    /**
     * Sets the Appian locale. This is useful so that test cases will work in
     * different geographic regions that format date and time
     * differently.<br>
     * <br>
     * FitNesse Example: <code>| set appian locale to | en_US or en_GB |</code>
     *
     * @param locale Appian locale (en_US or en_GB)
     */
    public void setAppianLocaleTo(String locale) {
        settings.setLocale(locale);
    }

    /**
     * Reads a file which defines a new locale and sets the current locale to
     * that.<br>
     * <br>
     * FitNesse Example:
     * <code>| create appian locale | Absolute Path to locale file |</code>
     *
     * @param localePath Absolute path to the JSON encoded locale file.
     *                   Format of file:
     *                   {
     *                   "dateFormats": [
     *                   { "label": "dateFormat", "value": "FORMAT_STRING" },
     *                   { "label": "dateDisplayFormat", "value": "FORMAT_STRING" },
     *                   { "label": "timeFormat", "value": "FORMAT_STRING" },
     *                   { "label": "timeDisplayFormat", "value": "FORMAT_STRING" },
     *                   { "label": "datetimeFormat", "value": "FORMAT_STRING" },
     *                   { "label": "datetimeDisplayFormat","value": "FORMAT_STRING"
     *                   }
     *                   ],
     *                   "labels": [
     *                   { "label": "", "value": "" }
     *                   ],
     *                   "locale": "LOCALE_CODE"
     *                   }
     */
    public void createAppianLocale(String localePath) {
        settings.createLocale(localePath);
    }

    /**
     * Sets the start datetime with which all of the relative dates and datetimes
     * will be calculated.<br>
     * <br>
     * FitNesse Example: <code>| set start datetime |</code>
     */
    public void setStartDatetime() {
        settings.setStartDatetime(new Date());
    }

    /**
     * Sets the datasource name<br>
     *
     * @param dataSourceName Name of the data source
     */
    @Deprecated
    public void setDataSourceNameTo(String dataSourceName) {
        settings.setDataSourceName(dataSourceName);
    }

    /**
     * Sets the global timeout seconds that are used for each implicit wait. <br>
     * FitNesse Example: <code>| set timeout seconds to | 10 |</code>
     *
     * @param ts Timeout seconds
     */
    public void setTimeoutSecondsTo(Integer ts) {
        settings.setTimeoutSeconds(ts);
    }

    /**
     * Sets the path on the automated test server where screenshots will be placed.
     * <br>
     * FitNesse Example:
     * <code>| set screenshot path to | AUTOMATED_TESTING_HOME/screenshots/ |</code>
     *
     * @param path Path to save screen shots
     */
    public void setScreenshotPathTo(String path) {
        settings.setScreenshotPath(path);
    }

    /**
     * Set the flag to stop FitNesse on error. If true, FitNesse will quit on the
     * first failed test. This will also quit the WebDriver as
     * well.<br>
     * <br>
     * FitNesse Example: <code>| set stop on error to | BOOLEAN |</code>
     *
     * @param bool true or false
     */
    public void setStopOnErrorTo(Boolean bool) {
        settings.setStopOnError(bool);
    }

    /**
     * Set the flag to take screenshots on errors. If true, every error in an
     * automated test will trigger a screenshot to be placed in
     * {@link #setScreenshotPathTo(String)}.<br>
     * <br>
     * FitNesse Example: <code>| set take error screenshots to | true |</code>
     *
     * @param bool true or false
     */
    public void setTakeErrorScreenshotsTo(Boolean bool) {
        if (settings.getScreenshotPath() == null) {
            settings.setScreenshotPath(props.getProperty(Constants.AUTOMATED_TESTING_HOME) + "/screenshots");
        }
        settings.setTakeErrorScreenshots(bool);
    }

    /**
     * Navigate to a particular url.<br>
     * <br>
     * FitNesse Example: <code>| open | https://forum.appian.com/suite |</code>
     *
     * @param url Url to navigate to
     */
    public void open(String url) {
        settings.getDriver().get(url);
    }

    /**
     * Resize browser window.<br>
     * <br>
     * FitNesse Example:
     * <code>| resize window width | WIDTH | height | HEIGHT |</code>
     *
     * @param width  Width for window
     * @param height Height for window
     */
    public void resizeWindowWidthHeight(Integer width, Integer height) {
        settings.getDriver().manage().window().setSize(new Dimension(width, height));
    }

    /**
     * Click on the x and y coordinate on monitor. <br>
     * <br>
     * FitNesse
     * Examples:<code>| click on x | X | and y | Y | coordinates on monitor |</code>
     *
     * @param x X coordinate on the primary monitor
     * @param y Y coordinate on the primary monitor
     */
    public void clickOnXAndYCoordinatesOnMonitor(Integer x, Integer y) {
        if ((x != null && x < 0) || (y != null && y < 0)) {
            throw new IllegalArgumentException("X and Y coordinates can not be null or negative");
        }
        try {
            Robot robot = new Robot();
            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1000);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Take a screenshot and place it in the directory defined by:
     * {@link #setScreenshotPathTo(String)}.<br>
     * <br>
     * FitNesse Example: <code>| take screenshot |</code>
     *
     * @param fileName File name for new screenshot
     */
    public void takeScreenshot(String fileName) {
        Screenshot.getInstance(settings).capture(fileName);
    }

    /**
     * Login to Appian.<br>
     * <br>
     * FitNesse Example:
     * <code>| login into | APPIAN_URL | with username | USERNAME | and password | PASSWORD |</code>
     *
     * @param url      Url of Appian site
     * @param userName Username Appian username
     * @param password Password Appian password
     */
    public void loginIntoWithUsernameAndPassword(String url, String userName, String password) {
        TempoLogin.getInstance(settings).navigateToLoginPage(url);
        TempoLogin.getInstance(settings).waitForLogin();
        TempoLogin.getInstance(settings).login(url, userName, password, false);
    }

    /**
     * Login to Appian.<br>
     * <br>
     * FitNesse Example:
     * <code>| login with username | USERNAME | and password | PASSWORD |</code> -
     * Uses the url set here:
     * {@link #setAppianUrlTo(String)}
     *
     * @param userName Username Appian username
     * @param password Password Appian password
     */
    public void loginWithUsernameAndPassword(String userName, String password) {
        loginIntoWithUsernameAndPassword(settings.getUrl(), userName, password);
    }

    /**
     * Login to Appian using users.properties.<br>
     * <br>
     *
     * @param url      Appian url
     * @param username Appian username, must match username in users.properties
     */
    public void loginIntoWithUsername(String url, String username) {
        String password = props.getProperty(username);
        loginIntoWithUsernameAndPassword(url, username, password);
    }

    /**
     * Login to Appian using users.properties.<br>
     * <br>
     *
     * @param username Appian username, must match username in users.properties
     */
    public void loginWithUsername(String username) {
        loginIntoWithUsername(settings.getUrl(), username);
    }

    /**
     * Login to Appian using roles.properties.<br>
     * <br>
     *
     * @param url  Appian url
     * @param role Role matching role in users.properties
     */
//    public void loginIntoWithRole(String url, String role) {
//        String usernamePassword = props.getProperty(role);
//        String username = StringUtils.substringBefore(usernamePassword, "|");
//        String password = StringUtils.substringAfter(usernamePassword, "|");
//        loginIntoWithUsernameAndPassword(url, username, password);
//    }

    public void loginIntoWithRole(String url, String role) {

        // ⭐ get active environment
        String env = props.getProperty("env").trim();

        // ⭐ build environment-specific role key
        String envKey = env + "." + role;

        // ⭐ fallback: use env-specific key if available
        String usernamePassword = props.containsKey(envKey)
                ? props.getProperty(envKey)
                : props.getProperty(role);

        String username = StringUtils.substringBefore(usernamePassword, "|");
        String password = StringUtils.substringAfter(usernamePassword, "|");
        loginIntoWithUsernameAndPassword(url, username, password);
    }

    /**
     * Login to Appian using roles.properties.<br>
     * <br>
     *
     * @param role Role matching role in users.properties
     */
    public void loginWithRole(String role) {
        loginIntoWithRole(settings.getUrl(), role);
    }

    /**
     * Login to and Appian site containing terms and conditions.<br>
     * <br>
     * FitNesse Example:
     * <code>| login with terms with username | USERNAME | and password | PASSWORD |</code>
     * - Uses the url set here:
     * {@link #setAppianUrlTo(String)}
     *
     * @param userName Appian username
     * @param password Appian password
     */
    public void loginWithTermsWithUsernameAndPassword(String userName, String password) {
        TempoLogin.getInstance(settings).navigateToLoginPage(settings.getUrl());
        TempoLogin.getInstance(settings).waitForTerms();
        TempoLogin.getInstance(settings).loginWithTerms(settings.getUrl(), userName, password);
    }

    /**
     * Login to and Appian site containing terms and conditions.<br>
     * <br>
     * FitNesse Example: <code>| login with terms with username | USERNAME | </code>
     * - Uses the url set here: {@link #setAppianUrlTo(String)}
     *
     * @param userName Appian username
     */
    public void loginWithTermsWithUsername(String userName) {
        TempoLogin.getInstance(settings).navigateToLoginPage(settings.getUrl());
        TempoLogin.getInstance(settings).waitForTerms();
        String password = props.getProperty(userName);
        TempoLogin.getInstance(settings).loginWithTerms(settings.getUrl(), userName, password);
    }

    /**
     * Login to and Appian site containing terms and conditions.<br>
     * <br>
     * FitNesse Example: <code>| login with terms with role | USER_ROLE| </code> -
     * Uses the url set here: {@link #setAppianUrlTo(String)}
     *
     * @param role Role matching role in users.properties
     */
    public void loginWithTermsWithRole(String role) {
        TempoLogin.getInstance(settings).navigateToLoginPage(settings.getUrl());
        TempoLogin.getInstance(settings).waitForTerms();
        String usernamePassword = props.getProperty(role);
        String username = StringUtils.substringBefore(usernamePassword, "|");
        String password = StringUtils.substringAfter(usernamePassword, "|");

        TempoLogin.getInstance(settings).loginWithTerms(settings.getUrl(), username, password);
    }

    /**
     * Waits for a particular period of time.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| wait for | 20 seconds |</code><br>
     * <code>| wait for | 5 minutes |</code><br>
     * <code>| wait for | 1 hour |</code><br>
     *
     * @param period Period of time, e.g. 5 minutes, 1 hour, 10 seconds
     */
    public void waitFor(String period) {
        int periodNum = Integer.parseInt(period.replaceAll("[^0-9]", ""));
        int noOfSeconds = 0;
        if (period.contains("hour")) {
            noOfSeconds = periodNum * 60 * 60;
        } else if (period.contains("minute")) {
            noOfSeconds = periodNum * 60;
        } else {
            noOfSeconds = periodNum;
        }

        try {
            int noOfMilliseconds = noOfSeconds * 1000;
            Thread.sleep(noOfMilliseconds);
        } catch (InterruptedException e) {
            throw ExceptionBuilder.build(e, settings, "Wait Until");
        }
    }

    /**
     * Waits for X seconds.<br>
     * <br>
     * FitNesse Example: <code>| wait for | 5 | seconds|</code>
     *
     * @param period Number of seconds to wait for
     */
    public void waitForSeconds(Integer period) {
        waitFor(period + " seconds");
    }

    /**
     * Waits for X minutes.<br>
     * <br>
     * FitNesse Example: <code>| wait for | 10 | minutes |</code>
     *
     * @param period Number of minutes to wait for
     */
    public void waitForMinutes(Integer period) {
        waitFor(period + " minutes");
    }

    /**
     * Wait for X hours.<br>
     * <br>
     * FitNesse Example: <code>| wait for | 1 | hours |</code>
     *
     * @param period Number of hours to wait for
     */
    public void waitForHours(Integer period) {
        waitFor(period + " hours");
    }

    /**
     * Waits for 'Working...' message to disappear<br>
     * <br>
     * FitNesse Example: <code>| wait for working |</code>
     */
    public void waitForWorking() {
        AppianObject.getInstance(settings).waitForWorking();
    }

    /**
     * Waits for Progerss Bar to disappear<br>
     * <br>
     * FitNesse Example: <code>| wait for progress bar |</code>
     */
    public void waitForProgressBar() {
        AppianObject.getInstance(settings).waitForProgressBar();
    }

    /**
     * Waits until a particular datetime<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| wait until | 2016-01-11 12:31 |</code> - Test will halt until that
     * particular time
     *
     * @param datetime Datetime string must match yyyy-mm-dd HH:mm
     */
    public void waitUntil(String datetime) {
        datetime = AppianObject.getInstance(settings).formatDatetimeCalculation(datetime);

        try {
            Date endDatetime = DateUtils.parseDate(datetime, settings.getDatetimeDisplayFormat());
            Date nowDatetime = new Date();

            while (endDatetime.after(nowDatetime)) {
                Thread.sleep(1000);
                nowDatetime = new Date();
            }
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Wait Until");
        }
    }

    /**
     * Calls a web api and returns result<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get web api | WEB_API_ENDPOINT | with username | USERNAME |</code>
     *
     * @param webApiEndpoint Web api endpoint, not including url
     * @param username       Appian username
     * @return WebAPI response
     */
    public String getWebApiWithUsername(String webApiEndpoint, String username) {
        String password = props.getProperty(username);
        return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, "", username, password);
    }

    /**
     * Calls a web api and returns result<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get web api | WEB_API_ENDPOINT | with role | ROLE |</code>
     *
     * @param webApiEndpoint Web api endpoint, not including url
     * @param role           Role matching role in users.properties
     * @return WebAPI response
     */
    public String getWebApiWithRole(String webApiEndpoint, String role) {
        String usernamePassword = props.getProperty(role);
        String username = StringUtils.substringBefore(usernamePassword, "|");
        String password = StringUtils.substringAfter(usernamePassword, "|");

        return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, "", username, password);
    }

    /**
     * Calls a web api and returns result<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| post web api | WEB_API_ENDPOINT | with body | BODY | with username | USERNAME |</code>
     *
     * @param webApiEndpoint Web api endpoint, not including url
     * @param body           Body to send in POST
     * @param username       Appian username
     * @return WebAPI response
     */
    public String postWebApiWithBodyWithUsername(String webApiEndpoint, String body, String username) {
        String password = props.getProperty(username);
        return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, body, username, password);
    }

    /**
     * Calls a web api and returns result<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| post web api | WEB_API_ENDPOINT | with body | BODY | with role | ROLE |</code>
     *
     * @param webApiEndpoint Web api endpoint, not including url
     * @param body           Body to send in POST
     * @param role           Role matching role in users.properties
     * @return WebAPI response
     */
    public String postWebApiWithBodyWithRole(String webApiEndpoint, String body, String role) {
        String usernamePassword = props.getProperty(role);
        String username = StringUtils.substringBefore(usernamePassword, "|");
        String password = StringUtils.substringAfter(usernamePassword, "|");

        return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, body, username, password);
    }

    /**
     * Sets test variables<br >
     * <br>
     * | set test variable | TEST_VAR_KEY | with | TEST_VAR_AS_JSON |
     *
     * @param key Test variable key
     * @param val JSON string containing variable data
     */
    public void setTestVariableWith(String key, String val) {
        settings.setTestVariableWith(key, val);
    }

    /**
     * Get test variable<br>
     * <br>
     * | get test variable | tv!VARIABLE_NAME |
     *
     * @param variableName Name for saved variable\
     * @return Variable data
     */
    public String getTestVariable(String variableName) {
        return settings.getTestVariable(variableName.replace("tv!", ""));
    }

    /**
     * Refreshes page<br>
     * <br>
     * FitNesse Example: <code>| refresh |</code>
     */
    public void refresh() {
        settings.getDriver().navigate().refresh();
    }

    @Deprecated
    public boolean startProcessWithProcessModelUuId(String processModelUuid) {
        try {
            settings.getDriver().get(
                    settings.getUrl() +
                            "/suite/plugins/servlet/appianautomatedtest?operation=startProcessWithPMUuId&pmUuid=" +
                            URLEncoder.encode(processModelUuid, "UTF-8"));
            String pageSource = settings.getDriver().getPageSource();
            if (pageSource.contains("Exceptions occur")) {
                return false;
            } else {
                LOG.debug("PROCESS ID: " + pageSource);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated
    public boolean waitUntilTaskOfProcessModelUuidStartedRecentlyIsCompleted(String taskName, String pmUuid) {
        WebDriver driver = settings.getDriver();

        boolean completed = false;
        try {

            int i = 0;
            String seconds = "120";
            while (!completed) {
                if (i > 120) {
                    return false;
                }

                Thread.sleep(5000);

                Set<String> windows = settings.getDriver().getWindowHandles();
                String mainHandle = settings.getDriver().getWindowHandle();

                ((JavascriptExecutor) driver).executeScript("window.open();");

                Set<String> completeWindow = driver.getWindowHandles();
                completeWindow.removeAll(windows);
                String completeHandle = ((String) completeWindow.toArray()[0]);

                driver.switchTo().window(completeHandle);
                driver.get(settings.getUrl() +
                        "/suite/plugins/servlet/appianautomatedtest?" +
                        "operation=queryIsTaskCompletedWithinSeconds&pmUuid=" +
                        URLEncoder.encode(pmUuid, "UTF-8") + "&taskName=" + URLEncoder.encode(taskName, "UTF-8") +
                        "&seconds=" +
                        URLEncoder.encode(seconds, "UTF-8"));

                String pageSource = driver.getPageSource();
                driver.close();
                driver.switchTo().window(mainHandle);

                if (pageSource.contains("Task is completed")) {
                    completed = true;
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return completed;
    }

    @Deprecated
    public boolean verifyDataInDatabaseWithQueryAndFields(String sqlQuery, String fields) {
        WebDriver driver = settings.getDriver();

        try {
            Set<String> windows = driver.getWindowHandles();
            String mainHandle = driver.getWindowHandle();

            ((JavascriptExecutor) driver).executeScript("window.open();");

            Set<String> verifyWindow = driver.getWindowHandles();
            verifyWindow.removeAll(windows);
            String verifyHandle = ((String) verifyWindow.toArray()[0]);

            driver.switchTo().window(verifyHandle);
            driver.get(settings.getUrl() +
                    "/suite/plugins/servlet/appianautomatedtest?operation=verifyDataInDataBase&dataSource=" +
                    URLEncoder.encode(settings.getDataSourceName(), "UTF-8") + "&sqlQuery=" +
                    URLEncoder.encode(sqlQuery, "UTF-8") + "&fields=" +
                    URLEncoder.encode(fields, "UTF-8"));

            String pageSource = driver.getPageSource();
            String jsonSource = pageSource.substring(pageSource.indexOf("["), pageSource.indexOf("]") + 1);

            driver.close();
            driver.switchTo().window(mainHandle);

            JSONArray resultArr = new JSONArray(jsonSource);

            return resultArr.length() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated
    public boolean verifyConstantHasValueOf(String constantName, String expectedConstantValue) {
        WebDriver driver = settings.getDriver();

        try {
            Set<String> windows = driver.getWindowHandles();
            String mainHandle = driver.getWindowHandle();

            ((JavascriptExecutor) driver).executeScript("window.open();");

            Set<String> verifyWindow = driver.getWindowHandles();
            verifyWindow.removeAll(windows);
            String verifyHandle = ((String) verifyWindow.toArray()[0]);

            driver.switchTo().window(verifyHandle);
            driver.get(settings.getUrl() +
                    "/suite/plugins/servlet/appianautomatedtest?operation=verifyConstantHasValueOf&constantName=" +
                    URLEncoder.encode(constantName, "UTF-8") + "&expectedConstantValue=" +
                    URLEncoder.encode(expectedConstantValue, "UTF-8"));

            String pageSource = driver.getPageSource();
            driver.close();
            driver.switchTo().window(mainHandle);

            return pageSource.contains("Constant value is verified");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Closes browser and driver quits. Used to end automated test.<br>
     * <br>
     * FitNesse Example: <code>| tear down selenium driver |</code>
     */
    @Deprecated
    public void tearDownSeleniumWebDriver() {
        tearDown();
    }

    /**
     * Closes browser and driver quits. Used to end automated test.<br>
     * <br>
     * FitNesse Example: <code>| tear down |</code>
     */
    public void tearDown() {
        settings.getDriver().quit();
    }

    /**
     * Returns a random string of a specific length<br>
     * <br>
     * FitNesse example: <code>| $rand= | get random string | 5 | </code> - This
     * will set the variable <i>rand</i> to a random string
     * which can later be accessed using $rand.
     *
     * @param length Length of random string
     * @return Random alphanumeric string
     */
    public String getRandomString(int length) {
        return RandomStringUtils.insecure().nextAlphanumeric(length);
    }

    /**
     * Returns a random alphabetic string of a specific length<br>
     * <br>
     * FitNesse example: <code>| $rand= | get random alphabet string | 5 | </code> -
     * This will set the variable <i>rand</i> to a random
     * alphabet string
     * which can later be accessed using $rand.
     *
     * @param length Length of random alphabet string
     * @return Random alphabet string
     */
    public String getRandomAlphabetString(int length) {
        return RandomStringUtils.insecure().nextAlphabetic(length);
    }

    /**
     * Returns a random integer of a specific length<br>
     * <br>
     * FitNesse example:
     * <code>| $randInt= | get random integer from | INT_MIN | to | INT_MAX | </code>
     * - This will set the variable
     * <i>randInt</i> to a random integer between two numbers which can later be
     * accessed using $randInt.
     *
     * @param min Minimum of random integer
     * @param max Maximum of random integer
     * @return Random integer between the min and max
     */
    public int getRandomIntegerFromTo(int min, int max) {
        if (min > max) {
            throw ExceptionBuilder.build(new IllegalArgumentException("Min cannot exceed the Max"), settings,
                    "Get Random Int");
        }
        Random random = new Random();
        long range = (long) max - (long) min;
        long fraction = (long) (range * random.nextDouble());
        return (int) (fraction + min);
    }

    /**
     * Returns a random integer of a specific length<br>
     * <br>
     * FitNesse example:
     * <code>| $randDecimal= | get random decimal from | DOUBLE_MIN |to | DOUBLE_MAX|</code>
     * - This will set the
     * variable <i>randDecimal</i> to a random decimal between two numbers which can
     * later be accessed using $randDecimal.
     *
     * @param min Minimum of random decimal
     * @param max Maximum of random decimal
     * @return Random decimal between the min and max
     */
    public double getRandomDecimalFromTo(double min, double max) {
        if (min > max) {
            throw ExceptionBuilder.build(new IllegalArgumentException("Min cannot exceed the Max"), settings,
                    "Get Random Decimal");
        }
        Random random = new Random();
        double range = max - min;
        double fraction = (range * random.nextDouble());
        return (fraction + min);
    }

    /**
     * Returns a random integer of a specific length<br>
     * <br>
     * FitNesse example:
     * <code>| $randDecimal= | get random decimal from | DOUBLE_MIN |to | DOUBLE_MAX| with | DECIMAL_PLACES |</code>
     * -
     * This will set the variable <i>randDecimal</i> to a random decimal between two
     * numbers which can later be accessed using $randDecimal.
     *
     * @param min           Minimum of random decimal
     * @param max           Maximum of random decimal
     * @param decimalPlaces Number of integers after the decimal places to display
     * @return Random decimal between the min and max with a certain number of
     *         decimal places
     */
    public double getRandomDecimalFromToWith(double min, double max, int decimalPlaces) {
        if (min > max) {
            throw ExceptionBuilder.build(new IllegalArgumentException("Min cannot exceed the Max"), settings,
                    "Get Random Decimal");
        }
        Random random = new Random();
        double range = max - min;
        double fraction = (range * random.nextDouble());
        BigDecimal total = new BigDecimal(fraction + min);
        BigDecimal trimmed = total.setScale(decimalPlaces, RoundingMode.HALF_DOWN);
        return trimmed.doubleValue();
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * Determines if a URL can be sent
     *
     * @return true if the url event has already been sent
     */
    public static boolean canSendUrlEvent() {
        return !urlSent;
    }
}
