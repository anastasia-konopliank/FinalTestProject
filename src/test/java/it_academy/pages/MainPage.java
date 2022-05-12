package it_academy.pages;

import it_academy.driver.DriverManager;
import it_academy.enums.TabName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private final Logger logger = LogManager.getLogger(MainPage.class);
    private final WebDriver driver;

    public MainPage() {
        driver = DriverManager.getDriver();
    }

    public void ensureOpened() throws Exception {
        var isUserTitleDisplayed = driver.findElement(By.xpath("//span[contains(@class, 'header__user-name')]")).isDisplayed();

        if (!isUserTitleDisplayed) {
            logger.error("Main page is not opened. User title is not presented on the page.");
            throw new Exception("Main page is not opened");
        }
    }

    public WebElement getTab(TabName tabName){
        logger.info("Trying to find tab " + tabName + ".");

        var expectedTab = driver.findElement(By.xpath("//div[contains(@class, 'app-title') and text()='" + tabName.getDisplayName() + "']"));

        if (expectedTab == null){
            logger.warn("Tab " + tabName + " is not found.");

            return null;
        }

        logger.info("Tab " + tabName + " is found.");

        return expectedTab;
    }

    public boolean isBenchmarkDisplayed(){
        var result = driver.findElement(By.cssSelector("app-benchmark-selection .benchmark-selection")).isDisplayed();
        logger.info("Check benchmark displayed: " + result);
        return result;
    }

    public boolean isSettingIconDisplayed(){
        var result = driver.findElement(By.xpath("//a[contains(@title, 'Settings')]")).isDisplayed();
        logger.info("Check settings displayed: " + result);
        return result;
    }

    public void openFilters() {
        driver.findElement(By.cssSelector("app-fast-filter-button")).click();
        logger.info("Filter Configurator control is opened.");
    }

    public void clickOnCheckboxInFilterConfigurationControl(String checkboxName) {
        driver.findElement(By.xpath("//*[@class='ag-column-tool-panel-columns']//span[text()='" + checkboxName + "']/preceding-sibling::span")).click();
        logger.info(checkboxName + " clicked.");

    }
}
