# Contributing to Appian Selenium API

## Building the Project

Appian Selenium API requires a minimum of Java 11 and can be built with `./gradlew build`

While Java 11 is the minimum version supported, we aim to retain compatibility with newer Java versions as well.
An automated check will run for all MRs to ensure any changes compile with future Java versions.

## Running the Tests

The easiest way to run tests is using the docker files within this project.

1. Navigate to the `docker` directory
2. Download the latest `fitnesse-for-appian.zip` and `cucumber-for-appian.zip` from the [Package Registry UI](https://gitlab.com/appian-oss/appian-selenium-api/-/packages) or your terminal. Replace GITLAB_TOKEN and VERSION in the commands below:

```
# fitnesse
curl -f --header "PRIVATE-TOKEN: <GITLAB_TOKEN>" "https://gitlab.com/api/v4/projects/appian-oss%2Fappian-selenium-api/packages/generic/FCS/<VERSION>/fitnesse-for-appian.zip" -o fitnesse-for-appian.zip
# cucumber
curl -f --header "PRIVATE-TOKEN: <GITLAB_TOKEN>" "https://gitlab.com/api/v4/projects/appian-oss%2Fappian-selenium-api/packages/generic/FCS/<VERSION>/cucumber-for-appian.zip" -o cucumber-for-appian.zip
```

3. Add an `.env` with the following keys
    * USERNAME - User that will be used to log in for tests
    * PASSWORD - Password for that user
    * (optional) SCREENSHOT_DIR - Path on your local machine that should be mapped to the screenshot directory
    * (optional) DOWNLOADS_DIR - Path on your local machine that should be mapped to the downloads directory
    * (optional) FITNESSEROOT_DIR - Path on your local machine that should be mapped to a Fitnesse root directory
    * (optional) CUCUMBERTEST_DIR - Path on your local machine that should be mapped to a directory with cucumber tests
    * (optional) BROWSER - Browser type to test against. This only controls the Selenium image used, you will still need to pass the appropriate environment variable to your tests when running (i.e. browser=REMOTE_FIREFOX)
4. Bring up the docker containers using `docker-compose up`
    * If you get errors about being unable to create directories, ensure the default directories (screenshots, downloads, FitNesseRoot, cucumber) exist before continuing

To run the example tests in this project, your `.env` file should look like this. You may need to create the `downloads` and `screenshots` directories inside of the `docker` directory.
```
USERNAME=user.name
PASSWORD=password
FITNESSEROOT_DIR=../fitnesse-for-appian/src/main/resources/FitNesseRoot
CUCUMBERTEST_DIR=../cucumber-for-appian/src/main/resources/TestExample/src/test/resources
SCREENSHOT_DIR=.
DOWNLOADS_DIR=.
```

All tests executed using this method should use the `REMOTE_CHROME` browser. Execution of tests can be viewed at
`localhost:4444`. The VNC password is `secret`.

To see how to run tests for each tool, follow the instructions in their READMEs.

* [FitNesse instructions](./fitnesse-for-appian/README.md#running-tests)
* [Cucumber instructions](./cucumber-for-appian/README.md#running-tests)
* [Selenium instructions](./appian-selenium-api/README.md#running-tests)

## Submitting Merge Requests

Before submitting an MR:

* Your patch should include new tests that cover your changes, or be accompanied by explanation for why it doesn't need
  any. It is you and your reviewer's responsibility to ensure your patch includes adequate tests.
* Your code should pass all the automated tests before you submit your PR for review.
    * See [Running the tests](#running-the-tests) above.
    * You can label pull requests as "Draft" to indicate they are not yet ready for merge.
* Your patch should include a changelog entry in CHANGELOG.md if it includes new features or breaking changes.

When submitting an MR:

* You agree to license your code under the project's open source license (APACHE 2.0).
* Base your branch off the current `main` branch.
* Add both your code and new tests if relevant.
* When MR is ready to be merged open an issue and mention the issue in your MR using the [closing pattern](https://docs.gitlab.com/user/project/issues/managing_issues/#closing-issues-automatically)
