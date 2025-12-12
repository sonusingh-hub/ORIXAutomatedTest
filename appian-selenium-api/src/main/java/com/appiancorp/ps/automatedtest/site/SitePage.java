package com.appiancorp.ps.automatedtest.site;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Navigateable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class SitePage extends AppianObject implements WaitFor, Clickable, Navigateable {

    private static final Logger LOG = LogManager.getLogger(SitePage.class);
    private static final String XPATH_ABSOLUTE_SITE_PAGE_LINK = Settings.getByConstant("xpathAbsoluteSitePageLink");
    private static final String XPATH_RELATIVE_SITE_GROUP = Settings.getByConstant("xpathRelativeSiteGroup");
    private static final String XPATH_RELATIVE_SITE_PAGE_LINK_IN_SITE_GROUP =
            Settings.getByConstant("xpathRelativeSitePageInGroup");
    private static final String XPATH_ABSOLUTE_SITE_LOGOUT_LINK = Settings.getByConstant("xpathAbsoluteSiteLogoutLink");
    private static final String XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK =
            Settings.getByConstant("xpathAbsoluteSiteUserProfileLink");
    private static final String XPATH_ABSOLUTE_SITE_PROFILE_MENU_LINK =
            Settings.getByConstant("xpathAbsoluteSiteProfileMenuLink");
    private static final String XPATH_ABSOLUTE_SITE_SETTINGS_MENU_LINK =
            Settings.getByConstant("xpathAbsoluteSiteSettingsMenuLink");
    private static final String XPATH_ABSOLUTE_SITES_DISCOVERABILITY_MENU_LINK =
            Settings.getByConstant("xpathSitesDiscoverabilityMenu");
    private static final String XPATH_RELATIVE_SITENAME_MENU_LINK =
            Settings.getByConstant("xpathRelativeSiteNameMenuLink");
    private static final String XPATH_ABSOLUTE_SIDE_BAR_COLLAPSE_BUTTON =
            Settings.getByConstant("xpathSideBarCollapseButton");

    public static SitePage getInstance(Settings settings) {
        return new SitePage(settings);
    }

    private SitePage(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String sitePage = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_SITE_PAGE_LINK, sitePage);
    }

    @Override
    public void click(String... params) {
        String sitePage = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("CLICK [" + sitePage + "]");
        }

        try {
            WebElement menu = settings.getDriver().findElement(By.xpath(getXpath(params)));
            clickElement(menu);
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Site Page", sitePage);
        }
    }

    @Override
    public void waitFor(String... params) {
        String sitePage = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("WAIT FOR [" + sitePage + "]");
        }

        try {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
        } catch (Exception e) {
            throw ExceptionBuilder.build(e, settings, "Site Page", sitePage);
        }
    }

    @Override
    public void navigateTo(String... params) {
        String siteIdentifier = getParam(0, params);
        String pageIdentifier = getParam(1, params);
        String groupIdentifier = null;

        if (params.length > 2) {
            groupIdentifier = getParam(2, params);
        }

        if (LOG.isDebugEnabled()) {
            String debugStmt = "NAVIGATE TO SITE [" + siteIdentifier + "] PAGE [" + pageIdentifier + "]" +
                    (groupIdentifier == null ? "" : " IN GROUP [" + groupIdentifier + "]");
            LOG.debug(debugStmt);
        }

        String url = settings.getUrl() + "/sites/" + siteIdentifier +
                (groupIdentifier == null ? "" : "/group/" + groupIdentifier) + "/page/" + pageIdentifier;

        settings.getDriver().get(url);
        waitForProgressBar();
    }

    public void navigateToProfile() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("NAVIGATE TO PROFILE");
        }

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK)));

        WebElement userProfile = settings.getDriver()
                .findElement(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK));
        userProfile.click();

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_PROFILE_MENU_LINK)));

        WebElement profileLink = settings.getDriver()
                .findElement(By.xpath(XPATH_ABSOLUTE_SITE_PROFILE_MENU_LINK));
        profileLink.click();
    }

    public void navigateToSettings() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("NAVIGATE TO SETTINGS");
        }

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK)));

        WebElement userProfile = settings.getDriver()
                .findElement(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK));
        userProfile.click();

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_SETTINGS_MENU_LINK)));

        WebElement settingsLink = settings.getDriver()
                .findElement(By.xpath(XPATH_ABSOLUTE_SITE_SETTINGS_MENU_LINK));
        settingsLink.click();
    }

    public void clickOnSiteGroup(String groupName) {
        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(xpathFormat(XPATH_RELATIVE_SITE_GROUP, groupName))));

        WebElement siteGroup =
                settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_RELATIVE_SITE_GROUP, groupName)));
        siteGroup.click();
    }

    public void clickOnSitePageInGroup(String sitePage, String groupName) {
        // Combine group and page XPaths to only find the page link that is under the group
        String groupXPath = xpathFormat(XPATH_RELATIVE_SITE_GROUP, groupName);
        String pageXPath = xpathFormat(XPATH_RELATIVE_SITE_PAGE_LINK_IN_SITE_GROUP, sitePage);
        String pageUnderGroupXPath = groupXPath + "//following-sibling::*" + pageXPath;

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.elementToBeClickable(By.xpath(pageUnderGroupXPath)));

        WebElement sitePageElement = settings.getDriver().findElement(By.xpath(pageUnderGroupXPath));
        this.clickElement(sitePageElement);
    }

    public void useSitesDiscoverabilityMenu(String sitename) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("USE SITES DISCOVERABILITY MENU TO GO TO SITE [" + sitename + "]");
        }

        WebElement sitesDiscoverabilityMenu = settings.getDriver()
                .findElement(By.xpath(XPATH_ABSOLUTE_SITES_DISCOVERABILITY_MENU_LINK));
        sitesDiscoverabilityMenu.click();

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(XPATH_ABSOLUTE_SITES_DISCOVERABILITY_MENU_LINK)));

        WebElement siteLink = settings.getDriver()
                .findElement(By.xpath(xpathFormat(XPATH_RELATIVE_SITENAME_MENU_LINK, sitename)));
        siteLink.click();
    }

    public void logout() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("LOG OUT");
        }

        if (settings.getDriver().findElements(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK)).isEmpty()) {
            (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK)));
            WebElement userProfile = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SITE_USER_PROFILE_LINK));
            userProfile.click();
        }

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK)));

        WebElement logoutLink = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SITE_LOGOUT_LINK));
        logoutLink.click();
    }

    public void toggleSidebar() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("TOGGLE SIDEBAR");
        }

        (new WebDriverWait(settings.getDriver(), Duration.ofSeconds(settings.getTimeoutSeconds()))).until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ABSOLUTE_SIDE_BAR_COLLAPSE_BUTTON)));

        WebElement toggleSidebar = settings.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_SIDE_BAR_COLLAPSE_BUTTON));
        toggleSidebar.click();
    }
}
