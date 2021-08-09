package com.sdet.automationFramework.page.login;
import com.sdet.automationFramework.utilities.BasePage;
import org.mortbay.jetty.SessionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

	BasePage page;
	SessionManager sessionManager;

	private By signUpButton = By.xpath("//a[contains(text(),' Sign up! ')]");
	private By confirmationMessage = By.xpath("//div[@class='row']//div/p[1]");
	private By dropDown = By.xpath("//span[@class='caret']");
	private By emailFromDropDown = By.xpath("//div[@class='text-center']//i/parent::p");
	private By inboxLink = By.xpath("//a[@title='Inbox']");
	private By logOutLink = By.xpath("//a[@id='logout_button']");


	public HomePage(WebDriver driver) {
		this.page = new BasePage(driver);
	}

	public void clickSignUpButton(){
		page.clickOnElement(signUpButton);
	}

	public String getConfirmationMessage(){
		return page.getTextOfWebElement(confirmationMessage);
	}

	public void clickDropDown(){
		page.clickOnElement(dropDown);
	}

	public String getEmailFromDropDown(){
		return page.getTextOfWebElement(emailFromDropDown);
	}

	public void clickInbox(){
		page.clickOnElement(inboxLink);
	}

	public void clickLogOut(){
		page.clickOnElement(logOutLink);
	}

}
