package it_academy.driver;

import it_academy.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DriverManager {
    private static WebDriver driver;
    private final static Logger logger = LogManager.getLogger(DriverManager.class);

    public static WebDriver getDriver() {
        init();
        return driver;
    }

    public static void init() {
        if (driver == null) {

            System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32 (101)\\chromedriver.exe");
            var options = new ChromeOptions();
            //options.addArguments("--remote-debugging-port=8000", "--auto-open-devtools-for-tabs");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();

            logger.info("Driver initialized");
        }
    }

    public static void cleanUp() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Driver cleaned up");
        }
    }
}
