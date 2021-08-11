package com.sdet.automationFramework.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/RestServiceTesting/RestServiceTesting.feature",
        glue = {"com/sdet/automationFramework/stepDef/RestServiceTesting"},
        tags = "@Regression",
        plugin = {"json:target/allure-results/index.json",
                "junit:target/allure-results/index.xml",
                "html:target/allure-results/index.html"},
        monochrome = true
)
public class RestServiceTest extends AbstractTestNGCucumberTests {
/*
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }*/

}