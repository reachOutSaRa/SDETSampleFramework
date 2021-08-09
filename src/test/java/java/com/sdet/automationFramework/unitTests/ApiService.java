package java.com.sdet.automationFramework.unitTests;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com/axp/automationFramework/stepDefinitions"},
        tags = "@Regression",
        plugin = {"json:target/allure-results/report.json",
                "junit:target/allure-results/report.xml",
                "html:target/allure-results/report.html"},
        monochrome = true
)
public class ApiService extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}