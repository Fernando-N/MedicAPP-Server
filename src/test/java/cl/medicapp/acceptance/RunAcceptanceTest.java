package cl.medicapp.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/acceptance",
        plugin = {
                "pretty",
                "html:target/cucumberReport/report.html",
                "json:target/cucumberReport/cucumber.json"
        }
)
public class RunAcceptanceTest {

}
