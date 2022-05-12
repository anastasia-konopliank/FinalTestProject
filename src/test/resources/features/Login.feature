Feature:
  On the main page main components should be presented.

  Background:
    Given the user opens the deltixuat.com website

  Scenario: Settings button on main page is displayed
    Given the user is logged in
    When main page is opened
    Then settings button is displayed

  Scenario: Benchmark selector control is displayed
    Given the user is logged in
    When main page is opened
    Then benchmark selector control is displayed

  Scenario Outline: Toolbar with <tab> is displayed
    Given the user is logged in
    When main page is opened
    Then toolbar with <tab> is displayed
    Examples:
    | tab          |
    | Summary      |
    | Grid & chart |
    | Histogram    |
    | Scatter-plot |
    | Reports      |

