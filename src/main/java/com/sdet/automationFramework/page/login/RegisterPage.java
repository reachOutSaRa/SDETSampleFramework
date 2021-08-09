package com.sdet.automationFramework.page.login;

import com.sdet.automationFramework.utilities.BasePage;
import org.mortbay.jetty.SessionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    BasePage page;
    SessionManager sessionManager;

    private By firstNameInputBox = By.xpath("//input[@id='registration_firstname']");
    private By lastNameInputBox = By.xpath("//input[@id='registration_lastname']");
    private By emailInputBox = By.xpath("//input[@id='registration_email']");
    private By userNameInputBox = By.xpath("//input[@id='username']");
    private By passwordInputBox = By.xpath("//input[@id='pass1']");
    private By confirmationPasswordInputBox = By.xpath("//input[@id='pass2']");
    private By registerButton = By.xpath("//button[@id='registration_submit']");

    public RegisterPage(WebDriver driver) {
        this.page = new BasePage(driver);

    }

    public void enterFirstName(String firstName) {
        page.enterTextInField(firstNameInputBox, firstName);
    }

    public void enterLastName(String lastName) {
        page.enterTextInField(lastNameInputBox, lastName);
    }

    public void enterEmail(String email) {
        page.enterTextInField(emailInputBox, email);
    }

    public void enterUserName(String userName) {
        page.enterTextInField(userNameInputBox, userName);
    }

    public void enterPassword(String password) {
        page.enterTextInField(passwordInputBox, password);
    }

    public void enterConfirmationPassword(String password) {
        page.enterTextInField(confirmationPasswordInputBox, password);
    }

    public void clickRegisterButton() {
        page.clickOnElement(registerButton);
    }

}
