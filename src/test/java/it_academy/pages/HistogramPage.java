package it_academy.pages;

import it_academy.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.IntStream;

public class HistogramPage {
    private Logger logger = LogManager.getLogger(HistogramPage.class);

    private WebDriver driver;

    public HistogramPage() {
        driver = DriverManager.getDriver();
    }

    public HistogramBar[] getBars() {

        List<WebElement> bars = driver.findElements(By.cssSelector(".bar"));

        if (bars.size() == 0) {
            logger.warn("No bars to check.");
            return new HistogramBar[0];
        }

        var result = IntStream
                .range(0, bars.size())
                .mapToObj(i -> new HistogramBar(bars.get(i), i))
                .toArray(HistogramBar[]::new);

        return result;
    }

    public boolean isBoundaryDisplayed() {

        var result = driver.findElement(By.cssSelector(".tooltip > div:nth-of-type(1)")).getText();
        logger.info("HistogramBar" + ": boundary displayed: " + result);
        return result.contains("Avg fill price");
    }

    public boolean isNumberOfOrdersDisplayed() {
        var result = driver.findElement(By.cssSelector(".tooltip > div:nth-of-type(2)")).isDisplayed();
        logger.info("HistogramBar" + ": boundary displayed: " + result);
        return result;
    }
}
