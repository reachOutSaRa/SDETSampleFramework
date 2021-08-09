package com.sdet.automationFramework.flows.ui;

import com.axp.automationFramework.page.login.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class RegisterFlow {

    SoftAssert softAssert = new SoftAssert();
    RegisterPage registerPage;
    WebDriver driver;

    public RegisterFlow() {
    }

    public RegisterFlow(WebDriver driver) {
        this.registerPage = new RegisterPage(driver);
        this.driver = driver;
    }

    /**
     * Function to Register
     */
    public void register() throws InterruptedException {
        registerPage.enterFirstName("Saranya");
        registerPage.enterLastName("Rajagopalan");
        registerPage.enterEmail("reachoutsara@gmail.com");
        registerPage.enterUserName("saraTest"+Math.random());
        registerPage.enterPassword("Au6[+wYv");
        registerPage.enterConfirmationPassword("Au6[+wYv");
        registerPage.clickRegisterButton();
    }

}
