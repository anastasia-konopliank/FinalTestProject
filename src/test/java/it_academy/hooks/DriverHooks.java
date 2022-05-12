package it_academy.hooks;

import it_academy.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverHooks {

    private final Logger logger = LogManager.getLogger(DriverManager.class);

    @Before
    public void setupDriver() {
        DriverManager.init();
        logger.info("Setup Driver completed");
    }

    @After
    public void quitDriver() {
        DriverManager.cleanUp();
        logger.info("Clean Up Driver completed");
    }
}
