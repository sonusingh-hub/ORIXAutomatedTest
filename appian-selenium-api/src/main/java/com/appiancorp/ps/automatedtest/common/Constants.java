package com.appiancorp.ps.automatedtest.common;

public class Constants {

    public final static String AUTOMATED_TESTING_HOME = "automated.testing.home";

    public final static String DOWNLOAD_DIRECTORY = "download.directory";
    public final static String DOWNLOAD_MIME_TYPES = "download.mime.types";
    public final static String CHROME_CAPABILITIES = "chrome.capabilities";
    public final static String EDGE_CAPABILITIES = "edge.capabilities";

    public final static String ACCEPT_BUTTON_LABEL = "acceptButton";

    public final static String LOGIN_TO_BASE_URL = "login.to.base.url";

    public final static String FIELD_LOCATOR_PLACEHOLDER = "PLACEHOLDER:";
    public final static String FIELD_LOCATOR_INSTRUCTIONS = "INSTRUCTIONS:";
    public final static String FIELD_LOCATOR_TOOLTIP = "TOOLTIP:";

    public enum Driver {
        CHROME("chrome.browser.home", "chrome.driver.home", "chromedriver.exe"),
        FIREFOX("firefox.browser.home", "firefox.driver.home", "geckodriver.exe"),
        EDGE("edge.browser.home", "edge.driver.home", "msedgedriver.exe");

        private String browserHome;
        private String driverHome;
        private String driver;

        Driver(String browserHome, String driverHome, String driver) {
            this.browserHome = browserHome;
            this.driverHome = driverHome;
            this.driver = driver;
        }

        public String getBrowserHome() {
            return this.browserHome;
        }

        public String getDriverHome() {
            return this.driverHome;
        }

        public String getDriver() {
            return this.driver;
        }
    }

    public enum RemoteDriver {
        REMOTE_CHROME("chrome.host.ip", "chrome.host.port", "chrome", "chrome.host.protocol"),
        REMOTE_FIREFOX("firefox.host.ip", "firefox.host.port", "firefox", "firefox.host.protocol"),
        REMOTE_EDGE("edge.host.ip", "edge.host.port", "edge", "edge.host.protocol");

        private String browserIP;
        private String browserPort;
        private String browserName;
        private String browserProtocol;

        RemoteDriver(String browserIP, String browserPort, String browserName, String protocol) {
            this.browserIP = browserIP;
            this.browserPort = browserPort;
            this.browserName = browserName;
            this.browserProtocol = protocol;
        }

        public String getBrowserIP() {
            return this.browserIP;
        }

        public String getBrowserPort() {
            return this.browserPort;
        }

        public String getBrowserName() {
            return this.browserName;
        }

        public String getBrowserProtocol() {
            return this.browserProtocol;
        }
    }
}
