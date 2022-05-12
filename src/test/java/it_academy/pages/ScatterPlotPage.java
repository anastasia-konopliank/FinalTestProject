package it_academy.pages;

import it_academy.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class ScatterPlotPage {

    private final Logger logger = LogManager.getLogger(ScatterPlotPage.class);
    private final ArrayList<ScatterPlotPageSnapshot> snapshots = new ArrayList<>();
    private final WebDriver driver;

    public ScatterPlotPage() {
        driver = DriverManager.getDriver();
    }

    public void clickOnAttribute(String axe) {
        driver.findElement(By.xpath("//div[contains(@class, 'app-title') and contains (text(), '" + axe + " Attribute')]/..//input[contains(@spellcheck, 'spellcheck')]")).click();
    }

    public void waitForPlotToFinishLoading() {
        try {
            new WebDriverWait(driver, 1).until(
                    ExpectedConditions.attributeContains(By.cssSelector(".visualization__view"),
                            "class", "loading"));
        } catch (TimeoutException e) {
            logger.info("No loading. Probably missed");
        }

        new WebDriverWait(driver, 10).until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(By.cssSelector(".visualization__view"),
                        "class", "loading")));
    }

    public void clickOnPoint() {
        waitForPlotToFinishLoading();
        driver.findElement(By.cssSelector(".scatter_element")).click();
    }

    public void clickOnDropDownItem(String item) {
        driver.findElement(By.cssSelector(".autocomplete-dropdown-item[title='" + item + "']")).click();
    }

    public void cleanSnapshots() {
        logger.info("Actions history cleared.");
        snapshots.clear();
    }

    public String[] getListOfAttributes(String axe) {
        clickOnAttribute(axe);

        logger.info("Attribute list opened for " + axe);

        var attributes = driver.findElements(By.cssSelector(".autocomplete-dropdown-item a")).toArray(WebElement[]::new);

        if (attributes.length == 0){
            logger.warn("No attributes in the list for " + axe);
        }

        var result = new ArrayList<String>();

        for (var element : attributes) {
            result.add(element.getText());
        }

        clickOnAttribute(axe);

        logger.info("Attribute list closed for " + axe);

        return result.toArray(String[]::new);
    }

    public void changeAttribute(String axe, String attribute) {
        clickOnAttribute(axe);

        logger.info("Attribute list opened for " + axe);

        clickOnDropDownItem(attribute);

        logger.info("Attribute " + attribute + " selected");
    }

    public boolean isGridDisplayed(){
        return driver.findElements(By.cssSelector(".visualization__grid")).size() > 0;
    }

    public void takePageSnapshot() {
        waitForPlotToFinishLoading();

        var xAttribute = driver.findElements(By.cssSelector("input.autocomplete-input")).get(0).getAttribute("title");
        var yAttribute = driver.findElements(By.cssSelector("input.autocomplete-input")).get(1).getAttribute("title");
        var xName = driver.findElement(By.cssSelector(".scatter-plot-x-label")).getText();
        var yName = driver.findElement(By.cssSelector(".scatter-plot-y-label")).getText();
        var xValue = driver.findElement(By.cssSelector("g.x.axis>.tick>text")).getText();
        var yValue = driver.findElement(By.cssSelector("[class='y axis']>.tick>text")).getText();

        logger.info("Page snapshot created.");

        snapshots.add(new ScatterPlotPageSnapshot(xAttribute, yAttribute, xValue, yValue, xName, yName));
    }

    public ScatterPlotPageAction[] getPageHistory() {

        var result = new ArrayList<ScatterPlotPageAction>();
        var currentSnapshot = snapshots.get(0);
        for (var snapshot : snapshots.stream().skip(1).toArray(ScatterPlotPageSnapshot[]::new)) {

            result.add(new ScatterPlotPageAction(currentSnapshot, snapshot));
            currentSnapshot = snapshot;
        }

        return result.toArray(ScatterPlotPageAction[]::new);
    }
}
