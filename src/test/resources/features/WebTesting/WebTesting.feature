@FunctionalTest @known @ui
Feature: OysterPlus UI

  @severity=blocker @Regression @Sanity @trail
  Scenario: Test the creation of BR by manual option
    Given Launch ELearning UI
    And SignUp
    Then Confirm registration
    And Compose a new message
    Then Acknowledgement shown is asserted
    And Logout


