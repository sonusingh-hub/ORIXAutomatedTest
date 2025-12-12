package com.appiancorp.ps.automatedtest.tempo;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import com.appiancorp.ps.automatedtest.common.ConfigReader;

import static com.appiancorp.ps.automatedtest.common.Constants.LOGIN_TO_BASE_URL;

public final class TempoLogin extends AppianObject {

    private static final Logger LOG = LogManager.getLogger(TempoLogin.class);
    private static final String XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON =
            Settings.getByConstant("xpathAbsoluteLoginSubmitButton");
    private static final String XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON =
            Settings.getByConstant("xpathAbsoluteLoginAgreeButton");
    private static final String XPATH_ABSOLUTE_LOGOUT_LINK = Settings.getByConstant("xpathAbsoluteLogoutLink");
    private static final String XPATH_ABSOLUTE_LOGIN_BOX = Settings.getByConstant("xpathAbsoluteLoginBox");

    private static final String XPATH_CUSTOM_LOGIN_SUBMIT_BUTTON_FORMAT =
            "//*[@id='%1$s' or @name='%1$s' or @value='%1$s' or text()='%1$s']";

    private static final String XPATH_ABSOLUTE_SITE_LOGOUT_LINK = Settings.getByConstant("xpathAbsoluteSiteLogoutLink");
    private static final String XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK =
            Settings.getByConstant("xpathAbsoluteSiteUserProfileLink");

    private String usernameFieldId;
    private String passwordFieldId;
    private String loginButtonId;

    public static TempoLogin getInstance(Settings settings) {
        return new TempoLogin(settings);
    }

    private TempoLogin(Settings settings) {
        super(settings);

        usernameFieldId = coalesce(System.getProperty("fitnesseForAppian.login.username"), "un");
        passwordFieldId = coalesce(System.getProperty("fitnesseForAppian.login.password"), "pw");
        // We don't coalesce here because we want to check if this unset
        loginButtonId = System.getProperty("fitnesseForAppian.login.loginButton");
    }

    private String coalesce(String value, String alternative) {
        return (value != null) ? value : alternative;
    }

    public void logout() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("LOG OUT");
        }

        try {
            waitForTempoLogoutLink(5);
            // Logout using dropdown containing Sign Out and Site shortcut links
            ((JavascriptExecutor) settings.getDriver()).executeScript(
                    "document.evaluate(\"" + XPATH_ABSOLUTE_LOGOUT_LINK +
                            "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
        } catch (Exception e) {
            LOG.debug("Tempo sign out failed, trying Site sign out.");
            // If dropdown does not exist, logout by clicking user profile avatar > Sign Out
            //Check if User Profile is already clicked
            if (settings.getDriver().findElements(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK)).isEmpty()) {
                WebElement userProfile =
                        settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK));
                userProfile.click();
            }
            WebElement logoutLink = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK));
            logoutLink.sendKeys(Keys.ENTER);
        }

    }

    public void waitFor(String... params) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR LOGIN");
        }

        // Try up to 1 minute to check for the logout link by alternating
        // 5 seconds at a time trying the Tempo and Sites patterns
        for (int i = 0; i < 6; i++) {
            try {
                waitForTempoLogoutLink(5);
                break;
            } catch (Exception e) {
                try {
                    waitForSitesLogoutLink(5);
                    break;
                } catch (Exception ex) {
                    if (i == 5) {
                        throw ExceptionBuilder.build(ex, settings, "Logout link");
                    }
                }
            }
        }
    }

    private void waitForTempoLogoutLink(int timeout) {
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout)))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGOUT_LINK)));
    }

    private void waitForSitesLogoutLink(int timeout) {
        WebElement userProfile = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK));
        userProfile.click();

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK)));
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout)))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK)));
        userProfile.click();
    }

    public void login(String url, String userName, String password, boolean withTerms) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("LOGIN [" + userName + "]");
        }

        if (!Boolean.parseBoolean(PropertiesUtilities.getProps().getProperty(LOGIN_TO_BASE_URL)) && !withTerms) {
            settings.getDriver().get(url);
        }

        try {
            WebElement userNameElement = findInputElementByIdOrNameOrValue(usernameFieldId);
            userNameElement.clear();
            userNameElement.sendKeys(userName);

            WebElement passwordElement = findInputElementByIdOrNameOrValue(passwordFieldId);
            passwordElement.clear();
            passwordElement.sendKeys(password);

            /* Have to be specific as there is a hidden button for accepting terms */
            WebElement submitButton;
            if (isCustomLoginButtonPropertySet()) {
                submitButton = findInputElementByIdOrNameOrValue(loginButtonId);
            } else {
                submitButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON));
            }
            submitButton.click();

            waitFor();
        } catch (Exception e) {
            // If we fail to login, quit selenium so it doesn't hang when you retry
            settings.getDriver().quit();
            throw ExceptionBuilder.build(e, settings, "Login page", userName);
        }
    }

    private WebElement findInputElementByIdOrNameOrValue(String searchString) {
        WebElement webElement;

        try {
            webElement = settings.getDriver().findElement(By.id(searchString));
        } catch (NoSuchElementException e1) {
            try {
                webElement = settings.getDriver().findElement(By.name(searchString));
            } catch (NoSuchElementException e2) {
                try {
                    webElement = settings.getDriver().findElement(By.xpath("//*[@value='" + searchString + "']"));
                } catch (NoSuchElementException e3) {
                    try {
                        webElement = settings.getDriver().findElement(By.xpath("//*[text() ='" + searchString + "']"));
                    } catch (NoSuchElementException e4) {
                        webElement = null;
                    }
                }
            }
        }

        return webElement;
    }

    public void loginWithTerms(String url, String userName, String password) {
        try {
            WebElement agreeButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
            agreeButton.click();
        } catch (Exception e) {
            LOG.error("Terms", e);
            throw ExceptionBuilder.build(e, settings, "Terms");
        }

        waitForLogin();
        login(url, userName, password, true);
    }

    public void waitForLogin() {
        waitForLogin(30);
    }

    public void waitForLogin(int timeout) {
        try {
            String loginButtonXpath = isCustomLoginButtonPropertySet() ?
                    String.format(XPATH_CUSTOM_LOGIN_SUBMIT_BUTTON_FORMAT, loginButtonId) :
                    XPATH_ABSOLUTE_LOGIN_SUBMIT_BUTTON;
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(timeout))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(loginButtonXpath)));
            WebElement submitButton = settings.getDriver().findElement(By.xpath(loginButtonXpath));
            // Needs to align the button to top to prevent it by being covered by the copyright div
            scrollIntoView(submitButton, true);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Login");
        }
    }

    private boolean isCustomLoginButtonPropertySet() {
        return loginButtonId != null;
    }

    public void waitForTerms() {
        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds())))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON)));
            WebElement agreeButton = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_LOGIN_AGREE_BUTTON));
            // Needs to align the button to top to prevent it by being covered by the copyright div
            scrollIntoView(agreeButton, true);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Login");
        }
    }

    public void navigateToLoginPage(String url) {
        // If we are already logged in, log out
        settings.getDriver().get(url);

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_LOGIN_BOX)));
        } catch (Exception e) {
            try {
                logout();
            } catch (Exception e2) {
                return;
            }
        }
    }
}
