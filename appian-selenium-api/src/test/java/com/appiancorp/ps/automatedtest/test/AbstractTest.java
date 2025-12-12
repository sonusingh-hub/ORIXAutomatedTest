package com.appiancorp.ps.automatedtest.test;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.Settings;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest {
    protected static final String TEST_BROWSER =
            System.getenv("browser") == null ? "REMOTE_CHROME" : System.getenv("browser");
    protected static final String TEST_SITE_VERSION =
            System.getenv("version") == null ? "26.1" : System.getenv("version");
    protected static final String TEST_SITE_URL = System.getenv("url");
    protected static final String TEST_SITE_LOCALE = "en_US";
    protected static final String TEST_ROLE = "role.basic_user";
    protected static final String TEST_USERNAME = "fitnesse.user";
    protected static final String TEST_SITE_USERNAME = "fitnesse.twoser";
    protected static final String TEST_PASSWORD = "password5";
    protected static final String TEST_ADMIN_ROLE = "role.admin";

    protected static final String TEST_ADMIN_USERNAME = "fitnesse.admin";
    protected static final String TEST_ADMIN_PASSWORD = "Pa$$w0rd5";
    protected static final Integer TEST_TIMEOUT =
            System.getenv("timeout") == null ? 10 : Integer.parseInt(System.getenv("timeout"));

    public static boolean atLeastVersion(Double version) {
        return Double.parseDouble(TEST_SITE_VERSION) >= version;
    }

    public static boolean isRunningOnJenkins() {
        return Settings.isLinux();
    }

    public static boolean isBrowser(String browser) {
        return TEST_BROWSER.equals(browser);
    }

    public static boolean isRemoteBrowser() {
        return (isBrowser(Constants.RemoteDriver.REMOTE_CHROME.name()) ||
                isBrowser(Constants.RemoteDriver.REMOTE_FIREFOX.name()) ||
                isBrowser(Constants.RemoteDriver.REMOTE_EDGE.name()));
    }

    @BeforeAll
    public static void setJUnitTest() throws Exception {
        if (TEST_SITE_URL == null) {
            throw new Exception("url environment variable must be set");
        }
        Settings.setIsJunitTest(true);
    }
}
