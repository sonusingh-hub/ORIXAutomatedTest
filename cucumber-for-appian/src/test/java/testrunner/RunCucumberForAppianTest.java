package testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
        glue = {"com.appiancorp.ps.cucumber", "com.appiancorp.ps.automatedtest.fixture"},
        plugin = {
        "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        tags = "@End2End and not @Regression"
)
public class RunCucumberForAppianTest {
}
