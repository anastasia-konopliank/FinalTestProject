Feature:
  On the main page histogram view should react on hover.

  Background:
    Given the user opens the deltixuat.com website

  Scenario: Boundaries should be displayed for each bar
    Given the user is logged in
    When main page is opened
    When Histogram tab is opened
    Then for each bar corresponding boundary should be displayed

  Scenario: Number of order should be displayed for each bar
    Given the user is logged in
    When main page is opened
    When Histogram tab is opened
    Then for each bar corresponding number of order should be displayed

