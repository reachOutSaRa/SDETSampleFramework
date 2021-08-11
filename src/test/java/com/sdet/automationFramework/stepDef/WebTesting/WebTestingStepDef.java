package com.sdet.automationFramework.stepDef.WebTesting;

import com.sdet.automationFramework.flows.ui.HomePageFlow;
import com.sdet.automationFramework.flows.ui.MessageFlow;
import com.sdet.automationFramework.flows.ui.RegisterFlow;
import com.sdet.automationFramework.utilities.BaseTest;
import com.sdet.automationFramework.utilities.EnvironmentData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;


public class WebTestingStepDef extends BaseTest {

    SoftAssert softAssert = new SoftAssert();
    HomePageFlow homePageFlow;
    MessageFlow messageFlow;
    RegisterFlow registerFlow;
    private EnvironmentData testEnvironmentInterface =
            ConfigFactory.create(EnvironmentData.class);

    public WebTestingStepDef() throws Throwable {
        WebDriver driver = BaseTest.connection(testEnvironmentInterface.platform(),
                testEnvironmentInterface.browserName(),
                testEnvironmentInterface.version(),
                "ELearning UI",
                testEnvironmentInterface.execution(),
                testEnvironmentInterface.webURL());

        homePageFlow = new HomePageFlow(driver);
        messageFlow = new MessageFlow(driver);
        registerFlow = new RegisterFlow(driver);
    }

    @Given("Launch ELearning UI")
    public void launch_e_learning_ui() throws InterruptedException {
        System.out.println("URL is Launched");
    }

    @Given("SignUp")
    public void sign_up() throws InterruptedException {
        homePageFlow.signUp();
        System.out.println("Launched URL");
    }

    @Then("Confirm registration")
    public void confirm_registration() throws InterruptedException {
        registerFlow.register();
        homePageFlow.assertConfirmationMessageAndMail();
        System.out.println("Registered and confirmation message asserted");
    }

    @Then("Compose a new message")
    public void compose_a_new_message() {
        homePageFlow.goToInbox();
        messageFlow.sendMessage();
        System.out.println("Composed Message");
    }

    @Then("Acknowledgement shown is asserted")
    public void acknowledgement_shown_is_asserted() {
        messageFlow.assertMessageSent();
        System.out.println("Message sent Alert asserted");
    }

    @Then("Logout")
    public void logout() {
        homePageFlow.logOut();
        System.out.println("Logged out of application");
    }


}
