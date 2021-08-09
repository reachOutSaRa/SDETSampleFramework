package com.sdet.automationFramework.flows.ui;

import com.sdet.automationFramework.page.login.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.PageFactory.initElements;

public class HomePageFlow {

    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    WebDriver driver;

    public HomePageFlow() {
    }

    public HomePageFlow(WebDriver driver) {
        this.homePage = new HomePage(driver);
        this.driver = driver;
    }

    /**
     * Initialize Login page
     */
    public void initializePage() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        homePage = initElements(driver, HomePage.class);
    }

    /**
     * Function to Sign Up and Register
     */
    public void signUp() throws InterruptedException {

        initializePage();
        homePage.clickSignUpButton();
    }

    /**
     * Function to get confirmation message and assert
     */
    public void assertConfirmationMessageAndMail() {
        softAssert.assertEquals(
                "Dear Saranya Rajagopalan,\n\nYour personal settings have been registered.",
                homePage.getConfirmationMessage(),
                "Confirmation Name is not as expected"
        );

        homePage.clickDropDown();

        softAssert.assertEquals(
                "reachoutsara@gmail.com",
                homePage.getEmailFromDropDown(),
                "Email is not registered as expected");
    }

    /**
     * Function to Log Out
     */
    public void goToInbox() {
        homePage.clickInbox();
    }

    /**
     * Function to Log Out
     */
    public void logOut() {
        homePage.clickDropDown();
        homePage.clickLogOut();
        driver.quit();
        softAssert.assertAll();
    }


}
