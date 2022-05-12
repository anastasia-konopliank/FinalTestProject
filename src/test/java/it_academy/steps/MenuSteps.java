package it_academy.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it_academy.enums.LinesPanelItem;
import it_academy.enums.TabName;
import it_academy.pages.*;
import it_academy.utils.SmartOrderedList;
import it_academy.utils.StringListComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuSteps {
    private Logger logger = LogManager.getLogger(MenuSteps.class);

    private LoginPage loginPage = new LoginPage();
    private MainPage mainPage = new MainPage();
    private HistogramPage histogramPage = new HistogramPage();
    private ScatterPlotPage scatterPlotPage = new ScatterPlotPage();
    private GridPage gridPage = new GridPage();

    @ParameterType(".*")
    public TabName tabName(String arg) {
        var result = Arrays.stream(TabName.values()).filter(x -> arg.equalsIgnoreCase(x.getDisplayName())).findFirst();

        if (result.isEmpty()) {
            logger.warn("Cannot parse " + arg + ", use first enum value as a fallback.");

            return TabName.SUMMARY;
        }

        return result.get();
    }


    @ParameterType(".*")
    public LinesPanelItem linesPanelItem(String arg) {
        var result = Arrays.stream(LinesPanelItem.values()).filter(x -> arg.equalsIgnoreCase(x.getDisplayName())).findFirst();

        if (result.isEmpty()) {
            logger.warn("Cannot parse " + arg + ", use first enum value as a fallback.");

            return LinesPanelItem.AVG_FILL_PRICE;
        }

        return result.get();
    }

    @Given("the user opens the deltixuat.com website")
    public void theUserOpensTheDeltixuatComWebsite() {
        loginPage.open();
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        loginPage.setUsername();
        loginPage.setPassword();
        loginPage.clickToLoginButton();
    }

    @When("main page is opened")
    public void mainPageIsOpened() throws Exception {
        mainPage.ensureOpened();
    }

    @Then("settings button is displayed")
    public void settingsButtonIsDisplayed() {
        Assert.assertTrue("Settings button expected to be displayed.", mainPage.isSettingIconDisplayed());
    }

    @Then("benchmark selector control is displayed")
    public void benchmarkSelectorControlIsDisplayed() {
        Assert.assertTrue("Benchmark expected to be displayed.", mainPage.isBenchmarkDisplayed());
    }

    @Then("toolbar with {tabName} is displayed")
    public void toolbarWithIsDisplayed(TabName tabName) {
        Assert.assertTrue(tabName + " expected to be displayed.", mainPage.getTab(tabName).isDisplayed());
    }

    @When("{tabName} tab is opened")
    public void tabIsOpened(TabName tabName) {
        mainPage.getTab(tabName).click();
        logger.info("Tab " + tabName + " is opened.");
    }

    @Then("for each bar corresponding boundary should be displayed")
    public void forEachBarCorrespondingBoundaryShouldBeDisplayed() {
        for (var bar : histogramPage.getBars()) {
            bar.hover();
            Assert.assertTrue("Boundary should be displayed for " + bar.getNumber(), histogramPage.isBoundaryDisplayed());
        }
    }

    @Then("for each bar corresponding number of order should be displayed")
    public void forEachBarCorrespondingNumberOfOrderShouldBeDisplayed() {
        for (var bar : histogramPage.getBars()) {
            bar.hover();
            Assert.assertTrue("Number of orders should be displayed for " + bar.getNumber(), histogramPage.isNumberOfOrdersDisplayed());
        }
    }

    @When("{string} attribute changed")
    public void attributeChanged(String axe) {
        scatterPlotPage.cleanSnapshots();
        scatterPlotPage.takePageSnapshot();
        for (var attribute : scatterPlotPage.getListOfAttributes(axe)) {
            scatterPlotPage.changeAttribute(axe, attribute);
            scatterPlotPage.takePageSnapshot();
        }
    }

    @When("{string} attribute changed once")
    public void attributeChangedOnce(String axe) {
        scatterPlotPage.cleanSnapshots();
        scatterPlotPage.takePageSnapshot();
        var firstAttribute = scatterPlotPage.getListOfAttributes(axe)[0];
        scatterPlotPage.changeAttribute(axe, firstAttribute);
    }

    @Then("name of {string} axe changed")
    public void nameOfAxeEqualsToAttributeName(String axe) {
        for (var action : scatterPlotPage.getPageHistory()) {
            Assert.assertEquals(action.getDifference() + " does not changed axe names", action.getAfter().getNameOf(axe), action.getAfter().getAttribute(axe));
        }
    }

    @Then("grid is {string}")
    public void gridIsInState(String gridState) {
        if (gridState.equalsIgnoreCase("collapsed")) {
            Assert.assertFalse("Grid should be collapsed", scatterPlotPage.isGridDisplayed());
        } else {
            Assert.assertTrue("Grid should be expanded", scatterPlotPage.isGridDisplayed());
        }
    }

    @Then("values of {string} axe is updated")
    public void valuesOfAxeIsUpdated(String axe) {
        var changedTimes = Arrays.stream(scatterPlotPage.getPageHistory())
                .map(x -> x.getAfter().getValue(axe))
                .distinct()
                .count();

        logger.info("Axe changed " + changedTimes + " out of " + scatterPlotPage.getPageHistory().length + " changes.");

        Assert.assertTrue("Axe " + axe + " should be changed when attribute changed at least for some attributes", changedTimes > 1);
    }

    @When("order is selected")
    public void orderIsSelected() {
        gridPage.clickOnAnyCellWithExecutions();
    }

    @When("+Lines clicked")
    public void linesClicked() {
        gridPage.clickOnPlusLines();
    }

    @When("ensure {linesPanelItem} is toggled on")
    public void ensureLineIsToggledOn(LinesPanelItem linesPanelItem) {
        if (!gridPage.isSwitcherSelected(linesPanelItem)) {
            gridPage.toggle(linesPanelItem);
        }
    }

    @When("order with executions selected")
    public void orderWithExecutionsSelected() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the value of Exec in the tooltip should match with Avg fill price in the Interactive legend control")
    public void theValueOfExecInTheTooltipShouldMatchWithAvgFillPriceInTheInteractiveLegendControl() {
        var valueOfExec = gridPage.getValueOfExecInTheTooltip();
        var avgFillPriceInTheInteractiveLegend = gridPage.getAvgFillPriceInTheInteractiveLegend();

        Assert.assertEquals("Value of Exec should match Avg fill price", valueOfExec, avgFillPriceInTheInteractiveLegend, 0.00001);
    }

    @And("the value of Exec in the tooltip should match with Order info string")
    public void theValueOfExecInTheTooltipShouldMatchWithOrderInfoString() {
        var valueOfExec = gridPage.getValueOfExecInTheTooltip();
        var orderInfo = gridPage.getOrderInfo();

        Assert.assertEquals("Value of Exec should match order info value", valueOfExec, orderInfo, 0.00001);
    }

    @And("the value of Exec in the tooltip should match with order info in the grid")
    public void theValueOfExecInTheTooltipShouldMatchWithOrderInfoInTheGrid() {
        var valueOfExec = gridPage.getValueOfExecInTheTooltip();
        var currentOrderInfoFromGrid = gridPage.getCurrentOrderInfoFromGrid();

        Assert.assertEquals("Value of Exec should match current order info value from grid", valueOfExec, currentOrderInfoFromGrid, 0.00001);
    }

    @Then("the value of Mid price in the Interactive legend control matches with its value in the tooltip")
    public void theValueOfMidPriceInTheInteractiveLegendControlMatchesWithItsValueInTheTooltip() {
        var fromLegend = gridPage.getValueOfMidInTheInteractiveLegend();
        var fromTooltip = gridPage.getValueOfMidInTheTooltip();

        Assert.assertEquals("Value of Mid from legend should match value of Mid from tooltip", fromLegend, fromTooltip, 0.00001);
    }

    @Then("color of Exec line matches with text color of {string}")
    public void colorOfExecLineMatchesWithTextColorOf(String element) {
        var colorOfExec = gridPage.getColorOfExecLine();
        var colorOfElement = gridPage.getColorOf(element);

        Assert.assertEquals("Colors should be same", colorOfExec, colorOfElement);
    }

    @Then("column {string} is {string}")
    public void columnIs(String columnName, String state) {
        // todo: replace column name and state with enums
        var isVisible = gridPage.isColumnVisible(columnName);

        if (state.equals("visible")) {
            Assert.assertTrue("Column " + columnName + " is expected to be visible", isVisible);
        } else {
            Assert.assertFalse("Column " + columnName + " is expected to be hidden", isVisible);
        }
    }

    @When("Filter Configurator control is opened")
    public void filterConfiguratorControlIsOpened() {
        mainPage.openFilters();
    }

    @When("filter for any column is opened and switched to the 3rd tab")
    public void filterForAnyColumnIsOpenedAndSwitchedToTheThirdTab() {
        gridPage.openAnyTabFilter();
        gridPage.selectThirdTab();
    }

    @When("column {string} is clicked")
    public void columnIsClicked(String columnName) {
        // todo: enum
        gridPage.clickOn(columnName);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("column {string} items is {string}")
    public void columnItemsIs(String columnName, String orderingRule) {
        // todo: enum
        var items = gridPage.getItems(columnName);
        var sorted = new SmartOrderedList(items).order();

        if (orderingRule.equals("ordered ascended")) {
            var c = new StringListComparator(items, sorted);
            Assert.assertTrue("Should be ordered" + c.getReason(), c.areSameByContent());
        } else if (orderingRule.equals("ordered descended")) {
            var c = new StringListComparator(items, reverseArray(sorted));
            Assert.assertTrue("Should be ordered desc. " + c.getReason(), c.areSameByContent());
        } else {
            var c = new StringListComparator(items, reverseArray(sorted));
            Assert.assertFalse("Ordered desc, but should not", c.areSameByContent());
            var c2 = new StringListComparator(items, sorted);
            Assert.assertFalse("Ordered, but should not", c2.areSameByContent());
        }
    }

    // todo: move to utils
    private static String[] reverseArray(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);
        String[] reversedArray = list.toArray(arr);
        return reversedArray;
    }

    @When("point is selected")
    public void pointIsSelected() {
        scatterPlotPage.clickOnPoint();
    }

    @When("filter on Tool panel is opened")
    public void filterOnToolPanelIsOpened() {
        gridPage.clickOnTheFilterOnToolPanel();
    }

    @Then("column {string} in Filter Configurator control is {string}")
    public void columnInFilterConfiguratorControlIs(String columnName, String state) {
        // todo: replace column name and state with enums
        var isVisible = gridPage.isColumnInFilterConfiguratorControlVisible(columnName);

        if (state.equals("visible")) {
            Assert.assertTrue("Column " + columnName + " is expected to be visible", isVisible);
        } else {
            Assert.assertFalse("Column " + columnName + " is expected to be hidden", isVisible);
        }
    }

    @When("checkbox {string} in Filter Configurator control is unchecked")
    public void checkboxInFilterConfiguratorControlIsUnchecked(String checkboxName) {
        // todo: use enums for column name
        mainPage.clickOnCheckboxInFilterConfigurationControl(checkboxName);
    }

    @When("checkbox {string} in the 3rd tab is unchecked")
    public void checkboxInTheThirdTabIsUnchecked(String checkboxName) {
        // todo: use enums for column name
        gridPage.clickOnCheckboxInTheThirdTab(checkboxName);
    }

    @When("checkbox {string} in Tool panel is unchecked")
    public void checkboxInToolPanelIsUnchecked(String checkboxName) {
        // todo: use enums for column name
        gridPage.uncheckCheckboxInToolPanel(checkboxName);
    }

    @When("ensured column {string} is visible")
    public void makeSureColumnIsVisible(String columnName) {
        if (!gridPage.isColumnVisible(columnName)) {

            logger.warn(columnName + " is invisible, make it visible as test precondition.");

            gridPage.clickOnTheFilterOnToolPanel();
            gridPage.checkCheckboxInToolPanel(columnName);
            gridPage.clickOnTheFilterOnToolPanel();
        }
    }
}
