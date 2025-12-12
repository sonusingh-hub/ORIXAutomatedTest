package com.appiancorp.ps.cucumber.fixtures;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.fixture.SitesFixture;
import io.cucumber.java.en.Given;

public class CucumberSitesFixture {
    private static SitesFixture fixture = new SitesFixture();

    public CucumberSitesFixture() {
        fixture.setSettings(CucumberBaseFixture.getSettings());
    }

    @Given("^I navigate to site \"([^\"]*)\"$")
    public void navigateToSite(String siteUrl) {
        fixture.navigateToSite(siteUrl);
    }

    @Given("^I navigate to site \"([^\"]*)\" page \"([^\"]*)\"$")
    public void navigateToSitePage(String siteUrl, String pageUrl) {
        fixture.navigateToSitePage(siteUrl, pageUrl);
    }

    @Given("^I navigate to site \"([^\"]*)\" page \"([^\"]*)\" in group \"([^\"]*)\"$")
    public void navigateToSitePageInGroup(String siteIdentifier, String pageIdentifier, String groupIdentifier) {
        fixture.navigateToSitePageInGroup(siteIdentifier, pageIdentifier, groupIdentifier);
    }

    @Given("^I click on site page \"([^\"]*)\"$")
    public void clickOnSitePage(String sitePage) {
        fixture.clickOnSitePage(sitePage);
    }

    @Given("^I click on site page \"([^\"]*)\" in group \"([^\"]*)\"$")
    public void clickOnSitePageInGroup(String sitePage, String group) {
        fixture.clickOnSitePageInGroup(sitePage, group);
    }

    @Given("^I open settings menu$")
    public void openSettingsMenu() {
        fixture.openSettingsMenu();
    }

    @Given("^I open user profile$")
    public void openUserProfile() {
        fixture.openUserProfile();
    }

    @Given("^I use discoverability to navigate to \"([^\"]*)\"$")
    public void useDiscoverabilityToNavigateTo(String sitename) {
        fixture.useDiscoverabilityToNavigateTo(sitename);
    }

    @Given("^I logout of site$")
    public void logout() {
        fixture.logout();
    }

    @Given("^I toggle sidebar$")
    public void toggleSidebar() {
        fixture.toggleSidebar();
    }

    public static Settings getSettings() {
        return fixture.getSettings();
    }

}
