# FitNesse for Appian

FitNesse for Appian is a client side tool, built on top of [FitNesse](http://docs.fitnesse.org/), that makes it easy to create automated tests on the Appian platform. This tool allows you to create test scripts in a easy to read wiki language that will be run by the system to interact with an Appian application as a human user.

FitNesse for Appian provides an Appian-specific wiki language within FitNesse that maps directly to actions that can be performed in Appian, such as:

* Navigating the interface (News, Tasks, Records, Reports, Actions, Sites)
* Starting actions and completing tasks
* Validating content on record views or in news events
* And much more... the complete list is available directly within the tool!

For more information about testing and development best practices when using FitNesse for Appian, please read [BESTPRACTICES.md](BESTPRACTICES.md)

## Setup

The following steps are for manual setup. If you can use docker, you can follow the instructions under "Running the Tests" in contributing.md

1) Unzip the contents into your root directory or preferred location (e.g. C:\)
2) In a terminal window navigate to the folder extracted from the zip file (e.g. C:\fitnesse-for-appian). We will refer to this directory as TESTING_HOME throughout this documentation
    * For PC, main commands will be:
        * dir: used to show contents of current directory (folder)
        * cd: used to change directory (open a folder), e.g. cd Documents would open the Documents folder. cd .. would go to the parent folder of current folder.
    * For Mac/Linux,
        * ls: used to show contents of a directory (folder)
        * cd: used to change directory (open a folder), e.g. cd Documents would open the Documents folder. cd .. would go to the parent folder of current folder.
3) Open the "custom.properties" file located in TESTING_HOME/configs and change:
    * automated.testing.home to be TESTING_HOME (e.g. C:\FitNesseForAppian)
    * download.directory to be a folder of your choosing
    * chrome.driver.home to be TESTING_HOME/lib/drivers/chromedriver(.exe if using windows, -mac if using a mac, -linux if using UNIX)
    * firefox.driver.home to be TESTING_HOME/lib/drivers/geckodriver(.exe if using windows, -mac if using a mac, -linux64 if using UNIX)
    * edge.driver.home to be TESTING_HOME/lib/drivers/msedgedriver(.exe if using windows, -mac if using a mac, -linux64 if using UNIX)
4) Run start.bat if using windows, start-mac.sh for Mac, and start-unix.sh for UNIX to install and run FitNesse for Appian. Installation should take around 30 seconds and is complete when you see the message "Starting FitNesse on port: 8980". To stop FitNesse for Appian, invoke Ctrl+C in the terminal.

## Running Tests

* Navigate to http://localhost:8980/FitNesseForAppian.Examples.TestExample
* Follow the configuration instructions marked by "Please follow the directions below to execute the test"
* Click Test at the top of the page

### Developing custom tests

To try out the tool further, use one of the `Automated Testing - xx.x.zip` files from the [apps](../apps/) directory and import it into your corresponding version of Appian environment.

Navigate to the FitNesseForAppian Cheatsheet and make use of the supported methods to write your own test suite to run against the sample application! Be sure you are using methods for the environment you are testing. For example, if testing sites, use navigation methods that come from the "Sites Methods" scenario. Using methods from a different environment has the potential to break in future releases.
