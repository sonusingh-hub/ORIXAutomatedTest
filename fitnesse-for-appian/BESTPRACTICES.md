# Best Practices

For more general FitNesse documentation, visit the [FitNesse Full Reference Guide](http://fitnesse.org/FitNesse.FullReferenceGuide).

## FitNesse Page Types

FitNesse is comprised of Static, Suite, and Test pages.  The image below shows examples of each type of page and how they are used. More information on each page type and what they should include can be found in the “Organizing Tests” page of the FitNesse for Appian documentation and the FitNesse documentation on page properties.

 
## Scenarios

Scenarios are reusable test scripts used to reduce the maintenance effort for repetitive tests. Scenarios are to FitNesse what a reusable expression rule is to Appian. If the same test script is repeated on several test pages, this script should be made into a scenario so that any changes to it will be reflected in all of its implementations. Note that scenarios can contain other scenarios.

A list of scenarios, called a “ScenarioLibrary,” can be created as a Test Page. This library can then be called by other Test Pages.  The ScenarioLibrary Test Page can  be located on a Static Page where other utility scripts are called, such as the Set up and Tear Down scripts (as pictured in Fig. 2). The image below demonstrates the structure of the ScenarioLibrary and how a Test Page calls its scenarios within the test flow. For more information on where the ScenarioLibrary is kept and used, refer to the “Scenarios” and “Organizing Tests” pages of the FitNesse for Appian documentation and the Scenario Table page in the FitNesse documentation. 

 
## Writing Tests

The primary Static page, shown below, is where Test Pages, Scenario Library, and Set Up/Tear Down scripts are located.  Users can select Test Pages to edit or execute their tests.  Users can select the Scenario Library link to edit the various scenario scripts that will be called from the Test Pages.  The Suite Set up, Set Up and Tear Down scripts are used to initialize and cleanup Selenium drivers.  Learn more about special pages like SuiteSetUp and SuiteTearDown in the FitNesse documentation. 

 
Any script that should be executed before a Test Page should go into SetUp, and every script that should be executed after every Test Page should go into TearDown. See the figure below to understand how these tables are related to a Test or Suite Page.

_Note: The Suite Set Up script initializes the test by navigating to the desired URL. So, the Suite Set Up script is specific to a single environment and will need to be adjusted for testing in new environments._

Some considerations to take when writing tests:

* When the test environment performance is slow or when the script needs to be paused momentarily, an explicit wait time can be added. 
  * For example, use explicit waits ("| wait for | 1 minute |") between form inputs when you know a background process needs to be completed before the next step. In this scenario, not using an explicit wait may result in misleading errors caused by page slowness, not by errors on the interface. 
* Create test pages so they can be run independently and don’t require previous tests to be successful. That way, if any tests fail, the rest can still run. 
* Avoid hard-coding test data. Hard coded test data requires users to manually locate and update the test values whenever a change is needed. Instead, use variables to allow more flexibility in testing. Using variables keeps test data values in one place at the top of the script and allows testers to easily change test data and will reduce errors when tests need to be updated. The “Using Variables” section below provides more detail on how to use variables in FitNesse.

## Using Variables

In Fitnesse, test data can be substituted with variables when needed. When creating test scripts or scenarios, it is a best practice to avoid hard-coding test data, which requires users to manually locate and update the test values whenever a change is needed. Instead, use variables to keep test data values in one place at the top of the script. Doing so will make changing test data easier and reduce the risk of errors when tests need to be updated.

There are three ways to use variables:

1. **Markup Variables:** Used within the Test Page or scenario script for variables that will be referenced throughout the entire script. Only markup variables, created through !define, can be used within expressions.
2. **SLIM Symbols:**  Used within a Test Page or Scenario to set random string values.
3. **Test Variables:** Used with JSON to capture multiple or complex variables at one time.
 
## Working With Test Data

If tests rely on data or processes to be in a certain state, it is useful to create the following two web API's per application: one to set up the test data, and the other one to clean up the test data. Test scripts can call these web APIs using “|get web api|” syntax which can be found in the FitNesse for Appian Cheat Sheet. Web APIs used for testing purposes should not be deployed to a Production environment.

* **Web API for Setting Up Test Data:** If a test requires an existing approval task, or a record to be in a certain status before testing can proceed, those will need to be set up ahead of time. Web APIs can be used to generate data and write to tables, or to kick off processes, depending on what is required. 
* **Web API for Cleaning Up Test Data:** After tests are run and test data is no longer needed, a web API can be used to delete test data and clean up any changes in the app. 

## Running Tests

* Create test user accounts for your application in all testing environments (Test, UAT, Sandbox as applicable). Do not use your own credentials. Using test user accounts will ensure that when testing, the logged in user is in the intended groups and has the appropriate permissions.
* To allow test users to work in FitNesse, you will need to include passwords for their usernames in the FitNesseForAppian/configs/users.properties file
* Logout before logging in at the beginning of each test page. Subsequent tests which begin by logging a user in will fail if the user is already logged in. 
* Enable error screenshots using “|set take error screenshots to | true|” in test scripts to easily determine where tests have failed. These screenshots will be saved to the screenshot path folder,  as defined by the “|set screenshot path to|SCREENSHOT_PATH|” method.

## Designing Appian Applications for FitNesse

General Practices: 

* If you are using the character “|” in SAIL component labels or altText, it can only be readable if the value is surrounded by “!- <value> -!”. For example, if you are trying to use this syntax: “|populate field|Field Name|with|value|” and the field labeled Field Name has a “|” character in it, the correct syntax would be “|populate field|!-Field Name-!|with|value|”
* Because FitNesse for Appian uses SAIL component labels to locate components, it is a best practice to create labels for all components so that test scripts can read those labels, rather than having to rely on the order of  components in an interface. If displaying component labels does not make sense, use the labelPosition “COLLAPSED” option on the component so that the label will not display. 

### Cards and Rich Text

FitNesse for Appian generally utilizes field labels and indexes to identify and populate Appian components. However, more graphical clickable components such as rich text icons and cards do not have labels and are identified by other properties, like link labels, alt text, and accessibility text. Although these properties do not impact what the end user sees, it is a best practice to populate them with values that are intuitive to an end user or tester so that:

* Applications can be tested using FitNesse for Appian without additional application changes
* Testing teams can write automated tests using the intuitive label for the component and do not have to rely on development teams for information
* The application is more readable by a screen reader

To achieve the above, follow these guidelines:

* **Cards:** When creating cardLayouts, developers should always include accessibilityText which matches any text displayed to the end user. If no text is displayed (the card contains an icon only, for example), use text which is intuitive to testers. 
  * **With Links:** When creating a cardLayout with a link, always include link labels using the same text as would be used for cardLayout accessibilityText, described in the previous bullet. 
* **Rich Text Icons:** When creating rich text icons, use altText if testers will need to validate what the icon represents. For example, a check icon would have "Check" as its altText so that it can be verified on the UI.
  * **With Links:** When using rich text icons with links, always include a caption for each clickable icon that matches the altText.
