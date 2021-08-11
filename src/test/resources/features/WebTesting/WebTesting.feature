@FunctionalTest @known @ui
Feature: ELearning Case Study

  @severity=blocker @Regression @Sanity @trail
  Scenario: Test sign up and compose message
    Given Launch ELearning UI
    And SignUp
    Then Confirm registration
    And Compose a new message
    Then Acknowledgement shown is asserted
    And Logout


