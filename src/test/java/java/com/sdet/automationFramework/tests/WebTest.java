package java.com.sdet.automationFramework.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import static org.openqa.selenium.support.PageFactory.initElements;

@CucumberOptions(
		features = "src/test/resources/features/WebTesting/WebTesting.feature",
		glue = {"com/sdet/automationFramework/stepDefinitions/WebTesting"},
		tags = "@Regression",
		plugin = {"json:target/allure-results/index.json",
				"junit:target/allure-results/index.xml",
				"html:target/allure-results/index.html"},
		monochrome = true
)
public class WebTest extends AbstractTestNGCucumberTests {

}
