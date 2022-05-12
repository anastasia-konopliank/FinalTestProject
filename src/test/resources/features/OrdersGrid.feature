Feature:
  On the main page Grid & chart grid control can be configured.

  Background:
    Given the user opens the deltixuat.com website

  Scenario: Column Id disappears when unchecked in Filter Configurator control
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When ensured column "Id" is visible
    When Filter Configurator control is opened
    When checkbox "Id" in Filter Configurator control is unchecked
    Then column "Id" is "hidden"

  Scenario: Column Id disappears when unchecked in column 3rd tab
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When ensured column "Id" is visible
    When filter for any column is opened and switched to the 3rd tab
    When checkbox "Id" in the 3rd tab is unchecked
    Then column "Id" is "hidden"

  Scenario: Column Id disappears when unchecked in Tool panel
    Given the user is logged in
    When main page is opened
    When Grid & chart tab is opened
    When ensured column "Id" is visible
    When filter on Tool panel is opened
    When checkbox "Id" in Tool panel is unchecked
    Then column "Id" is "hidden"