package com.sdet.automationFramework.page.login;

import com.sdet.automationFramework.utilities.BasePage;
import org.mortbay.jetty.SessionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MessagePage {

    BasePage page;
    SessionManager sessionManager;

    private By composeMessageButton = By.xpath("//img[@alt='Compose message']");
    private By sendToInputBox = By.xpath("//input[@class='select2-search__field']");
    private String firstListName = "//ul/li[contains(text(),'%s')]";
    private By subjectInputBox = By.xpath("//input[@id='compose_message_title']");
    private By messageFrame = By.xpath("//body/p");
    private By sendMessageButton = By.xpath("//button[@id='compose_message_compose']");
    private By alertMessage = By.xpath("//div[@class='alert alert-success']");

    public MessagePage(WebDriver driver) {
        this.page = new BasePage(driver);
    }


    public void clickComposeMessage(){
        page.clickOnElement(composeMessageButton);
    }

    public void enterSendTo(String sendTo){
        page.enterTextInField(sendToInputBox,sendTo);
        page.clickOnElement(By.xpath(String.format(firstListName,"naveen naveen (naveen)")));
    }

    public void enterSubject(String subject){
        page.enterTextInField(subjectInputBox,subject);
    }

    public void enterMessageBody(String message){
       // page.clickOnElement(messageFrame);
        page.enterTextInsideFrame(messageFrame,message);

}
    public void clickSendMessageButton(){
        page.clickOnElement(sendMessageButton);
    }

    public String getAlertMessage(){
        return page.getTextOfWebElement(alertMessage);
    }
}
