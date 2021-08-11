@FunctionalTest @known
Feature: REST Service Case Study TestCases - TC_001, TC_002

  @severity=blocker @Regression @Sanity @trail
  Scenario: To verify whether application allows the admin to get access token
    Given Call get access token api endpoint with status code "200"
    Then Get the access token
    And Call login admin user api endpoint provided with valid admin user request body with status code "200"
    And Call get admin user account details api endpoint with status code "200"
    And Call logout admin user api endpoint with status code "200"
