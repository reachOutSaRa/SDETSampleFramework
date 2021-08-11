@FunctionalTest @known
Feature: REST Service Case Study TestCases - TC_001, TC_002

  @severity=blocker @Regression @Sanity @trail
  Scenario: To verify whether application allows the admin to get access token
    Given Call get access token api endpoint
    Then Assert status code "200"
    And Call login admin user api endpoint
    Then Provide valid admin user request body
    And Assert status code "200"
    Then Call get admin user account details api endpoint
    And Assert status code "200"
    Then Call logout admin user api endpoint
    And Assert status code "200"
