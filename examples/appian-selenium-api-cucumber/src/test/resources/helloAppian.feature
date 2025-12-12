Feature: Test Action Page
  A step definition test example.

  Scenario: verify action page "Create a Case" fields
    Given I login with credentials
    When I click on action "Create a Case"
    Then I verify fields
    Then I tear down site
