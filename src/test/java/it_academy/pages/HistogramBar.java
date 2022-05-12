package it_academy.pages;

import it_academy.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HistogramBar {
    private WebElement element;
    private int index;
    private Logger logger = LogManager.getLogger(HistogramBar.class);

    public HistogramBar(WebElement element, int index) {

        this.element = element;
        this.index = index;
    }

    public void hover() {
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).build().perform();
        logger.info("Bar" + index + " hovered.");
    }

    public int getNumber() {
        return index;
    }
}

