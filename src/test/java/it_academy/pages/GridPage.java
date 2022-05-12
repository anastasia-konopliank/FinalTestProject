package it_academy.pages;

import it_academy.driver.DriverManager;
import it_academy.enums.LinesPanelItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.awt.*;
import java.util.Arrays;

public class GridPage {
    private final Logger logger = LogManager.getLogger(GridPage.class);
    private final WebDriver driver;
    private String idOfSelectedOrder;

    public GridPage() {
        driver = DriverManager.getDriver();
    }

    public void scrollTableRight() {
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('ag-body-horizontal-scroll-viewport')[0].scrollBy(200, 0)");
    }

    public void scrollTableLeft() {
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('ag-body-horizontal-scroll-viewport')[0].scroll(0, 0)");
    }

    public void clickOnAnyCellWithExecutions() {

        var index = 1;

        for (var attempt = 0; attempt < 100; attempt++) {
            try {
                var webElement = driver.findElement(By.cssSelector("[row-index='" + index + "'] [col-id='numberOfExecutions']"));

                var numberOfExecutions = webElement.getText();
                if (numberOfExecutions.isEmpty() || Integer.parseInt(numberOfExecutions) < 1) {
                    index++;
                    continue;
                }
            } catch (WebDriverException exception) {
                scrollTableRight();
                continue;
            }

            break;
        }

        driver.findElement(By.cssSelector("[row-index='" + index + "'] [col-id='numberOfExecutions']")).click();

        scrollTableLeft();

        logger.info("Order clicked at row " + index);

        idOfSelectedOrder = driver.findElement(By.cssSelector(".ag-row-selected [col-id='orderId'] span")).getText();
        logger.info("Id of selected order is " + idOfSelectedOrder);
    }

    public void clickOnPlusLines() {

        driver.findElement(By.xpath("//div[contains(@class, 'chart-settings mr-1')]")).click();
        logger.info("+Lines clicked.");
    }

    public boolean isSwitcherSelected(LinesPanelItem linesPanelItem) {
        // todo: log
        return driver.findElement(By.xpath("//span[contains(text(), '" + linesPanelItem.getDisplayName() + "')]/preceding-sibling::label[contains(@class, 'switch')]/input")).isSelected();
    }

    public void toggle(LinesPanelItem linesPanelItem) {
        driver.findElement(By.xpath("//span[contains(text(), '" + linesPanelItem.getDisplayName() + "')]/preceding-sibling::label[contains(@class, 'switch')]/span")).click();
        logger.info(linesPanelItem + " toggled.");
    }

    public double getValueOfExecInTheTooltip() {

        var text = driver.findElement(By.xpath("//div[contains(@class, 'chart-view__info mb-2 hidden-text')]/div")).getText();

        var splitByComma = text.replaceAll("&nbsp;", "").split(",");

        var execPrice = Arrays.stream(splitByComma).filter(x -> x.toLowerCase().trim().startsWith("exec price")).findFirst().get();

        var splitedPrice = execPrice.split(":");

        var result = Double.parseDouble(splitedPrice[1].trim());

        return result;
    }

    public double getValueOfMidInTheTooltip() {

        var text = driver.findElement(By.cssSelector("g.dc-y-axis-label-container.dc-y-axis-label-container-MID_PRICE text:last-child")).getText();

        logger.info("Value of mid in the tooltip: " + text);

        var result = Double.parseDouble(text);

        return result;
    }

    public double getAvgFillPriceInTheInteractiveLegend() {

        var text = driver.findElement(By.xpath("//span[contains(text(), 'Avg fill price')]/ancestor::div[contains(@class, 'd-flex align-items-center')]/div[contains(@class, 'legend-container__item-value')]")).getText();

        logger.info("Value of exec in the tooltip: " + text);

        var result = Double.parseDouble(text);

        return result;
    }

    public double getOrderInfo() {
        var text = driver.findElement(By.cssSelector("g.dc-y-axis-label-container.dc-y-axis-label-container-AVERAGE_EXECUTION_PRICE text")).getText();

        var result = Double.parseDouble(text);

        return result;
    }

    public double getCurrentOrderInfoFromGrid() {
        var text = driver.findElement(By.xpath("//div[contains(@row-id, '" + idOfSelectedOrder + "')]//div[contains(@class,'ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value') and contains(@col-id,'averageFillPrice')]/span/span")).getText();

        var result = Double.parseDouble(text);

        return result;
    }

    public String getColorOfExecLine() {

        var value = driver.findElement(By.cssSelector("g.dc-line-AVERAGE_EXECUTION_PRICE path:last-child")).getAttribute("stroke");

        var color = Color.decode(value);

        var rgba = "rgba(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", 1)";

        logger.info("Color of Exec line is " + value + " and " + rgba);

        return rgba;
    }

    public String getColorOf(String element) {

        By selector = null;
        if (element.equals("Price imp. (ticks)")) {
            selector = By.cssSelector("[ref=eRightContainer] div.ag-row-selected [col-id=priceImprovementToBenchmark]");
        } else {
            selector = By.cssSelector("[ref=eRightContainer] div.ag-row-selected [col-id=priceImprovementToBenchmarkAmount]");
        }

        var value = driver.findElement(selector).getCssValue("color");
        logger.info("Color of " + element + " is " + value);
        return value;
    }

    public double getValueOfMidInTheInteractiveLegend() {
        var text = driver.findElement(By.xpath("//span[contains(text(), 'Mid price')]/ancestor::div[contains(@class, 'd-flex align-items-center')]/div[contains(@class, 'legend-container__item-value')]")).getText();

        logger.info("Value of mid in interactive legend: " + text);

        var result = Double.parseDouble(text);

        return result;
    }

    public boolean isColumnVisible(String columnName) {
        var result = driver.findElements(getColumnHeaderItemByName(columnName)).size() > 0;
        logger.info( columnName + " is displayed: " + result);
        return result;
    }

    public boolean isColumnInFilterConfiguratorControlVisible(String checkboxName) {
        var result = driver.findElement(By.xpath("//*[@class='ag-column-tool-panel-columns']//span[text()='" + checkboxName + "']/preceding-sibling::span")).isDisplayed();
        logger.info( checkboxName + " is displayed: " + result);
        return result;
    }

    public void openAnyTabFilter() {
        driver.findElement(By.xpath("//span[text() ='Id']/parent::div[@ref='eLabel']/preceding-sibling::span[@ref='eMenu']")).click();
        logger.info("Column tab filter Id is opened.");
    }

    public void selectThirdTab() {
        driver.findElement(By.cssSelector("span.ag-icon.ag-icon-columns")).click();
        logger.info("Third tab selected.");
    }

    public void clickOn(String columnName) {
        driver.findElement(getColumnHeaderItemByName(columnName)).click();
        logger.info("Click on column " + columnName);
    }

    private By getColumnHeaderItemByName(String columnName) {
        return By.xpath("//div[contains(@class, 'ag-header-cell-label')]/span[text() ='" + columnName + "']");
    }

    public String[] getItems(String columnName) {

        var colId = driver.findElement(By.xpath("//div[contains(@class, 'ag-header-cell-label')]/span[text() ='" + columnName + "']/../../..")).getAttribute("col-id");
        var elements = driver.findElements(By.cssSelector(".ag-cell[col-id='" + colId + "'] span"));
        var texts = elements.stream().map(x -> x.getText()).limit(elements.size() - 2).toArray(String[]::new);
        logger.info("Found " + texts.length + " elements.");

        return texts;
    }

    public void clickOnCheckboxInTheThirdTab(String checkboxName) {
        driver.findElement(By.xpath("//*[@ref='tabBody']//span[text()='" + checkboxName + "']/preceding-sibling::span")).click();
        logger.info(checkboxName + " clicked.");
    }

    public void clickOnTheFilterOnToolPanel() {
        driver.findElement(By.cssSelector("div.ag-side-button")).click();
        logger.info("The filter on Tool Panel clicked.");
    }

    public void uncheckCheckboxInToolPanel(String checkboxName) {
        driver.findElement(By.xpath("//*[@ref='primaryColsListPanel']//span[text()='" + checkboxName + "']/preceding-sibling::span/span[1]/span[1]")).click();
        logger.info(checkboxName + " clicked.");
    }

    public void checkCheckboxInToolPanel(String checkboxName) {
        driver.findElement(By.xpath("//*[@ref='primaryColsListPanel']//span[text()='" + checkboxName + "']/preceding-sibling::span/span[2]/span[1]")).click();
        logger.info(checkboxName + " clicked.");
    }
}
