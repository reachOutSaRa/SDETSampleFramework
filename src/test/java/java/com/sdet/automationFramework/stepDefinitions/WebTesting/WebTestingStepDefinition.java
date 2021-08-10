package java.com.sdet.automationFramework.stepDefinitions.WebTesting;

import com.sdet.automationFramework.flows.ui.HomePageFlow;
import com.sdet.automationFramework.flows.ui.MessageFlow;
import com.sdet.automationFramework.flows.ui.RegisterFlow;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.com.sdet.automationFramework.utilities.BaseTest;
import java.com.sdet.automationFramework.utilities.EnvironmentData;

public class WebTestingStepDefinition extends BaseTest {

    private EnvironmentData testEnvironmentInterface =
            ConfigFactory.create(EnvironmentData.class);
    SoftAssert softAssert = new SoftAssert();

    HomePageFlow homePageFlow;
    MessageFlow messageFlow;
    RegisterFlow registerFlow;

    public WebTestingStepDefinition() throws Throwable {
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
    }

    @Then("Confirm registration")
    public void confirm_registration() throws InterruptedException {
        registerFlow.register();
        homePageFlow.assertConfirmationMessageAndMail();
    }

    @Then("Compose a new message")
    public void compose_a_new_message() {
        homePageFlow.goToInbox();
        messageFlow.sendMessage();
    }

    @Then("Acknowledgement shown is asserted")
    public void acknowledgement_shown_is_asserted() {
        messageFlow.assertMessageSent();
    }

    @Then("Logout")
    public void logout() {
        homePageFlow.logOut();
    }


}
