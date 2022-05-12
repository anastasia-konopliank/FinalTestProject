package it_academy.pages;

import it_academy.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class LoginPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage() {
        driver = DriverManager.getDriver();
    }

    public void open() {
        driver.get("http://app.tca.deltixuat.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        logger.info("Page opened.");
    }

    public void setUsername() {
        WebElement usernameField = driver.findElement(
                By.xpath("//input[contains(@formcontrolname, 'username')]"));
        usernameField.sendKeys("selenium_chrome");

        logger.info("Username is set.");
    }

    public void setPassword() {
        WebElement passwordField = driver.findElement(
                By.xpath("//input[contains(@formcontrolname, 'password')] "));
        passwordField.sendKeys("Axa@Demo");

        logger.info("Password is set.");
    }

    public void clickToLoginButton() {
        driver.findElement(By.xpath("//button[contains(@ng-click, 'sendForm()')]")).click();

        logger.info("Log in button clicked.");
    }
}
