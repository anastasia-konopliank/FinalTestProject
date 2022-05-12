Feature:
  On the main page Scatter-plot attributes and intervals should be changed.

  Background:
    Given the user opens the deltixuat.com website

  Scenario: Value of Y axe should be updated, name of X should be changed when X attribute changed
    Given the user is logged in
    When main page is opened
    When Scatter-plot tab is opened
    When "X" attribute changed
    Then values of "X" axe is updated
    Then name of "X" axe changed

  Scenario: Value of X should be updated and name of Y should be changed when Y attribute changed
    Given the user is logged in
    When main page is opened
    When Scatter-plot tab is opened
    When "Y" attribute changed
    Then values of "Y" axe is updated
    Then name of "Y" axe changed

  Scenario: Grid should be collapsed when Y attribute changed
    Given the user is logged in
    When main page is opened
    When Scatter-plot tab is opened
    When point is selected
    Then grid is "expanded"
    When "Y" attribute changed once
    Then grid is "collapsed"



