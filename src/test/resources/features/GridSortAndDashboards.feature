Feature:
  On the main page Grid & chart grid control can be sorted.

  Background:
    Given the user opens the deltixuat.com website

  Scenario Outline: Values in column <column> should be sorted correctly after click
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When column "<column>" is clicked
    Then column "<column>" items is "ordered ascended"
    When column "<column>" is clicked
    Then column "<column>" items is "ordered descended"
    When column "<column>" is clicked
    Then column "<column>" items is "not ordered"

    Examples:
    | column |
    | Avg fill price |
    | Start time   |
    | Type   |