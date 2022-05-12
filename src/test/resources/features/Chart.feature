Feature:
  On the main page Grid & chart Avg Price and Mid Price should be correctly displayed.

  Background:
    Given the user opens the deltixuat.com website

  Scenario: Value of Exec should match values from other places
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When ensured column "Id" is visible
    When order is selected
    When +Lines clicked
    When ensure Avg fill price is toggled on
    When ensure Mid price is toggled on
    When +Lines clicked
    When order with executions selected
    Then the value of Exec in the tooltip should match with Avg fill price in the Interactive legend control
    And the value of Exec in the tooltip should match with Order info string
    And the value of Exec in the tooltip should match with order info in the grid

  Scenario: Color of Exec line should match colors from other places
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When ensured column "Id" is visible
    When order is selected
    When +Lines clicked
    When ensure Avg fill price is toggled on
    When ensure Mid price is toggled on
    When +Lines clicked
    When order with executions selected
    Then color of Exec line matches with text color of "Price imp. (ticks)"
    Then color of Exec line matches with text color of "Price imp. (amount)"

  Scenario: Value of Mid price should match values from other places
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When ensured column "Id" is visible
    When order is selected
    When +Lines clicked
    When ensure Avg fill price is toggled on
    When ensure Mid price is toggled on
    When +Lines clicked
    When order with executions selected
    Then the value of Mid price in the Interactive legend control matches with its value in the tooltip