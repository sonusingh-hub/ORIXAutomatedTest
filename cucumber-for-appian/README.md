# Cucumber For Appian

Cucumber For Appian is a client-side tool that builds on top of what FitNesse For Appian has implemented and allows users to write automated UI tests against Appian with Gherkin syntax. Cucumber For Appian will support all of the same fixture methods that FitNesse For Appian currently supports.

## Overview

1. We host all the CucumberForAppian related artifacts in the `CucumberForAppian` directory
1. All the `StepDefs` are present in */CucumberForAppian/src/main/java/com/appiancorp/ps/cucumber/fixtures/*
    - `StepDefs` refer to the Java methods that all the Gherkin language tests connect to to execute the actual logic
1. There are three StepDef files:
    - `CucumberBaseFixture.java`
    - `CucumberSitesFixture`
    - `CucumberTempoFixture`
1. They all correspond 1-1 with all the defined fixture classes present in
   */src/main/java/com/appiancorp/ps/automatedtest/fixture*
1. The `CheatSheet.feature` file located in
   */CucumberForAppian/src/main/resources/TestExample/src/test/resources/CheatSheet.feature* lists out all the methods
   we support and how to invoke them using the `@Given, @When, @Then, @But, @And....` and other Cucumber supported
   annotation

## Running Tests

* Edit the [TempoNavigationExample.feature](./src/main/resources/TestExample/src/test/resources/TempoNavigationExample.feature) file. The comments on the top of the .feature files will also help you in setting up.
  * `BROWSER` - Chrome, Edge, or Firefox
  * `APPIAN_URL` - e.g. https://site-name.appiancloud.com
  * `APPIAN_VERSION` - e.g. 25.2
  * `APPIAN_LOCALE` - en_US or en_GB
  * `APPIAN_USERNAME` - e.g. fitnesse.user
* Run `docker exec docker-fitnesse-1 run_cucumber.sh`

### Choosing tests to run

By default, only the TempoNavigationExample.feature test file will run when you execute the command above due to the “tags” argument in RunCucumberForAppianTest.java.
 
At the top of the TempoNavigationExample.feature file, there is a tag “@Tempo” which corresponds to that in the .java file. To selectively run the tests, you can add or remove tags as you wish. You can completely remove the tags parameter if you’d like to run all of the tests together.

## Developing Custom Cucumber tests

The Cucumber sample tests from above will be a good model to follow when developing your own tests.

To help you out, we have provided a [CucumberForAppian Cheatsheet](./src/main/resources/TestExample/src/test/resources/CheatSheet.feature). When developing tests, be sure you are using methods for the environment you are testing. For example, if testing sites, use navigation methods that come from the "Sites Methods" scenario. Using methods from a different environment has the potential to break in future releases.

The CheatSheet.feature file lists out all the methods we support and how to invoke them using the @Given, @When, @Then, @But, @And.... and other Cucumber supported annotation

## Maintenance

- Cucumber fixtures are essentially a wrapper and call all the FitNesse fixture methods defined in
  */src/main/java/com/appiancorp/ps/automatedtest/fixture/ \*.java*
- Any addition made in the FitNesse fixtures that will be available to customers need to have a corresponding entry in
  the appropriate Cucumber fixture file
- Any addition to the CheatSheet for FitNesse need to be mirrored in the CheatSheet for Cucumber
- Any addition to unit tests for FitNesse fixtures should have a mirrored entry in the Integration tests for Cucumber
  fixtures
- **Main point to take away is that whatever edits you make in FitNesse should be duplicated in Cucumber**
