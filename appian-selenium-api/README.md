# Running Selenium API tests

1. Set `url` environment variable to the URL of the site you are testing against
2. Set `version` environment variable to the version of Appian running on the site

```bash
export url=https://appian.com/suite
export version=25.2
```

3. In terminal, run `./gradlew setup` from the [root directory](..) of this repository.
4. Navigate to [
   `appian-selenium-api/src/test/java/com/appiancorp/ps/automatedtest/test/AbstractTest.java`](./src/test/java/com/appiancorp/ps/automatedtest/test/AbstractTest.java):
    1. Configure the users.
        1. To use existing users on your site, update the usernames and passwords (i.e. `TEST_USERNAME`,
           `ADMIN_USERNAME`, ...) to users available on your testing site.
        2. Then put the updated values in [
           `shared-properties/configs/users.properties`](../shared-properties/configs/users.properties). Alternatively,
           create the users fitnesse.user and fitnesse.admin on your site, with the passwords specified in the config
           file.
4. If you want to test on a **Automated Testing - xx.x.zip** app in `/apps`, install the testing app using the following steps:
    1. Import the corresponding **Automated Testing - xx.x.zip** from the `../apps` folder of this repo to a site.
    2. Open the Data Store **AUT_DS**, click **VERIFY** and **SAVE & PUBLISH**.
    3. Navigate to `/suite/tempo/actions`, run action **Initialize**.
5. Now you can run the tests in [`appian-selenium-api/src/test`](./src/test) directory. For example, you can use the IntelliJ IDEA IDE to run the tests directly.
