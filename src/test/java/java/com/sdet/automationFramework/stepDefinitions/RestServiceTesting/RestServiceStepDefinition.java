package java.com.sdet.automationFramework.stepDefinitions.RestServiceTesting;/*
package com.axp.automationFramework.stepDefinitions.RestServiceTesting;

import com.axp.automationFramework.flows.MABAllocationAlgorithm.MABAllocationAlgorithmFlow;
import com.axp.automationFramework.services.MABAllocationAlgorithm.api.RestApi;
import com.axp.automationFramework.services.MABAllocationAlgorithm.model.request.RequestInstance;
import com.axp.automationFramework.services.MABAllocationAlgorithm.model.response.Response;
import com.axp.automationFramework.utilities.EnvironmentInterface;
import com.axp.automationFramework.utilities.PortForwardingL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;

public class RestServiceStepDefinition {


    private EnvironmentInterface testEnvironmentInterface =
            ConfigFactory.create(EnvironmentInterface.class);
    private Session session;

    public PortForwardingL portForwardingL = new PortForwardingL();
    public RestApi mabApi = new RestApi(
            testEnvironmentInterface.mabAllocationURL());
    public RequestInstance dataInstance = new RequestInstance();
    public Response mabResponse = new Response();
    public MABAllocationAlgorithmFlow mabAllocationAlgorithmFlow = new MABAllocationAlgorithmFlow();

    public RestServiceStepDefinition() throws JSchException {

    }

    @Given("Open a session to forward port to ZeusDev")
    public void openSessionToForwardPortToZeusDev() throws InterruptedException, JSchException {
        session = portForwardingL.openSessionToForwardPortToZeusDev(
                testEnvironmentInterface.lport(),
                testEnvironmentInterface.rhost(),
                testEnvironmentInterface.rport(),
                testEnvironmentInterface.ZeusDevUserName(),
                testEnvironmentInterface.ZeusDevPassword(),
                testEnvironmentInterface.ZeusDevHost()
        );
    }

    @And("For MAB Allocation Algorithm Request {string}")
    public void getMABAllocationAlgorithmRequest(String scenarioName) throws Exception {
        dataInstance = new RequestInstance(scenarioName);
        Allure.addAttachment("MAB Allocation Algorithm Request","MAB Request is build successfully");
    }

    @When("Post the MAB Allocation Algorithm request")
    public void postMABRequest() throws JsonProcessingException {

        mabResponse = mabApi.getMABResponse(dataInstance.getMabRequest());
        Allure.addAttachment("MAB Allocation Algorithm Response","MAB Response is successful");
    }

    @Then("Close the ZeusDev session")
    public void closeZeusDevSession() {
        portForwardingL.closeSession(session);
    }

    @And("Validate MAB Allocation Algorithm response {string}")
    public void validateMABResponse(String scenarioName) throws Exception {
        mabAllocationAlgorithmFlow.assertMABAllocationAlgorithmResponse(
                mabResponse, mabAllocationAlgorithmFlow.getMABAllocationAlgorithmExpectedResponse(
                        scenarioName));
    }

}
*/
