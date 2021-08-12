package com.sdet.automationFramework.flows.ui;

import com.sdet.automationFramework.page.login.MessagePage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class MessageFlow {

    SoftAssert softAssert = new SoftAssert();
    MessagePage messagePage;
    WebDriver driver;

    public MessageFlow(WebDriver driver) {
        this.messagePage = new MessagePage(driver);
        this.driver = driver;
    }

    /**
     * Function to send Message
     */
    public void sendMessage(){
        messagePage.clickComposeMessage();
        messagePage.enterSendTo("naveen");
        messagePage.enterSubject("Test");
        messagePage.enterMessageBody("Test Automation Trail");
        messagePage.clickSendMessageButton();
    }

    public void assertMessageSent(){
        softAssert.assertEquals(
                "The message has been sent to naveen naveen (naveen)",
                messagePage.getAlertMessage(),
                "Message is not as expected"
        );
    }
}
