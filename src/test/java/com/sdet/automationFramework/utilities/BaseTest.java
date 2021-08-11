package com.sdet.automationFramework.utilities;

import io.cucumber.java.Scenario;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base Test
 *
 * @author SaRa
 * @lastModifiedBy SaRa
 * @lastModifiedDate 20210630
 */
public class BaseTest {

	/************************************************************************
	 * Sauce lab connection
	 *************************************************************************/
	public static final String USERNAME = System.getenv("SAUCE_USER_NAME");
	public static final String ACCESS_KEY = System.getenv("SAUCE_API_KEY");
	public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "sauceserver";
	public static boolean testResults;
	public static String jobName;
	public static String testExecution;
	public static WebDriver driver;
	public static String sessionId;

	/**
	 * Fucntion to instantiate WebDriver
	 *
	 * @param platform
	 * @param browserName
	 * @param version
	 * @param jobName1
	 * @param execution
	 * @param url
	 * @return
	 * @throws Throwable
	 * @author SaRa
	 * @lastModifiedBy SaRa
	 * @lastModifiedDate 20210709
	 */
	public static WebDriver connection(String platform, String browserName, String version,
									   String jobName1, String execution, String url) throws Throwable {

		if (testExecution != null && testExecution.equalsIgnoreCase("Sauce")) {
			System.out.println("in Sauce Labs function");
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platform", platform);
			caps.setCapability("browserName", browserName);
			caps.setCapability("version", version);
			jobName = jobName + "_" + platform + "_" + browserName + "_" + version;
			caps.setCapability("name", jobName);
			caps.setCapability("name", "Remote File Upload Test");

			driver = new RemoteWebDriver(new URL(URL), caps);
			LocalFileDetector localFileDetector = new LocalFileDetector();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else {

			System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

	/**
	 * Function to get the Job Name from Scenario
	 *
	 * @param scenario
	 * @author SaRa
	 * @lastModifiedBy SaRa
	 * @lastModifiedDate 20210709
	 */
	public static void setupJobName(Scenario scenario) {

		jobName = scenario.getName();
		System.out.println("Scenario Name =" + jobName);
	}

	/**
	 * Function to tear down existing instances and update results
	 *
	 * @param scenario
	 * @throws JSONException
	 * @author SaRa
	 * @lastModifiedBy SaRa
	 * @lastModifiedDate 20210709
	 */
	public void teardownSauce(Scenario scenario) throws JSONException {


		if (testExecution != null && testExecution.equals("Sauce")) {
			sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
			UpdateResults(!scenario.isFailed());
			System.out.println("SauceOnDemandSessionID=" + sessionId + "job-name=" + jobName);
		}

	}

	/**
	 * Function to update results
	 *
	 * @param testResults
	 * @throws JSONException
	 * @author SaRa
	 * @lastModifiedBy SaRa
	 * @lastModifiedDate 20210709
	 */
	public void UpdateResults(boolean testResults) throws JSONException {
		Map<String, Object> updates = new HashMap<String, Object>();
		updates.put("passed", testResults);

	}


}