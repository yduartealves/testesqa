package steps;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
		  plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
            features = {"src/test/resources/"},
         //  glue = {"com.pb.cucumbertest.stepdefinitions"},
            monochrome = true,
            dryRun = false
		
		)


public class TestRunner {

}
