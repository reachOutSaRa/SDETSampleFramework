package com.sdet.automationFramework.stepDef;

import com.sdet.automationFramework.utilities.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks extends BaseTest {

    @Before
    public void setUp(Scenario scenario) throws Throwable {
        testResults = true;
        testExecution = System.getProperty("testExecution");
        setupJobName(scenario);
    }

    @After
    public void tearDown(Scenario scenario) throws Throwable {
        teardownSauce(scenario);
    }
}
