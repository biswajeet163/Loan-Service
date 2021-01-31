package com.loanapp.cucumber.stepdefination;


import org.junit.runner.RunWith; 

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/loanapp/cucumber/features", plugin = {
		"html:target/cucumber-report" }, monochrome = true)
public class CucumberTestRunner {

}
   