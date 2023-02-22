package com.petstore.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(features ="src/test/java/com/petstore/features",plugin = "json:target/jsonReports/cucumber-reports.json",glue = "com.petstore.stepdefinitions",dryRun = false,monochrome = true, tags="@DeletePurchaseOrder")

public class TestRunner {


}
