package com.sdet.automationFramework.page.login;

import com.sdet.automationFramework.utilities.BasePage;
import org.mortbay.jetty.SessionManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MessagePage {

    BasePage page;
    SessionManager sessionManager;

    private By composeMessageButton = By.xpath("//img[@alt='Compose message']");
    private By sendToInputBox = By.xpath("//input[@class='select2-search__field']");
    private String firstListName = "//ul/li[contains(text(),'%s')]";
    private By subjectInputBox = By.xpath("//input[@id='compose_message_title']");
    private By iframe = By.tagName("iframe");
    private By messageBody = By.xpath("//body[@contenteditable='true']");
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

        page.enterTextInFrameByTagName(iframe,messageBody,message);

}
    public void clickSendMessageButton(){
        page.clickOnElement(sendMessageButton);
    }

    public String getAlertMessage(){
        return page.getTextOfWebElement(alertMessage);
    }


 /*   public static void main(String args[]){
        System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver.exe");

        ChromeOptions opt=new ChromeOptions();

        opt.setExperimentalOption("debuggerAddress","localhost:9222 ");

        WebDriver driver=new ChromeDriver(opt);

        driver.get("http://facebook.com");
    }*/
}
