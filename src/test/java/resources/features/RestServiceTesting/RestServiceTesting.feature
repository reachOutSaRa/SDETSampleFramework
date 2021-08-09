@FunctionalTest @known
Feature: OysterPlus MGM Regression Suite

  @severity=blocker @Regression @Sanity @trail
  Scenario: Test the response is successful for MGM request
    Given For MGM Request "Basic_MGM_Response"
    When Post the PZN request
    Then Validate response "Basic_MGM_Response"

  @severity=critical @Regression @Sanity
  Scenario Outline: Test the response with eligibility flag as 1
    Given For MGM Request "<request>"
    #And update BR "Response_With_EligibilityFlag_As_1"
    When Post the PZN request
    Then Validate response "<response>"
    Then Validate hive Variables "<hiveVariables>"


    Examples:
      |request              | response                            | hiveVariables                         |
      |Basic_MGM_Response   | Response_With_EligibilityFlag_As_1  | Response_With_EligibilityFlag_As_1    |
      |Basic_MGM_Response   | Response_With_EligibilityFlag_As_0  | Response_With_EligibilityFlag_As_0    |
