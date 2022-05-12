package it_academy.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:test-output", "json:target/cucumber-report/cucumber.json" },
        monochrome = true,
        glue = {"it_academy.hooks", "it_academy.steps"},
        features = "classpath:features"
)
public class CucumberRunnerTest {
}
