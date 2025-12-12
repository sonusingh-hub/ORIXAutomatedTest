# Appian Selenium API Example with Java

## Introduction

> A template using Appian Selenium API with Java.

## Usage

1. Open this project in an IDE, such as IntelliJ IDEA or Eclipse.
1. Update configurations:
    1. Open file `configs/custom.properties`
        1. Update `automated.testing.home` with the path to this installation.
           Examples:
            * Windows: `automated.testing.home=C:\\testFolder\\appian-selenium-api-example-java`
            * Mac: `automated.testing.home=/Users/tester/Desktop/appian-selenium-api-example-java`
        2. (Optional) Update `chrome.driver.home` with the path to a specific chrome driver.
            * If this is not supplied, the correct driver will automatically be downloaded.
            * Specific versions can be downloaded at https://chromedriver.chromium.org/downloads
    1. Open file `configs/users.properties`, for each username used in a test add a line in the format of
       `<username>=<password>` to this file.
        * If you prefer not to disclose your password in plain text, you can also choose to use
          `com.appiancorp.ps.automatedtest.fixture.BaseFixture.loginIntoWithUsernameAndPassword` fixture method
          to login by passing in the password from a system property value.
1. Open `src/main/java/com/appiancorp/example/HelloAppian.java`:
    1. Update `TEST_SITE_URL` to your Appian URL (ending in `/suite`), such as
       `protected static String TEST_SITE_URL = "https://example.appiancloud.com/suite";`
    1. Update `TEST_USERNAME` with a username, such as `protected static String TEST_USERNAME = "tester.tester";`
        * Make sure this user exists in `configs/users.properties`
    1. Update `TEST_SITE_VERSION` with a valid Appian site version, such as
       `protected static String TEST_SITE_VERSION = "21.2"`
1. Run the `main()` method of this class:
    * You should see that a `Chrome` browser will open up, and the fixture commands in `HelloAppian.java` will
      execute accordingly.
    * Note that this test will fail since your site might not have an action "Create a Case" under menu "Actions"
      that opens up an interface that has a "Submit" button.

## JavaDoc

* Unzip `javadoc.zip` and open the `index.html` file in a browser. 
