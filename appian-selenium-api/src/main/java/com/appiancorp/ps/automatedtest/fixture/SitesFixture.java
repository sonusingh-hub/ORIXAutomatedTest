package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.common.Version;
import com.appiancorp.ps.automatedtest.site.Site;
import com.appiancorp.ps.automatedtest.site.SitePage;

public class SitesFixture extends TempoFixture {

    public SitesFixture() {
        super();
    }

    /**
     * Navigate to Appian Site.<br>
     * <br>
     * Example: <code>| navigate to site | SITE_NAME |</code>
     *
     * @param siteUrl Url of Appian site
     */
    public void navigateToSite(String siteUrl) {
        Site.getInstance(settings).navigateTo(siteUrl);
    }


    /**
     * Navigate to an Appian Site Page <br>
     * <br>
     * Example: <code>| navigate to site | SITE_URL | page | PAGE_URL | </code>
     *
     * @param siteUrl Url of Appian site
     * @param pageUrl Url of Page
     */
    public void navigateToSitePage(String siteUrl, String pageUrl) {
        SitePage.getInstance(settings).navigateTo(siteUrl, pageUrl);
    }

    /**
     * Navigate to an Appian Site Page that is organized under a Group <br>
     * <br>
     * Example: <code>| navigate to site | SITE_IDENTIFIER | page | PAGE_IDENTIFIER | in group | GROUP_IDENTIFIER | </code>
     *
     * @param siteIdentifier  Web Address Identifier of Appian site
     * @param pageIdentifier  Web Address Identifier of Page
     * @param groupIdentifier Web Address Identifier of the Group
     */
    public void navigateToSitePageInGroup(String siteIdentifier, String pageIdentifier, String groupIdentifier) {
        if (SitePage.getInstance(settings).atLeastVersion(23.3)) {
            SitePage.getInstance(settings).navigateTo(siteIdentifier, pageIdentifier, groupIdentifier);
        } else {
            throw new UnsupportedOperationException(
                    "navigateToSitePageInGroup is only supported for version 23.3 and later");
        }
    }

    /**
     * Navigate to Appian Site Page.<br>
     * <br>
     * Example: <code>| navigate to site | SITE_NAME |</code>
     *
     * @param sitePage Name of Appian site page
     */
    public void clickOnSitePage(String sitePage) {
        SitePage.getInstance(settings).waitFor(sitePage);
        SitePage.getInstance(settings).click(sitePage);
    }

    /**
     * Click on an Appian Site page organized under a site group<br>
     * <br>
     * Example: <code>|click on site page | SITE_PAGE | in group | SITE_GROUP |</code>
     *
     * @param sitePage Name of the Appian Site Page
     * @param group    Name of the group the Site Page is located under
     */
    public void clickOnSitePageInGroup(String sitePage, String group) {
        if (SitePage.getInstance(settings).atLeastVersion(23.3)) {
            SitePage.getInstance(settings).clickOnSiteGroup(group);
            SitePage.getInstance(settings).clickOnSitePageInGroup(sitePage, group);
        } else {
            throw new UnsupportedOperationException(
                    "clickOnSitePageInGroup is only supported for version 23.3 and later");
        }
    }

    /**
     * Opens settings menu on a site <br>
     * Example: <code>| Open settings menu |</code>
     */
    public void openSettingsMenu() {
        SitePage.getInstance(settings).navigateToSettings();
    }

    /**
     * Navigates to the user profile from the sites menu <br>
     * Example: <code>| Open user profile |</code>
     */
    public void openUserProfile() {
        SitePage.getInstance(settings).navigateToProfile();
    }

    /**
     * Navigates to another site from Sites Discoverability menu <br>
     * Example: <code>| Use discoverability to navigate to | SITE_NAME| </code>
     *
     * @param sitename Site Name
     */
    public void useDiscoverabilityToNavigateTo(String sitename) {
        SitePage.getInstance(settings).useSitesDiscoverabilityMenu(sitename);
    }

    /**
     * Logs out of Appian <br>
     * Example: <code>| logout |</code>
     */
    @Override
    public void logout() {
        if (Settings.getVersion().compareTo(new Version(17, 1)) < 0) {
            super.logout();
        } else {
            SitePage.getInstance(settings).logout();
        }
    }

    /**
     * Collapses or expands the sidebar <br>
     * Example: <code>| Toggle sidebar |</code>
     */
    public void toggleSidebar() {
        SitePage.getInstance(settings).toggleSidebar();
    }
}
